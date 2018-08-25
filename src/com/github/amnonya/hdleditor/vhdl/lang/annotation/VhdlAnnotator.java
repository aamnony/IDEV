package com.github.amnonya.hdleditor.vhdl.lang.annotation;

import com.github.amnonya.hdleditor.vhdl.VhdlIdentifierComparator;
import com.github.amnonya.hdleditor.vhdl.exceptions.DeclarationAlreadyExistsException;
import com.github.amnonya.hdleditor.vhdl.fileTypes.VhdlSyntaxHighlighter;
import com.github.amnonya.hdleditor.vhdl.psi.VhdlArchitectureBody;
import com.github.amnonya.hdleditor.vhdl.psi.VhdlConstantDeclaration;
import com.github.amnonya.hdleditor.vhdl.psi.VhdlEntityDeclaration;
import com.github.amnonya.hdleditor.vhdl.psi.VhdlFile;
import com.github.amnonya.hdleditor.vhdl.psi.VhdlFullTypeDeclaration;
import com.github.amnonya.hdleditor.vhdl.psi.VhdlIdentifier;
import com.github.amnonya.hdleditor.vhdl.psi.VhdlIdentifierList;
import com.github.amnonya.hdleditor.vhdl.psi.VhdlInterfaceGenericDeclaration;
import com.github.amnonya.hdleditor.vhdl.psi.VhdlInterfacePortDeclaration;
import com.github.amnonya.hdleditor.vhdl.psi.VhdlLabel;
import com.github.amnonya.hdleditor.vhdl.psi.VhdlPackageBody;
import com.github.amnonya.hdleditor.vhdl.psi.VhdlPackageDeclaration;
import com.github.amnonya.hdleditor.vhdl.psi.VhdlRefname;
import com.github.amnonya.hdleditor.vhdl.psi.VhdlSignalDeclaration;
import com.github.amnonya.hdleditor.vhdl.psi.VhdlSubtypeDeclaration;
import com.github.amnonya.hdleditor.vhdl.psi.tree.VhdlPsiTreeUtil;
import com.intellij.lang.annotation.AnnotationHolder;
import com.intellij.lang.annotation.Annotator;
import com.intellij.openapi.editor.colors.TextAttributesKey;
import com.intellij.openapi.util.Pair;
import com.intellij.openapi.util.TextRange;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiReference;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.TreeSet;

public class VhdlAnnotator implements Annotator {

    private static final String SUFFIX_GENERIC = "_g";
    private static final String SUFFIX_CONSTANT = "_c";

    private AnnotationHolder holder;

    @Override
    public void annotate(@NotNull PsiElement element, @NotNull AnnotationHolder holder) {
        this.holder = holder;
        // Design units:
        if (element instanceof VhdlEntityDeclaration) {
            handleEntity((VhdlEntityDeclaration) element);
        } else if (element instanceof VhdlArchitectureBody) {
            handleArchitecture((VhdlArchitectureBody) element);
        }
        // Object declarations:
        else if (element instanceof VhdlInterfaceGenericDeclaration) {
            handleGenericDeclaration((VhdlInterfaceGenericDeclaration) element);
        } else if (element instanceof VhdlInterfacePortDeclaration) {
            handlePortDeclaration((VhdlInterfacePortDeclaration) element);
        } else if (element instanceof VhdlConstantDeclaration) {
            handleConstantDeclaration((VhdlConstantDeclaration) element);
        } else if (element instanceof VhdlSignalDeclaration) {
            handleSignalDeclaration((VhdlSignalDeclaration) element);
        }
        // Type declarations:
        else if (element instanceof VhdlFullTypeDeclaration) {
            handleTypeDeclaration((VhdlFullTypeDeclaration) element);
        } else if (element instanceof VhdlSubtypeDeclaration) {
            handleSubtypeDeclaration((VhdlSubtypeDeclaration) element);
        }
        // Usages:
        else if (element instanceof VhdlIdentifier) {
            VhdlIdentifier id = (VhdlIdentifier) element;
            PsiReference[] references = id.getReferences();
            if (references != PsiReference.EMPTY_ARRAY) {
                PsiElement refId = references[0].resolve();
                PsiElement declaration = null;
                if (refId != null) {
                    declaration = refId.getParent();
                    if (declaration instanceof VhdlIdentifierList) {
                        declaration = declaration.getParent();
                    }
                }
                handleIdentifierUsage(id, declaration);
            }
        }
        // Misc.
        else if (element instanceof VhdlLabel) {
            handleLabel((VhdlLabel) element);
        }
        this.holder = null;
    }

