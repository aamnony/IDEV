package com.github.aamnony.idev.vhdl.lang;

import com.github.aamnony.idev.vhdl.IdByNameComparator;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.roots.OrderRootType;
import com.intellij.openapi.roots.libraries.Library;
import com.intellij.openapi.roots.libraries.LibraryTable;
import com.intellij.openapi.roots.libraries.LibraryTablesRegistrar;
import com.intellij.openapi.vfs.VirtualFile;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiManager;
import com.intellij.psi.search.FileTypeIndex;
import com.intellij.psi.search.GlobalSearchScope;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class VhdlPsiTreeUtil {

    private static final int PACKAGE_NAME_INDEX_IN_USE_CLAUSE = 1;

    /**
     * The {@code standard} package is always imported to every file.
     */
    private static final Collection<String> ALWAYS_IMPORTED_PACKAGES = Collections.singleton("standard");

    /**
     * Usually we import {@code std_logic_1164} and {@code numeric_std} (or god forbid {@code std_logic_arith} :( ).
     */
    private static final int IMPORTED_PACKAGES_INITIAL_COUNT = 2 + ALWAYS_IMPORTED_PACKAGES.size();

    /**
     * Finds {@link VhdlIdentifier}s that match {@code id}, in {@code project}.<br>
     * If {@code id == null} then all found {@link VhdlIdentifier}s are returned in the order they were found in.<br>
     * The file containing {@code id}, is queried first.
     * <p>
     * The comparison is made by {@link IdByNameComparator}.
     *
     * @param id       The {@link VhdlIdentifier} to look for.
     * @param foundIds A {@link List} of {@link VhdlIdentifier}s that match {@code id}.
     */
    public static void findIdentifiers(@NotNull VhdlIdentifier id, @NotNull List<VhdlIdentifier> foundIds) {
        // todo: Guess where the name could be defined by looking up potential packages.
        Project project = id.getProject();
        VhdlFile idFile = (VhdlFile) id.getContainingFile();

        PsiElement[] scopes = id.getScopes();
        // Search first in the id containing file.
        findIdentifiers(id, foundIds, scopes);
//        findIdentifiers(id, foundIds, idFile);

        Set<String> importedPackageNames = new HashSet<>(IMPORTED_PACKAGES_INITIAL_COUNT);
        importedPackageNames.addAll(ALWAYS_IMPORTED_PACKAGES);
        getImportedPackageNames(idFile, importedPackageNames);

        Collection<VirtualFile> virtualFiles = FileTypeIndex.getFiles(VhdlFileType.INSTANCE, GlobalSearchScope.allScope(project));

        LibraryTable globalLibrariesTable = LibraryTablesRegistrar.getInstance().getLibraryTable();
        Library[] globalLibraries = globalLibrariesTable.getLibraries();
        for (Library lib : globalLibraries) {
            VirtualFile[] libFiles = lib.getFiles(OrderRootType.SOURCES);
            virtualFiles.addAll(Arrays.asList(libFiles));
        }

        // Search other files in the project.
        for (VirtualFile virtualFile : virtualFiles) {
            VhdlFile vhdlFile = (VhdlFile) PsiManager.getInstance(project).findFile(virtualFile);
            if (vhdlFile != null && vhdlFile != idFile) {
                VhdlPackageDeclaration[] packages = vhdlFile.findChildrenByClass(VhdlPackageDeclaration.class);
                for (VhdlPackageDeclaration pkg : packages) {
                    String packageName = pkg.getIdentifierList().get(0).getText();
                    if (importedPackageNames.contains(packageName.toLowerCase())) {
                        findIdentifiers(id, foundIds, vhdlFile);
                        break; // No need to search the file again.
                    }
                }
                VhdlEntityDeclaration[] entities = vhdlFile.findChildrenByClass(VhdlEntityDeclaration.class);
                for (VhdlEntityDeclaration entity : entities) {
                    VhdlIdentifier entityId = entity.getIdentifierList().get(0);
                    if (IdByNameComparator.match(id, entityId)) {
                        foundIds.add(entityId);
                    }

                }
            }
        }
    }

    /**
     * Finds {@link VhdlIdentifier}s that match the {@code id}, in the psi tree under {@code scopes}. <br>
     * The comparison is made by {@link IdByNameComparator}.
     * If {@code id == null} then all found {@link VhdlIdentifier}s are returned.
     *
     * @param id       The {@link VhdlIdentifier} to look for.
     * @param foundIds A {@link List} of {@link VhdlIdentifier}s that match {@code id}.
     * @param scopes   The root {@link PsiElement}s to search under.
     */
    public static void findIdentifiers(@Nullable VhdlIdentifier id, @NotNull List<VhdlIdentifier> foundIds,
                                       @NotNull PsiElement... scopes) {
        for (PsiElement scope : scopes) {
            for (PsiElement child : scope.getChildren()) {
                if (child instanceof VhdlIdentifier) {
                    VhdlIdentifier childId = (VhdlIdentifier) child;
                    if (id == null || IdByNameComparator.match(id, childId)) {
                        foundIds.add(childId);
                    }
                } else {
                    findIdentifiers(id, foundIds, child);
                }
            }
        }
    }

    /**
     * Finds all used/imported packages in {@code vhdFile} and adds their names to {@code importedPackageNames}.
     *
     * @param vhdFile              The {@link VhdlFile} to search in.
     * @param importedPackageNames The {@link Set} of {@link String}s to add the found package names to.
     */
    private static void getImportedPackageNames(VhdlFile vhdFile, Set<String> importedPackageNames) {
        for (VhdlContextClause context : vhdFile.findChildrenByClass(VhdlContextClause.class)) {
            for (VhdlUseClause useClause : context.getUseClauseList()) {
                for (VhdlSelectedName selectedName : useClause.getSelectedNameList()) {
                    String packageName = selectedName.getIdentifierList().get(PACKAGE_NAME_INDEX_IN_USE_CLAUSE).getText();
                    importedPackageNames.add(packageName.toLowerCase());

                }
            }
        }
    }

    /**
     * Gets the architectures which implement {@code entity}.
     *
     * @param entity The {@link VhdlEntityDeclaration} to find the architectures of.
     * @return The {@link VhdlArchitectureBody}s that implement {@code entity}.
     */
    @NotNull
    public static List<VhdlArchitectureBody> getArchitectures(VhdlEntityDeclaration entity) {
        List<VhdlArchitectureBody> architectures = new ArrayList<>(1);
        VhdlFile file = (VhdlFile) entity.getContainingFile();
        VhdlArchitectureBody fileArchitecture = file.findChildByClass(VhdlArchitectureBody.class);
        if (fileArchitecture == null) {
            throw new UnsupportedOperationException("Architecture implementation of an entity in an outside file is not supported yet");
        }
        architectures.add(fileArchitecture);
        return architectures;
    }

    /**
     * Gets the entity which {@code arch} is implementing.
     *
     * @param arch The {@link VhdlArchitectureBody} to find the entity of.
     * @return The {@link VhdlEntityDeclaration} that {@code arch} is implementing.
     */
    @NotNull
    public static VhdlEntityDeclaration getEntity(VhdlArchitectureBody arch) {
        VhdlFile file = (VhdlFile) arch.getContainingFile();
        VhdlEntityDeclaration fileEntity = file.findChildByClass(VhdlEntityDeclaration.class);
        if (fileEntity == null) {
            throw new UnsupportedOperationException("Architecture implementing an entity in an outside file is not supported yet");
        }
        return fileEntity;
    }

    /**
     * Gets the package bodies which implement {@code pkg}.
     *
     * @param pkg The {@link VhdlPackageDeclaration} to find the body of.
     * @return The {@link VhdlPackageBody}s that implement {@code pkg}.
     */
    @NotNull
    public static List<VhdlPackageBody> getPackageBodies(VhdlPackageDeclaration pkg) {
        List<VhdlPackageBody> packageBodies = new ArrayList<>(1);
        VhdlFile file = (VhdlFile) pkg.getContainingFile();
        VhdlPackageBody filePackageBody = file.findChildByClass(VhdlPackageBody.class);
        if (filePackageBody == null) {
            throw new UnsupportedOperationException("Package body implementation of a package in an outside file is not supported yet");
        }
        packageBodies.add(filePackageBody);
        return packageBodies;
    }

    /**
     * Gets the package which {@code packageBody} is implementing.
     *
     * @param packageBody The {@link VhdlPackageBody} to find the declaration of.
     * @return The {@link VhdlPackageDeclaration} that {@code packageBody} is implementing.
     */
    @NotNull
    public static VhdlPackageDeclaration getPackage(VhdlPackageBody packageBody) {
        VhdlFile file = (VhdlFile) packageBody.getContainingFile();
        VhdlPackageDeclaration filePackage = file.findChildByClass(VhdlPackageDeclaration.class);
        if (filePackage == null) {
            throw new UnsupportedOperationException("Package body implementing a package in an outside file is not supported yet");
        }
        return filePackage;
    }

    /**
     * Gets the subprogram bodies which implement {@code subprogram}.
     * Only package subprograms are supported.
     *
     * @param subprogram The {@link VhdlSubprogramDeclaration} to find the body of.
     * @return The {@link VhdlSubprogramBody} that implement {@code subprogram}.
     */
    @NotNull
    public static List<VhdlSubprogramBody> getSubprogramBodies(VhdlSubprogramDeclaration subprogram) {
        List<VhdlSubprogramBody> subprogramBodies = new ArrayList<>(1);
        PsiElement parent = subprogram.getParent();
        if (parent instanceof VhdlPackageDeclarativePart) {
            VhdlPackageDeclaration packageDeclaration = (VhdlPackageDeclaration) parent.getParent();
            List<VhdlPackageBody> packageBodies = getPackageBodies(packageDeclaration);
            VhdlIdentifier declarationId = subprogram.getSubprogramSpecification().getDesignator().getIdentifier();
            for (VhdlPackageBody packageBody : packageBodies) {
                List<VhdlSubprogramBody> bodies = packageBody.getPackageBodyDeclarativePart().getSubprogramBodyList();
                for (VhdlSubprogramBody body : bodies) {
                    VhdlIdentifier bodyId = body.getSubprogramSpecification().getDesignator().getIdentifier();
                    if (IdByNameComparator.match(bodyId, declarationId)) {
                        subprogramBodies.add(body);
                    }
                }
            }
        }
        return subprogramBodies;
    }

    /**
     * Gets the subprogram which {@code subprogramBody} is implementing.
     * Only package subprograms are supported.
     *
     * @param subprogramBody The {@link VhdlSubprogramBody} to find the declaration of.
     * @return The {@link VhdlSubprogramDeclaration} that {@code subprogramBody} is implementing, or null if none is found.
     */
    @Nullable
    public static VhdlSubprogramDeclaration getSubprogram(VhdlSubprogramBody subprogramBody) {
        PsiElement parent = subprogramBody.getParent();
        if (parent instanceof VhdlPackageBodyDeclarativePart) {
            VhdlPackageBody packageBody = (VhdlPackageBody) parent.getParent();
            VhdlPackageDeclaration pkg = getPackage(packageBody);

            VhdlIdentifier bodyId = subprogramBody.getSubprogramSpecification().getDesignator().getIdentifier();
            List<VhdlSubprogramDeclaration> declarations = pkg.getPackageDeclarativePart().getSubprogramDeclarationList();
            for (VhdlSubprogramDeclaration declaration : declarations) {
                VhdlIdentifier declarationId = declaration.getSubprogramSpecification().getDesignator().getIdentifier();
                if (IdByNameComparator.match(declarationId, bodyId)) {
                    return declaration;
                }
            }
        }
        return null;
    }

    /**
     * Gets the entity declaration which contains {@code element}.
     *
     * @param element The {@link PsiElement} to find the entity declaration of.
     * @return The {@link VhdlEntityDeclaration} that contains {@code element}, or null if none is found.
     */
    @Nullable
    public static VhdlEntityDeclaration getEntityDeclaration(PsiElement element) {
        if (element != null) {
            if (element instanceof VhdlEntityDeclaration) {
                return (VhdlEntityDeclaration) element;
            } else if (element instanceof VhdlFile) {
                return null;
            } else {
                return getEntityDeclaration(element.getParent());
            }
        }
        return null;
    }

    /**
     * Gets the architecture body which contains {@code element}.
     *
     * @param element The {@link PsiElement} to find the architecture body of.
     * @return The {@link VhdlArchitectureBody} that contains {@code element}, or null if none is found.
     */
    @Nullable
    public static VhdlArchitectureBody getArchitectureBody(PsiElement element) {
        if (element != null) {
            if (element instanceof VhdlArchitectureBody) {
                return (VhdlArchitectureBody) element;
            } else if (element instanceof VhdlFile) {
                return null;
            } else {
                return getArchitectureBody(element.getParent());
            }
        }
        return null;
    }

    /**
     * Gets the (generic or port) map aspect which contains {@code id}.
     *
     * @param id The {@link VhdlIdentifier} to find the map aspect of.
     * @return The {@link VhdlGenericMapAspect}/{@link VhdlPortMapAspect} that contains {@code element}, or null if none is found.
     */
    @Nullable
    public static PsiElement getMapAspect(VhdlIdentifier id) {
        PsiElement parent = id.getParent();
        while (!(parent instanceof VhdlFile)) {
            if (parent instanceof VhdlGenericMapAspect) {
                return parent;
            } else if (parent instanceof VhdlPortMapAspect) {
                return parent;
            }
            parent = parent.getParent();
        }
        return null;
    }

    /**
     * Gets the instantiated entity name of {@code mapAspect}.
     *
     * @param mapAspect The {@link VhdlGenericMapAspect}/{@link VhdlPortMapAspect} to find the entity name of.
     * @return The instantiated entity name  that contains {@code mapAspect}, or null if none is found.
     * @throws IllegalArgumentException if {@code mapAspect} is not {@link VhdlGenericMapAspect}/{@link VhdlPortMapAspect}.
     */
    @Nullable
    public static String getEntityName(PsiElement mapAspect) {
        if (!(mapAspect instanceof VhdlGenericMapAspect || mapAspect instanceof VhdlPortMapAspect)) {
            throw new IllegalArgumentException("mapAspect must be either VhdlGenericMapAspect or VhdlPortMapAspect");
        }

        PsiElement parent = mapAspect.getParent();
        while (!(parent instanceof VhdlFile)) {
            if (parent instanceof VhdlComponentInstantiationStatement) {
                VhdlComponentInstantiationStatement inst = (VhdlComponentInstantiationStatement) parent;
                List<VhdlIdentifier> entityIds = inst.getInstantiatedUnit().getSelectedName().getIdentifierList();
                return entityIds.get(entityIds.size() - 1).getText();
            }
            parent = parent.getParent();
        }
        return null;
    }

    /**
     * Checks whether or not {@code id} is part of a map aspect choice (left side or map assignment).
     *
     * @param id The {@link VhdlIdentifier} to check.
     * @return {@code true} if {@code id} is part of a {@link VhdlGenericMapAspect}/{@link VhdlPortMapAspect} {@link VhdlChoice}.
     * {@code false} otherwise
     */
    public static boolean isChoice(VhdlIdentifier id) {
        PsiElement parent = id.getParent();
        while (!(parent instanceof VhdlGenericMapAspect || parent instanceof VhdlPortMapAspect)) {
            if (parent instanceof VhdlChoice) {
                return true;
            }
            parent = parent.getParent();
        }
        return false;
    }

    /**
     * Gets the generic declaration of {@code id} inside the entity named {@code entityName}.
     *
     * @param entityName The name of the {@link VhdlEntityDeclaration} to search in.
     * @param id         The {@link VhdlIdentifier} to look for.
     * @return The {@link VhdlIdentifier} of the generic declaration that is named {@code id} , or null if none is found.
     */
    @Nullable
    public static VhdlIdentifier getGenericDeclaration(String entityName, VhdlIdentifier id) {
        Project project = id.getProject();
        VhdlFile idFile = (VhdlFile) id.getContainingFile();

        Collection<VirtualFile> virtualFiles = FileTypeIndex.getFiles(VhdlFileType.INSTANCE, GlobalSearchScope.allScope(project));

        // Search other files in the project.
        for (VirtualFile virtualFile : virtualFiles) {
            VhdlFile vhdlFile = (VhdlFile) PsiManager.getInstance(project).findFile(virtualFile);
            if (vhdlFile != null && vhdlFile != idFile) {
                VhdlEntityDeclaration[] entities = vhdlFile.findChildrenByClass(VhdlEntityDeclaration.class);
                for (VhdlEntityDeclaration entity : entities) {
                    VhdlIdentifier entityId = entity.getIdentifierList().get(0);
                    if (IdByNameComparator.match(entityId, entityName)) {
                        List<VhdlInterfaceGenericDeclaration> generics = entity
                                .getEntityHeader()
                                .getGenericClause()
                                .getGenericInterfaceList()
                                .getInterfaceGenericDeclarationList();

                        for (VhdlInterfaceGenericDeclaration generic : generics) {
                            for (VhdlIdentifier genericId : generic.getIdentifierList().getIdentifierList()) {
                                if (IdByNameComparator.match(genericId, id)) {
                                    return genericId;
                                }
                            }
                        }
                    }

                }
            }
        }
        return null;
    }


    /**
     * Gets the port declaration of {@code id} inside the entity named {@code entityName}.
     *
     * @param entityName The name of the {@link VhdlEntityDeclaration} to search in.
     * @param id         The {@link VhdlIdentifier} to look for.
     * @return The {@link VhdlIdentifier} of the port declaration that is named {@code id} , or null if none is found.
     */
    @Nullable
    public static VhdlIdentifier getPortDeclaration(String entityName, VhdlIdentifier id) {
        Project project = id.getProject();
        VhdlFile idFile = (VhdlFile) id.getContainingFile();

        Collection<VirtualFile> virtualFiles = FileTypeIndex.getFiles(VhdlFileType.INSTANCE, GlobalSearchScope.allScope(project));

        // Search other files in the project.
        for (VirtualFile virtualFile : virtualFiles) {
            VhdlFile vhdlFile = (VhdlFile) PsiManager.getInstance(project).findFile(virtualFile);
            if (vhdlFile != null && vhdlFile != idFile) {
                VhdlEntityDeclaration[] entities = vhdlFile.findChildrenByClass(VhdlEntityDeclaration.class);
                for (VhdlEntityDeclaration entity : entities) {
                    VhdlIdentifier entityId = entity.getIdentifierList().get(0);
                    if (IdByNameComparator.match(entityId, entityName)) {
                        List<VhdlInterfacePortDeclaration> ports = entity
                                .getEntityHeader()
                                .getPortClause()
                                .getPortInterfaceList()
                                .getInterfacePortDeclarationList();

                        for (VhdlInterfacePortDeclaration port : ports) {
                            for (VhdlIdentifier portId : port.getIdentifierList().getIdentifierList()) {
                                if (IdByNameComparator.match(portId, id)) {
                                    return portId;
                                }
                            }
                        }
                    }

                }
            }
        }
        return null;
    }

    /**
     * Gets the component declaration which contains {@code id}.
     *
     * @param id The {@link VhdlIdentifier} to find the component declaration of.
     * @return The {@link VhdlComponentDeclaration} that contains {@code element}, or null if none is found.
     */
    @Nullable
    public static VhdlComponentDeclaration getComponentDeclaration(VhdlIdentifier id) {
        PsiElement parent = id.getParent();
        while (!(parent instanceof VhdlFile)) {
            if (parent instanceof VhdlComponentDeclaration) {
                return (VhdlComponentDeclaration) parent;
            }
            parent = parent.getParent();
        }
        return null;
    }
}