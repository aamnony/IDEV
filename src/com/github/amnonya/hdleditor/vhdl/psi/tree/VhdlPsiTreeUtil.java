package com.github.amnonya.hdleditor.vhdl.psi.tree;

import com.github.amnonya.hdleditor.vhdl.IdByNameComparator;
import com.github.amnonya.hdleditor.vhdl.fileTypes.VhdlFileType;
import com.github.amnonya.hdleditor.vhdl.psi.VhdlArchitectureBody;
import com.github.amnonya.hdleditor.vhdl.psi.VhdlContextClause;
import com.github.amnonya.hdleditor.vhdl.psi.VhdlEntityDeclaration;
import com.github.amnonya.hdleditor.vhdl.psi.VhdlFile;
import com.github.amnonya.hdleditor.vhdl.psi.VhdlIdentifier;
import com.github.amnonya.hdleditor.vhdl.psi.VhdlPackageBody;
import com.github.amnonya.hdleditor.vhdl.psi.VhdlPackageBodyDeclarativePart;
import com.github.amnonya.hdleditor.vhdl.psi.VhdlPackageDeclaration;
import com.github.amnonya.hdleditor.vhdl.psi.VhdlPackageDeclarativePart;
import com.github.amnonya.hdleditor.vhdl.psi.VhdlSelectedName;
import com.github.amnonya.hdleditor.vhdl.psi.VhdlSubprogramBody;
import com.github.amnonya.hdleditor.vhdl.psi.VhdlSubprogramDeclaration;
import com.github.amnonya.hdleditor.vhdl.psi.VhdlUseClause;
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

        // Search first in the id containing file.
        findIdentifiers(id, foundIds, idFile);

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

}