    /**
     * Creates annotations in {@code file}. <br>
     * The annotations are related to the global structure of the file.
     *
     * @param file The {@link VhdlFile} to annotate.
     */
    private void handleFile(@NotNull VhdlFile file) {
        VhdlEntityDeclaration[] entities = file.findChildrenByClass(VhdlEntityDeclaration.class);
        VhdlArchitectureBody[] architectures = file.findChildrenByClass(VhdlArchitectureBody.class);
        VhdlPackageDeclaration[] packages = file.findChildrenByClass(VhdlPackageDeclaration.class);
        VhdlPackageBody[] packageBodies = file.findChildrenByClass(VhdlPackageBody.class);

        int endOfFile = file.getTextLength() - 1;
        TextRange warningRange = new TextRange(endOfFile, endOfFile);

        // Missing design units.
        if (entities.length == 1) {
            if (architectures.length == 0) {
                holder.createWarningAnnotation(warningRange, "No architecture is declared in this file");
            }
        } else if (packages.length == 1) {
            if (packageBodies.length == 0) {
                holder.createWarningAnnotation(warningRange, "No package body is declared in this file");
            }
        } else {

            holder.createWarningAnnotation(warningRange, "No entity or package is declared in this file");
        }

        // Multiple (same) design units.
        for (int i = 1; i < entities.length; i++) {
            holder.createWarningAnnotation(entities[i], "Multiple entities are declared in this file");
        }
        for (int i = 1; i < architectures.length; i++) {
            holder.createWarningAnnotation(architectures[i], "Multiple architectures are declared in this file");
        }
        for (int i = 1; i < packages.length; i++) {
            holder.createWarningAnnotation(packages[i], "Multiple packages are declared in this file");
        }
        for (int i = 1; i < packageBodies.length; i++) {
            holder.createWarningAnnotation(packageBodies[i], "Multiple package bodies are declared in this file");
        }
    }

    /**
     * Creates annotations in {@code entity}.
     *
     * @param entity The {@link VhdlEntityDeclaration} to annotate.
     */
    private void handleEntity(@NotNull VhdlEntityDeclaration entity) {
        annotateLabels(entity.getIdentifierList(), VhdlSyntaxHighlighter.PRIMARY_DESIGN_UNIT_NAME);
    }

    /**
     * Creates annotations in {@code arch} - both inside the declarative part and inside the
     * statements part.
     *
     * @param arch The {@link VhdlArchitectureBody} to annotate.
     */
    private void handleArchitecture(@NotNull VhdlArchitectureBody arch) {
        VhdlRefname archEntity = arch.getRefname();
        VhdlFile parent = (VhdlFile) arch.getParent();
        VhdlEntityDeclaration fileEntity = parent.findChildByClass(VhdlEntityDeclaration.class);
        if (fileEntity != null) {
            String entityName = fileEntity.getIdentifierList().get(0).getName();

            if (archEntity.getText().equalsIgnoreCase(entityName)) {
                annotateText(archEntity, VhdlSyntaxHighlighter.PRIMARY_DESIGN_UNIT_NAME);
            } else {
                holder.createWarningAnnotation(archEntity.getTextRange(), "Architecture doesn't implement the entity declared in this file");
            }
        }
        annotateLabels(arch.getIdentifierList(), VhdlSyntaxHighlighter.SECONDARY_DESIGN_UNIT_NAME);
    }


    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    /**
     * Creates annotations for {@code portDeclaration}.
     *
     * @param portDeclaration The {@link VhdlInterfacePortDeclaration} to annotate.
     */
    private void handlePortDeclaration(VhdlInterfacePortDeclaration portDeclaration) {
        for (VhdlIdentifier id : portDeclaration.getIdentifierList().getIdentifierList()) {
            try {
                checkDuplicates(id);
                annotateText(id, VhdlSyntaxHighlighter.PORT_NAME);
            } catch (DeclarationAlreadyExistsException e) {
                holder.createErrorAnnotation(e.getIdentifier(), e.getMessage());
            }
        }
    }

    /**
     * Creates annotations for {@code genericDeclaration}.
     *
     * @param genericDeclaration The {@link VhdlInterfaceGenericDeclaration} to annotate.
     */
    private void handleGenericDeclaration(VhdlInterfaceGenericDeclaration genericDeclaration) {
        for (VhdlIdentifier id : genericDeclaration.getIdentifierList().getIdentifierList()) {
            try {
                checkDuplicates(id);
                annotateText(id, VhdlSyntaxHighlighter.GENERIC_NAME);
                if (!id.getText().toLowerCase().endsWith(SUFFIX_GENERIC)) {
                    holder.createWeakWarningAnnotation(id, String.format("Generic names should use '%s' suffix convention", SUFFIX_GENERIC));
                }
            } catch (DeclarationAlreadyExistsException e) {
                holder.createErrorAnnotation(e.getIdentifier(), e.getMessage());
            }
        }
    }

