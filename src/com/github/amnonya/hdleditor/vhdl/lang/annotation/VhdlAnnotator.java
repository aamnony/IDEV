package com.github.amnonya.hdleditor.vhdl.lang.annotation;

import com.github.amnonya.hdleditor.vhdl.IdByNameComparator;
import com.github.amnonya.hdleditor.vhdl.exceptions.DeclarationAlreadyExistsException;
import com.github.amnonya.hdleditor.vhdl.fileTypes.VhdlSyntaxHighlighter;
import com.github.amnonya.hdleditor.vhdl.psi.VhdlAliasDeclaration;
import com.github.amnonya.hdleditor.vhdl.psi.VhdlArchitectureBody;
import com.github.amnonya.hdleditor.vhdl.psi.VhdlAttributeDeclaration;
import com.github.amnonya.hdleditor.vhdl.psi.VhdlAttributeName;
import com.github.amnonya.hdleditor.vhdl.psi.VhdlComponentDeclaration;
import com.github.amnonya.hdleditor.vhdl.psi.VhdlConstantDeclaration;
import com.github.amnonya.hdleditor.vhdl.psi.VhdlDesignator;
import com.github.amnonya.hdleditor.vhdl.psi.VhdlEntityDeclaration;
import com.github.amnonya.hdleditor.vhdl.psi.VhdlFile;
import com.github.amnonya.hdleditor.vhdl.psi.VhdlFileDeclaration;
import com.github.amnonya.hdleditor.vhdl.psi.VhdlFullTypeDeclaration;
import com.github.amnonya.hdleditor.vhdl.psi.VhdlFunctionParameterConstantDeclaration;
import com.github.amnonya.hdleditor.vhdl.psi.VhdlFunctionParameterSignalDeclaration;
import com.github.amnonya.hdleditor.vhdl.psi.VhdlIdentifier;
import com.github.amnonya.hdleditor.vhdl.psi.VhdlIdentifierList;
import com.github.amnonya.hdleditor.vhdl.psi.VhdlInterfaceGenericDeclaration;
import com.github.amnonya.hdleditor.vhdl.psi.VhdlInterfacePortDeclaration;
import com.github.amnonya.hdleditor.vhdl.psi.VhdlLabel;
import com.github.amnonya.hdleditor.vhdl.psi.VhdlPackageBody;
import com.github.amnonya.hdleditor.vhdl.psi.VhdlPackageDeclaration;
import com.github.amnonya.hdleditor.vhdl.psi.VhdlProcedureParameterConstantDeclaration;
import com.github.amnonya.hdleditor.vhdl.psi.VhdlProcedureParameterSignalDeclaration;
import com.github.amnonya.hdleditor.vhdl.psi.VhdlProcedureParameterVariableDeclaration;
import com.github.amnonya.hdleditor.vhdl.psi.VhdlRefname;
import com.github.amnonya.hdleditor.vhdl.psi.VhdlSignalDeclaration;
import com.github.amnonya.hdleditor.vhdl.psi.VhdlSubprogramBody;
import com.github.amnonya.hdleditor.vhdl.psi.VhdlSubprogramDeclaration;
import com.github.amnonya.hdleditor.vhdl.psi.VhdlSubprogramParameterFileDeclaration;
import com.github.amnonya.hdleditor.vhdl.psi.VhdlSubprogramSpecification;
import com.github.amnonya.hdleditor.vhdl.psi.VhdlSubtypeDeclaration;
import com.github.amnonya.hdleditor.vhdl.psi.VhdlTypes;
import com.github.amnonya.hdleditor.vhdl.psi.VhdlVariableDeclaration;
import com.github.amnonya.hdleditor.vhdl.psi.impl.VhdlIdentifierPsiImplUtil;
import com.github.amnonya.hdleditor.vhdl.psi.tree.VhdlPsiTreeUtil;
import com.intellij.lang.annotation.AnnotationHolder;
import com.intellij.lang.annotation.Annotator;
import com.intellij.openapi.editor.colors.TextAttributesKey;
import com.intellij.openapi.util.TextRange;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiReference;
import com.intellij.psi.PsiWhiteSpace;
import com.intellij.psi.tree.IElementType;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class VhdlAnnotator implements Annotator {

    private static final String SUFFIX_GENERIC = "_g";
    private static final String SUFFIX_CONSTANT = "_c";

    private AnnotationHolder holder;

    @Override
    public void annotate(@NotNull PsiElement element, @NotNull AnnotationHolder holder) {
        this.holder = holder;
        // Design units:
        if (element instanceof VhdlEntityDeclaration) {
            annotate((VhdlEntityDeclaration) element);
        } else if (element instanceof VhdlArchitectureBody) {
            annotate((VhdlArchitectureBody) element);
        } else if (element instanceof VhdlPackageDeclaration) {
            annotate((VhdlPackageDeclaration) element);
        } else if (element instanceof VhdlPackageBody) {
            annotate((VhdlPackageBody) element);
        }
        // Object declarations:
        else if (element instanceof VhdlInterfaceGenericDeclaration) {
            annotate((VhdlInterfaceGenericDeclaration) element);
        } else if (element instanceof VhdlInterfacePortDeclaration) {
            annotate((VhdlInterfacePortDeclaration) element);
        } else if (element instanceof VhdlConstantDeclaration) {
            annotate((VhdlConstantDeclaration) element);
        } else if (element instanceof VhdlSignalDeclaration) {
            annotate((VhdlSignalDeclaration) element);
        } else if (element instanceof VhdlVariableDeclaration) {
            annotate((VhdlVariableDeclaration) element);
        } else if (element instanceof VhdlFileDeclaration) {
            annotate((VhdlFileDeclaration) element);
        } else if (element instanceof VhdlAliasDeclaration) {
            annotate((VhdlAliasDeclaration) element);
        } else if (element instanceof VhdlAttributeDeclaration) {
            annotate((VhdlAttributeDeclaration) element);
        } else if (element instanceof VhdlFunctionParameterConstantDeclaration) {
            annotate((VhdlFunctionParameterConstantDeclaration) element);
        } else if (element instanceof VhdlFunctionParameterSignalDeclaration) {
            annotate((VhdlFunctionParameterSignalDeclaration) element);
        } else if (element instanceof VhdlProcedureParameterConstantDeclaration) {
            annotate((VhdlProcedureParameterConstantDeclaration) element);
        } else if (element instanceof VhdlProcedureParameterSignalDeclaration) {
            annotate((VhdlProcedureParameterSignalDeclaration) element);
        } else if (element instanceof VhdlProcedureParameterVariableDeclaration) {
            annotate((VhdlProcedureParameterVariableDeclaration) element);
        } else if (element instanceof VhdlSubprogramParameterFileDeclaration) {
            annotate((VhdlSubprogramParameterFileDeclaration) element);
        }
        // Type declarations:
        else if (element instanceof VhdlFullTypeDeclaration) {
            annotate((VhdlFullTypeDeclaration) element);
        } else if (element instanceof VhdlSubtypeDeclaration) {
            annotate((VhdlSubtypeDeclaration) element);
        }
        // Subprograms (functions, procedures):
        else if (element instanceof VhdlSubprogramDeclaration) {
            annotate((VhdlSubprogramDeclaration) element);
        } else if (element instanceof VhdlSubprogramBody) {
            annotate((VhdlSubprogramBody) element);
        }
        // Other code constructs:
        else if (element instanceof VhdlComponentDeclaration) {
            annotate((VhdlComponentDeclaration) element);
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
                    } else if (declaration instanceof VhdlDesignator) {
                        declaration = declaration.getParent();
                    }
                }
                annotate(id, declaration);
            }
        }
        // Misc.
        else if (element instanceof VhdlLabel) {
            annotate((VhdlLabel) element);
        } else if (element instanceof VhdlAttributeName) {
            annotatePredefinedAttributeName((VhdlAttributeName) element);
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
    private void annotate(@NotNull VhdlEntityDeclaration entity) {
        annotateLabels(entity.getIdentifierList(), VhdlSyntaxHighlighter.PRIMARY_DESIGN_UNIT);
    }

    /**
     * Creates annotations in {@code arch} - both inside the declarative part and inside the
     * statements part.
     *
     * @param arch The {@link VhdlArchitectureBody} to annotate.
     */
    private void annotate(@NotNull VhdlArchitectureBody arch) {
        VhdlRefname archEntity = arch.getRefname();
        VhdlFile parent = (VhdlFile) arch.getParent();
        VhdlEntityDeclaration fileEntity = parent.findChildByClass(VhdlEntityDeclaration.class);
        if (fileEntity != null) {
            String entityName = fileEntity.getIdentifierList().get(0).getName();

            if (archEntity.getText().equalsIgnoreCase(entityName)) {
                annotateText(archEntity, VhdlSyntaxHighlighter.PRIMARY_DESIGN_UNIT);
            } else {
                holder.createWarningAnnotation(archEntity.getTextRange(), "Architecture doesn't implement the entity declared in this file");
            }
        }
        annotateLabels(arch.getIdentifierList(), VhdlSyntaxHighlighter.SECONDARY_DESIGN_UNIT);
    }

    /**
     * Creates annotations in {@code pkg}.
     *
     * @param pkg The {@link VhdlPackageDeclaration} to annotate.
     */
    private void annotate(@NotNull VhdlPackageDeclaration pkg) {
        annotateLabels(pkg.getIdentifierList(), VhdlSyntaxHighlighter.PRIMARY_DESIGN_UNIT);
    }


    /**
     * Creates annotations in {@code packageBody}.
     *
     * @param packageBody The {@link VhdlPackageBody} to annotate.
     */
    private void annotate(VhdlPackageBody packageBody) {
        VhdlFile parent = (VhdlFile) packageBody.getParent();
        VhdlIdentifier bodyPackageName = packageBody.getIdentifierList().get(0);
        VhdlPackageDeclaration filePackage = parent.findChildByClass(VhdlPackageDeclaration.class);
        if (filePackage != null) {
            VhdlIdentifier filePackageName = filePackage.getIdentifierList().get(0);
            if (!IdByNameComparator.match(bodyPackageName, filePackageName)) {
                holder.createWarningAnnotation(bodyPackageName.getTextRange(), "Package body doesn't implement the package declared in this file");
            }
        }
        annotateLabels(packageBody.getIdentifierList(), VhdlSyntaxHighlighter.SECONDARY_DESIGN_UNIT);
    }


    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    /**
     * Creates annotations for {@code genericDeclaration}.
     *
     * @param genericDeclaration The {@link VhdlInterfaceGenericDeclaration} to annotate.
     */
    private void annotate(VhdlInterfaceGenericDeclaration genericDeclaration) {
        for (VhdlIdentifier id : genericDeclaration.getIdentifierList().getIdentifierList()) {
            try {
                checkDuplicates(id);
                annotateText(id, VhdlSyntaxHighlighter.GENERIC);
                if (!id.getText().toLowerCase().endsWith(SUFFIX_GENERIC)) {
                    holder.createWeakWarningAnnotation(id, String.format("Generic names should use '%s' suffix convention", SUFFIX_GENERIC));
                }
            } catch (DeclarationAlreadyExistsException e) {
                holder.createErrorAnnotation(e.getIdentifier(), e.getMessage());
            }
        }
    }

    /**
     * Creates annotations for {@code portDeclaration}.
     *
     * @param portDeclaration The {@link VhdlInterfacePortDeclaration} to annotate.
     */
    private void annotate(VhdlInterfacePortDeclaration portDeclaration) {
        for (VhdlIdentifier id : portDeclaration.getIdentifierList().getIdentifierList()) {
            try {
                checkDuplicates(id);
                annotateText(id, VhdlSyntaxHighlighter.PORT);
            } catch (DeclarationAlreadyExistsException e) {
                holder.createErrorAnnotation(e.getIdentifier(), e.getMessage());
            }
        }
    }

    /**
     * Creates annotations for {@code constantDeclaration}.
     *
     * @param constantDeclaration The {@link VhdlConstantDeclaration} to annotate.
     */
    private void annotate(VhdlConstantDeclaration constantDeclaration) {
        for (VhdlIdentifier id : constantDeclaration.getIdentifierList().getIdentifierList()) {
            try {
                checkDuplicates(id);
                annotateText(id, VhdlSyntaxHighlighter.CONSTANT);
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
     *
     * @param signalDeclaration The {@link VhdlSignalDeclaration} to annotate.
     */
    private void annotate(VhdlSignalDeclaration signalDeclaration) {
        for (VhdlIdentifier id : signalDeclaration.getIdentifierList().getIdentifierList()) {
            try {
                checkDuplicates(id);
                annotateText(id, VhdlSyntaxHighlighter.SIGNAL);
            } catch (DeclarationAlreadyExistsException e) {
                holder.createErrorAnnotation(e.getIdentifier(), e.getMessage());
            }
        }
    }

    /**
     * Creates annotations for {@code variableDeclaration}.
     *
     * @param variableDeclaration The {@link VhdlVariableDeclaration} to annotate.
     */
    private void annotate(VhdlVariableDeclaration variableDeclaration) {
        for (VhdlIdentifier id : variableDeclaration.getIdentifierList().getIdentifierList()) {
            try {
                checkDuplicates(id);
                annotateText(id, VhdlSyntaxHighlighter.VARIABLE);
            } catch (DeclarationAlreadyExistsException e) {
                holder.createErrorAnnotation(e.getIdentifier(), e.getMessage());
            }
        }
    }

    /**
     * Creates annotations for {@code fileDeclaration}.
     *
     * @param fileDeclaration The {@link VhdlFileDeclaration} to annotate.
     */
    private void annotate(VhdlFileDeclaration fileDeclaration) {
        for (VhdlIdentifier id : fileDeclaration.getIdentifierList().getIdentifierList()) {
            try {
                checkDuplicates(id);
                annotateText(id, VhdlSyntaxHighlighter.FILE_VARIABLE);
            } catch (DeclarationAlreadyExistsException e) {
                holder.createErrorAnnotation(e.getIdentifier(), e.getMessage());
            }
        }
    }

    /**
     * Creates annotations for {@code aliasDeclaration}.
     *
     * @param aliasDeclaration The {@link VhdlAliasDeclaration} to annotate.
     */
    private void annotate(VhdlAliasDeclaration aliasDeclaration) {
        VhdlIdentifier id = aliasDeclaration.getIdentifier();
        if (id != null) {
            try {
                checkDuplicates(id);
                annotateText(id, VhdlSyntaxHighlighter.ALIAS);
            } catch (DeclarationAlreadyExistsException e) {
                holder.createErrorAnnotation(e.getIdentifier(), e.getMessage());
            }
        }
    }

    /**
     * Creates annotations for {@code attributeDeclaration}.
     *
     * @param attributeDeclaration The {@link VhdlAttributeDeclaration} to annotate.
     */
    private void annotate(VhdlAttributeDeclaration attributeDeclaration) {
        VhdlIdentifier id = attributeDeclaration.getIdentifier();
        try {
            checkDuplicates(id);
            annotateText(id, VhdlSyntaxHighlighter.ATTRIBUTE);
        } catch (DeclarationAlreadyExistsException e) {
            holder.createErrorAnnotation(e.getIdentifier(), e.getMessage());
        }
    }

    /**
     * Creates annotations for {@code constantDeclaration}.
     *
     * @param constantDeclaration The {@link VhdlFunctionParameterConstantDeclaration} to annotate.
     */
    private void annotate(VhdlFunctionParameterConstantDeclaration constantDeclaration) {
        for (VhdlIdentifier id : constantDeclaration.getIdentifierList().getIdentifierList()) {
            try {
                checkDuplicates(id);
                annotateText(id, VhdlSyntaxHighlighter.SUBPROGRAM_PARAMETER);
            } catch (DeclarationAlreadyExistsException e) {
                holder.createErrorAnnotation(e.getIdentifier(), e.getMessage());
            }
        }
    }

    /**
     * Creates annotations for {@code signalDeclaration}.
     *
     * @param signalDeclaration The {@link VhdlFunctionParameterSignalDeclaration} to annotate.
     */
    private void annotate(VhdlFunctionParameterSignalDeclaration signalDeclaration) {
        for (VhdlIdentifier id : signalDeclaration.getIdentifierList().getIdentifierList()) {
            try {
                checkDuplicates(id);
                annotateText(id, VhdlSyntaxHighlighter.SUBPROGRAM_PARAMETER);
            } catch (DeclarationAlreadyExistsException e) {
                holder.createErrorAnnotation(e.getIdentifier(), e.getMessage());
            }
        }
    }

    /**
     * Creates annotations for {@code constantDeclaration}.
     *
     * @param constantDeclaration The {@link VhdlProcedureParameterConstantDeclaration} to annotate.
     */
    private void annotate(VhdlProcedureParameterConstantDeclaration constantDeclaration) {
        for (VhdlIdentifier id : constantDeclaration.getIdentifierList().getIdentifierList()) {
            try {
                checkDuplicates(id);
                annotateText(id, VhdlSyntaxHighlighter.SUBPROGRAM_PARAMETER);
            } catch (DeclarationAlreadyExistsException e) {
                holder.createErrorAnnotation(e.getIdentifier(), e.getMessage());
            }
        }
    }

    /**
     * Creates annotations for {@code signalDeclaration}.
     *
     * @param signalDeclaration The {@link VhdlProcedureParameterSignalDeclaration} to annotate.
     */
    private void annotate(VhdlProcedureParameterSignalDeclaration signalDeclaration) {
        for (VhdlIdentifier id : signalDeclaration.getIdentifierList().getIdentifierList()) {
            try {
                checkDuplicates(id);
                annotateText(id, VhdlSyntaxHighlighter.SUBPROGRAM_PARAMETER);
            } catch (DeclarationAlreadyExistsException e) {
                holder.createErrorAnnotation(e.getIdentifier(), e.getMessage());
            }
        }
    }

    /**
     * Creates annotations for {@code variableDeclaration}.
     *
     * @param variableDeclaration The {@link VhdlProcedureParameterVariableDeclaration} to annotate.
     */
    private void annotate(VhdlProcedureParameterVariableDeclaration variableDeclaration) {
        for (VhdlIdentifier id : variableDeclaration.getIdentifierList().getIdentifierList()) {
            try {
                checkDuplicates(id);
                annotateText(id, VhdlSyntaxHighlighter.SUBPROGRAM_PARAMETER);
            } catch (DeclarationAlreadyExistsException e) {
                holder.createErrorAnnotation(e.getIdentifier(), e.getMessage());
            }
        }
    }

    /**
     * Creates annotations for {@code fileDeclaration}.
     *
     * @param fileDeclaration The {@link VhdlSubprogramParameterFileDeclaration} to annotate.
     */
    private void annotate(VhdlSubprogramParameterFileDeclaration fileDeclaration) {
        for (VhdlIdentifier id : fileDeclaration.getIdentifierList().getIdentifierList()) {
            try {
                checkDuplicates(id);
                annotateText(id, VhdlSyntaxHighlighter.SUBPROGRAM_PARAMETER);
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
    private void annotate(VhdlFullTypeDeclaration typeDeclaration) {
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
    private void annotate(VhdlSubtypeDeclaration subtypeDeclaration) {
        VhdlIdentifier id = subtypeDeclaration.getIdentifier();
        try {
            checkDuplicates(id);
            annotateText(id, VhdlSyntaxHighlighter.SUBTYPE);
        } catch (DeclarationAlreadyExistsException e) {
            holder.createErrorAnnotation(e.getIdentifier(), e.getMessage());
        }
    }

    /**
     * Creates annotations for {@code subprogramDeclaration}.
     *
     * @param subprogramDeclaration The {@link VhdlSubprogramDeclaration} to annotate.
     */
    private void annotate(VhdlSubprogramDeclaration subprogramDeclaration) {
        VhdlIdentifier id = subprogramDeclaration.getSubprogramSpecification().getDesignator().getIdentifier();
        if (id == null) {
            // Subprogram is an operator (has symbol instead of identifier), e.g. "and", "**", "-"...
            // We consider it as string.
            return;
        }
        try {
            checkDuplicates(id);
            annotateText(id, VhdlSyntaxHighlighter.SUBPROGRAM_DECLARATION);
        } catch (DeclarationAlreadyExistsException e) {
            holder.createErrorAnnotation(e.getIdentifier(), e.getMessage());
        }
    }

    /**
     * Creates annotations for {@code subprogramBody}.
     *
     * @param subprogramBody The {@link VhdlSubprogramDeclaration} to annotate.
     */
    private void annotate(VhdlSubprogramBody subprogramBody) {
        VhdlIdentifier startId = subprogramBody.getSubprogramSpecification().getDesignator().getIdentifier();
        if (startId == null) {
            // Subprogram is an operator (has symbol instead of identifier), e.g. "and", "**", "-"...
            // We consider it as string.
            return;
        }
        VhdlDesignator endDesignator = subprogramBody.getDesignator();
        List<VhdlIdentifier> ids = new ArrayList<>(2);
        ids.add(startId);
        if (endDesignator != null) {
            VhdlIdentifier endId = endDesignator.getIdentifier();
            ids.add(endId);
        }
        try {
            checkDuplicates(startId);
            annotateLabels(ids, VhdlSyntaxHighlighter.SUBPROGRAM_DECLARATION);
        } catch (DeclarationAlreadyExistsException e) {
            holder.createErrorAnnotation(e.getIdentifier(), e.getMessage());
        }
    }

    /**
     * Creates annotations for {@code componentDeclaration}.
     *
     * @param componentDeclaration The {@link VhdlComponentDeclaration} to annotate.
     */
    private void annotate(VhdlComponentDeclaration componentDeclaration) {
        annotateLabels(componentDeclaration.getIdentifierList(), VhdlSyntaxHighlighter.PRIMARY_DESIGN_UNIT);
    }

    /**
     * Creates annotations for {@code id} based on the annotation of {@code declaration}
     *
     * @param id          The {@link VhdlIdentifier} to annotate.
     * @param declaration The {@link PsiElement} to base the annotation of {@code id} on.
     */
    private void annotate(VhdlIdentifier id, PsiElement declaration) {
        if (declaration instanceof VhdlInterfaceGenericDeclaration) {
            annotateText(id, VhdlSyntaxHighlighter.GENERIC);
        } else if (declaration instanceof VhdlInterfacePortDeclaration) {
            annotateText(id, VhdlSyntaxHighlighter.PORT);
        } else if (declaration instanceof VhdlConstantDeclaration) {
            annotateText(id, VhdlSyntaxHighlighter.CONSTANT);
        } else if (declaration instanceof VhdlSignalDeclaration) {
            annotateText(id, VhdlSyntaxHighlighter.SIGNAL);
        } else if (declaration instanceof VhdlVariableDeclaration) {
            annotateText(id, VhdlSyntaxHighlighter.VARIABLE);
        } else if (declaration instanceof VhdlFileDeclaration) {
            annotateText(id, VhdlSyntaxHighlighter.FILE_VARIABLE);
        } else if (declaration instanceof VhdlAliasDeclaration) {
            annotateText(id, VhdlSyntaxHighlighter.ALIAS);
        } else if (declaration instanceof VhdlAttributeDeclaration) {
            annotateText(id, VhdlSyntaxHighlighter.ATTRIBUTE);
        } else if (declaration instanceof VhdlFullTypeDeclaration) {
            annotateText(id, VhdlSyntaxHighlighter.TYPE);
        } else if (declaration instanceof VhdlSubtypeDeclaration) {
            annotateText(id, VhdlSyntaxHighlighter.SUBTYPE);
        } else if (VhdlIdentifierPsiImplUtil.isSubprogramParameter(declaration)) {
            annotateText(id, VhdlSyntaxHighlighter.SUBPROGRAM_PARAMETER);
        } else if (declaration instanceof VhdlSubprogramSpecification) {
            annotateText(id, VhdlSyntaxHighlighter.SUBPROGRAM_CALL);
        } else if (declaration instanceof VhdlComponentDeclaration) {
            annotateText(id, VhdlSyntaxHighlighter.PRIMARY_DESIGN_UNIT);
        } else if (declaration instanceof VhdlEntityDeclaration) {
            annotateText(id, VhdlSyntaxHighlighter.PRIMARY_DESIGN_UNIT);
        } else if (declaration instanceof VhdlArchitectureBody) {
            annotateText(id, VhdlSyntaxHighlighter.SECONDARY_DESIGN_UNIT);
        } else if (declaration instanceof VhdlPackageDeclaration) {
            annotateText(id, VhdlSyntaxHighlighter.PRIMARY_DESIGN_UNIT);
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
                    break;
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

    /**
     * Creates annotations for {@code label} and its matching {@link VhdlLabel}, if one exists.
     *
     * @param label The {@link VhdlLabel} to annotate.
     * @see #annotateLabels(List, TextAttributesKey)
     */
    private void annotate(VhdlLabel label) {
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
     * Creates annotations for {@code attributeName} if it's a predefined attribute.
     * User defined attributes are handled by {@link #annotateText(PsiElement, TextAttributesKey)}.
     *
     * @param attributeName The {@link VhdlAttributeName} to annotate.
     */
    private void annotatePredefinedAttributeName(VhdlAttributeName attributeName) {
        if (attributeName.getRangeType() != null) {
            annotateText(attributeName.getRangeType(), VhdlSyntaxHighlighter.ATTRIBUTE);
            return;
        }
        List<VhdlIdentifier> ids = attributeName.getIdentifierList();
        for (VhdlIdentifier id : ids) {
            PsiElement prevSibling = id.getPrevSibling();
            if (prevSibling == null) continue;

            if (prevSibling instanceof PsiWhiteSpace) {
                prevSibling = prevSibling.getPrevSibling();
            }
            if (prevSibling == null) continue;

            IElementType prevSiblingType = prevSibling.getNode().getElementType();
            if (prevSiblingType == VhdlTypes.T_APOSTROPHE) {
                // This id is the attribute itself, let's check if it's predefined.
                String idName = id.getName();
                for (String predefinedAttribute : VhdlSyntaxHighlighter.PREDEFINED_ATTRIBUTES) {
                    if (predefinedAttribute.equalsIgnoreCase(idName)) {
                        annotateText(id, VhdlSyntaxHighlighter.ATTRIBUTE);
                        return;
                    }
                }
            }
        }
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
}