    /**
     * Creates annotations for {@code constantDeclaration}.
     * TODO: Currently supports only architecture declarations.
     *
     * @param constantDeclaration The {@link VhdlConstantDeclaration} to annotate.
     */
    private void handleConstantDeclaration(VhdlConstantDeclaration constantDeclaration) {
        for (VhdlIdentifier id : constantDeclaration.getIdentifierList().getIdentifierList()) {
            try {
                checkDuplicates(id);
                annotateText(id, VhdlSyntaxHighlighter.CONSTANT_NAME);
                if (!id.getText().toLowerCase().endsWith(SUFFIX_CONSTANT)) {
                    holder.createWeakWarningAnnotation(id, String.format("Constant names should use '%s' suffix convention", SUFFIX_CONSTANT));
                }
            } catch (DeclarationAlreadyExistsException e) {
                holder.createErrorAnnotation(e.getIdentifier(), e.getMessage());
            }
        }
    }

    /**
     * Creates annotations for {@code signalDeclaration}.
     * TODO: Currently supports only architecture declarations.
     *
     * @param signalDeclaration The {@link VhdlSignalDeclaration} to annotate.
     */
    private void handleSignalDeclaration(VhdlSignalDeclaration signalDeclaration) {
        for (VhdlIdentifier id : signalDeclaration.getIdentifierList().getIdentifierList()) {
            try {
                checkDuplicates(id);
                annotateText(id, VhdlSyntaxHighlighter.SIGNAL_NAME);
            } catch (DeclarationAlreadyExistsException e) {
                holder.createErrorAnnotation(e.getIdentifier(), e.getMessage());
            }
        }
    }

    /**
     * Creates annotations for {@code typeDeclaration}.
     *
     * @param typeDeclaration The {@link VhdlFullTypeDeclaration} to annotate.
     */
    private void handleTypeDeclaration(VhdlFullTypeDeclaration typeDeclaration) {
        VhdlIdentifier id = typeDeclaration.getIdentifier();
        try {
            checkDuplicates(id);
            annotateText(id, VhdlSyntaxHighlighter.TYPE);
        } catch (DeclarationAlreadyExistsException e) {
            holder.createErrorAnnotation(e.getIdentifier(), e.getMessage());
        }
    }

    /**
     * Creates annotations for {@code subtypeDeclaration}.
     *
     * @param subtypeDeclaration The {@link VhdlSubtypeDeclaration} to annotate.
     */
    private void handleSubtypeDeclaration(VhdlSubtypeDeclaration subtypeDeclaration) {
        VhdlIdentifier id = subtypeDeclaration.getIdentifier();
        try {
            checkDuplicates(id);
            annotateText(id, VhdlSyntaxHighlighter.SUBTYPE);
        } catch (DeclarationAlreadyExistsException e) {
            holder.createErrorAnnotation(e.getIdentifier(), e.getMessage());
        }
    }

    private void handleIdentifierUsage(VhdlIdentifier id, PsiElement declaration) {
        if (declaration instanceof VhdlInterfaceGenericDeclaration) {
            annotateText(id, VhdlSyntaxHighlighter.GENERIC_NAME);
        } else if (declaration instanceof VhdlInterfacePortDeclaration) {
            annotateText(id, VhdlSyntaxHighlighter.PORT_NAME);
        } else if (declaration instanceof VhdlConstantDeclaration) {
            annotateText(id, VhdlSyntaxHighlighter.CONSTANT_NAME);
        } else if (declaration instanceof VhdlSignalDeclaration) {
            annotateText(id, VhdlSyntaxHighlighter.SIGNAL_NAME);
        } else if (declaration instanceof VhdlEntityDeclaration) {
            annotateText(id, VhdlSyntaxHighlighter.PRIMARY_DESIGN_UNIT_NAME);
        } else if (declaration instanceof VhdlArchitectureBody) {
            annotateText(id, VhdlSyntaxHighlighter.SECONDARY_DESIGN_UNIT_NAME);
        } else if (declaration instanceof VhdlFullTypeDeclaration) {
            annotateText(id, VhdlSyntaxHighlighter.TYPE);
        } else if (declaration instanceof VhdlSubtypeDeclaration) {
            annotateText(id, VhdlSyntaxHighlighter.SUBTYPE);
        }
    }


    /**
     * Checks if {@code declarationId} is already declared in its scope.
     *
     * @param declarationId The declared {@link VhdlIdentifier} to check.
     * @throws DeclarationAlreadyExistsException If {@code declarationId} is already declared in its scope.
     */
    private void checkDuplicates(@NotNull VhdlIdentifier declarationId) throws DeclarationAlreadyExistsException {
        PsiElement[] scopes = declarationId.getScopes();
        PsiElement declaration = null;
        List<VhdlIdentifier> ids = new ArrayList<>();
        VhdlPsiTreeUtil.findIdentifiers(declarationId, ids, scopes);
        for (VhdlIdentifier id : ids) {
            if (id.isDeclared()) {
                declaration = id.getParent();
                if (declaration instanceof VhdlIdentifierList) {
                    declaration = declaration.getParent();
                }
            }
        }

        if (declaration instanceof VhdlInterfaceGenericDeclaration) {
            throw new DeclarationAlreadyExistsException("Generic named '%s' already exists in this entity", declarationId);
        } else if (declaration instanceof VhdlInterfacePortDeclaration) {
            throw new DeclarationAlreadyExistsException("Port named '%s' already exists in this entity", declarationId);
        } else if (declaration instanceof VhdlConstantDeclaration) {
            throw new DeclarationAlreadyExistsException("Constant named '%s' already exists in this scope", declarationId);
        } else if (declaration instanceof VhdlSignalDeclaration) {
            throw new DeclarationAlreadyExistsException("Signal named '%s' already exists in this scope", declarationId);
        }
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    /**
     * Creates annotations for {@code label} and its matching {@link VhdlLabel}.
     *
     * @param label The {@link VhdlLabel} to annotate.
     * @see #annotateLabels(List, TextAttributesKey)
     */
    private void handleLabel(VhdlLabel label) {
        List<VhdlIdentifier> ids = new ArrayList<>(2);
        ids.add(label.getIdentifier());

        PsiElement[] siblings = label.getParent().getChildren();
        for (PsiElement sibling : siblings) {
            if (sibling instanceof VhdlLabel && sibling != label) {
                ids.add(((VhdlLabel) sibling).getIdentifier());
                // Found the matching label, we can stop searching.
                break;
            }
        }
        annotateLabels(ids, VhdlSyntaxHighlighter.LABEL);
    }

    /**
     * Creates annotations for a group of {@link VhdlIdentifier}s that represent labels.
     *
     * @param ids        The {@link List} of {@link VhdlIdentifier}s that represent labels.
     *                   Its {@code size()} can be either: <ul>
     *                   <li>1 - if there is only a start label and no end label</li>
     *                   <li>2 - if there are both start and end labels</li>
     *                   </ul>
     * @param attributes The text attributes to annotate if no warnings and errors exist for the labels.
     * @throws IllegalArgumentException if {@code ids.size()} is not 1 or 2.
     */
    private void annotateLabels(@NotNull List<VhdlIdentifier> ids, TextAttributesKey attributes) {
        VhdlIdentifier startLabel = ids.get(0);
        if (ids.size() == 1) {
            // No end label, still annotate the start label.
            annotateText(startLabel, attributes);
        } else if (ids.size() == 2) {
            VhdlIdentifier endLabel = ids.get(1);
            if (endLabel.getText().equalsIgnoreCase(startLabel.getText())) {
                annotateText(startLabel, attributes);
                annotateText(endLabel, attributes);
            } else {
                holder.createErrorAnnotation(startLabel, "Labels don't match");
                holder.createErrorAnnotation(endLabel, "Labels don't match");
            }
        } else {
            throw new IllegalArgumentException("ids must be of size 1 or 2.");
        }
    }

    /**
     * Creates text annotation to {@code element}.
     *
     * @param element    The {@link PsiElement} to annotate.
     * @param attributes The text attributes to use for the annotation.
     */
    private void annotateText(PsiElement element, TextAttributesKey attributes) {
        holder.createInfoAnnotation(element, null).setTextAttributes(attributes);
    }

    /**
     * Separates the unique and duplicate {@link VhdlIdentifier}s in {@code ids}.
     *
     * @param ids The {@link Collection} of {@link VhdlIdentifier}s to search in.
     * @return A {@link Pair} of {@link Collection}s of {@link VhdlIdentifier}s: <ul>
     * <li>The first is the unique identifiers</li>
     * <li>The second is the duplicate identifiers</li>
     * </ul>
     */
    @NotNull
    private static Pair<Collection<VhdlIdentifier>, Collection<VhdlIdentifier>>
    getDuplicates(@NotNull Collection<VhdlIdentifier> ids) {
        TreeSet<VhdlIdentifier> uniques = new TreeSet<>(VhdlIdentifierComparator.INSTANCE);
        List<VhdlIdentifier> duplicates = new ArrayList<>(ids.size() / 2);

        for (VhdlIdentifier id : ids) {
            if (!uniques.add(id)) {
                duplicates.add(id);
            }
        }
        return new Pair<>(uniques, duplicates);
    }
}