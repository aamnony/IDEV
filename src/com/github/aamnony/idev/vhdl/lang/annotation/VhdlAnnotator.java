package com.github.aamnony.idev.vhdl.lang.annotation;

import com.github.aamnony.idev.vhdl.exceptions.DeclarationAlreadyExistsException;
import com.github.aamnony.idev.vhdl.highlighting.VhdlHighlightingColors;
import com.github.aamnony.idev.vhdl.highlighting.VhdlSyntaxHighlighter;
import com.github.aamnony.idev.vhdl.psi.VhdlAttributeDeclaration;
import com.github.aamnony.idev.vhdl.psi.VhdlDesignator;
import com.github.aamnony.idev.vhdl.psi.VhdlEntityDeclaration;
import com.github.aamnony.idev.vhdl.psi.VhdlFullTypeDeclaration;
import com.github.aamnony.idev.vhdl.psi.VhdlFunctionParameterConstantDeclaration;
import com.github.aamnony.idev.vhdl.psi.VhdlFunctionParameterSignalDeclaration;
import com.github.aamnony.idev.vhdl.psi.VhdlIdentifier;
import com.github.aamnony.idev.vhdl.psi.VhdlInterfaceGenericDeclaration;
import com.github.aamnony.idev.vhdl.psi.VhdlInterfacePortDeclaration;
import com.github.aamnony.idev.vhdl.psi.VhdlLabel;
import com.github.aamnony.idev.vhdl.psi.VhdlPackageBody;
import com.github.aamnony.idev.vhdl.psi.VhdlPackageDeclaration;
import com.github.aamnony.idev.vhdl.psi.VhdlProcedureParameterConstantDeclaration;
import com.github.aamnony.idev.vhdl.psi.VhdlProcedureParameterSignalDeclaration;
import com.github.aamnony.idev.vhdl.psi.VhdlProcedureParameterVariableDeclaration;
import com.github.aamnony.idev.vhdl.psi.VhdlRefname;
import com.github.aamnony.idev.vhdl.psi.VhdlSignalDeclaration;
import com.github.aamnony.idev.vhdl.psi.VhdlSubprogramBody;
import com.github.aamnony.idev.vhdl.psi.VhdlSubprogramDeclaration;
import com.github.aamnony.idev.vhdl.psi.VhdlSubprogramSpecification;
import com.github.aamnony.idev.vhdl.psi.VhdlSubtypeDeclaration;
import com.github.aamnony.idev.vhdl.psi.impl.VhdlPsiImplUtil;
import com.github.aamnony.idev.vhdl.IdByNameComparator;
import com.github.aamnony.idev.vhdl.exceptions.DeclarationAlreadyExistsException;
import com.github.aamnony.idev.vhdl.highlighting.VhdlHighlightingColors;
import com.github.aamnony.idev.vhdl.highlighting.VhdlSyntaxHighlighter;
import com.github.aamnony.idev.vhdl.psi.VhdlAliasDeclaration;
import com.github.aamnony.idev.vhdl.psi.VhdlArchitectureBody;
import com.github.aamnony.idev.vhdl.psi.VhdlAttributeDeclaration;
import com.github.aamnony.idev.vhdl.psi.VhdlAttributeName;
import com.github.aamnony.idev.vhdl.psi.VhdlComponentDeclaration;
import com.github.aamnony.idev.vhdl.psi.VhdlConstantDeclaration;
import com.github.aamnony.idev.vhdl.psi.VhdlDesignator;
import com.github.aamnony.idev.vhdl.psi.VhdlEntityDeclaration;
import com.github.aamnony.idev.vhdl.psi.VhdlFile;
import com.github.aamnony.idev.vhdl.psi.VhdlFileDeclaration;
import com.github.aamnony.idev.vhdl.psi.VhdlFullTypeDeclaration;
import com.github.aamnony.idev.vhdl.psi.VhdlFunctionParameterConstantDeclaration;
import com.github.aamnony.idev.vhdl.psi.VhdlFunctionParameterSignalDeclaration;
import com.github.aamnony.idev.vhdl.psi.VhdlIdentifier;
import com.github.aamnony.idev.vhdl.psi.VhdlIdentifierList;
import com.github.aamnony.idev.vhdl.psi.VhdlInterfaceGenericDeclaration;
import com.github.aamnony.idev.vhdl.psi.VhdlInterfacePortDeclaration;
import com.github.aamnony.idev.vhdl.psi.VhdlLabel;
import com.github.aamnony.idev.vhdl.psi.VhdlPackageBody;
import com.github.aamnony.idev.vhdl.psi.VhdlPackageDeclaration;
import com.github.aamnony.idev.vhdl.psi.VhdlProcedureParameterConstantDeclaration;
import com.github.aamnony.idev.vhdl.psi.VhdlProcedureParameterSignalDeclaration;
import com.github.aamnony.idev.vhdl.psi.VhdlProcedureParameterVariableDeclaration;
import com.github.aamnony.idev.vhdl.psi.VhdlRefname;
import com.github.aamnony.idev.vhdl.psi.VhdlSignalDeclaration;
import com.github.aamnony.idev.vhdl.psi.VhdlSubprogramBody;
import com.github.aamnony.idev.vhdl.psi.VhdlSubprogramDeclaration;
import com.github.aamnony.idev.vhdl.psi.VhdlSubprogramParameterFileDeclaration;
import com.github.aamnony.idev.vhdl.psi.VhdlSubprogramSpecification;
import com.github.aamnony.idev.vhdl.psi.VhdlSubtypeDeclaration;
import com.github.aamnony.idev.vhdl.psi.VhdlTypes;
import com.github.aamnony.idev.vhdl.psi.VhdlVariableDeclaration;
import com.github.aamnony.idev.vhdl.psi.impl.VhdlPsiImplUtil;
import com.github.aamnony.idev.vhdl.psi.tree.VhdlPsiTreeUtil;
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
        annotateLabels(entity.getIdentifierList(), VhdlHighlightingColors.PRIMARY_DESIGN_UNIT);
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
                annotateText(archEntity, VhdlHighlightingColors.PRIMARY_DESIGN_UNIT);
            } else {
                holder.createWarningAnnotation(archEntity.getTextRange(), "Architecture doesn't implement the entity declared in this file");
            }
        }
        annotateLabels(arch.getIdentifierList(), VhdlHighlightingColors.SECONDARY_DESIGN_UNIT);
    }

    /**
     * Creates annotations in {@code pkg}.
     *
     * @param pkg The {@link VhdlPackageDeclaration} to annotate.
     */
    private void annotate(@NotNull VhdlPackageDeclaration pkg) {
        annotateLabels(pkg.getIdentifierList(), VhdlHighlightingColors.PRIMARY_DESIGN_UNIT);
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
        annotateLabels(packageBody.getIdentifierList(), VhdlHighlightingColors.SECONDARY_DESIGN_UNIT);
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
                annotateText(id, VhdlHighlightingColors.GENERIC);
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
                annotateText(id, VhdlHighlightingColors.PORT);
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
                annotateText(id, VhdlHighlightingColors.CONSTANT);
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
                annotateText(id, VhdlHighlightingColors.SIGNAL);
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
                annotateText(id, VhdlHighlightingColors.VARIABLE);
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
                annotateText(id, VhdlHighlightingColors.FILE_VARIABLE);
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
                annotateText(id, VhdlHighlightingColors.ALIAS);
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
            annotateText(id, VhdlHighlightingColors.ATTRIBUTE);
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
                annotateText(id, VhdlHighlightingColors.SUBPROGRAM_PARAMETER);
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
                annotateText(id, VhdlHighlightingColors.SUBPROGRAM_PARAMETER);
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
                annotateText(id, VhdlHighlightingColors.SUBPROGRAM_PARAMETER);
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
                annotateText(id, VhdlHighlightingColors.SUBPROGRAM_PARAMETER);
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
                annotateText(id, VhdlHighlightingColors.SUBPROGRAM_PARAMETER);
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
                annotateText(id, VhdlHighlightingColors.SUBPROGRAM_PARAMETER);
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
            annotateText(id, VhdlHighlightingColors.TYPE);
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
            annotateText(id, VhdlHighlightingColors.SUBTYPE);
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
            annotateText(id, VhdlHighlightingColors.SUBPROGRAM_DECLARATION);
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
            annotateLabels(ids, VhdlHighlightingColors.SUBPROGRAM_DECLARATION);
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
        annotateLabels(componentDeclaration.getIdentifierList(), VhdlHighlightingColors.PRIMARY_DESIGN_UNIT);
    }

    /**
     * Creates annotations for {@code id} based on the annotation of {@code declaration}
     *
     * @param id          The {@link VhdlIdentifier} to annotate.
     * @param declaration The {@link PsiElement} to base the annotation of {@code id} on.
     */
    private void annotate(VhdlIdentifier id, PsiElement declaration) {
        if (declaration instanceof VhdlInterfaceGenericDeclaration) {
            annotateText(id, VhdlHighlightingColors.GENERIC);
        } else if (declaration instanceof VhdlInterfacePortDeclaration) {
            annotateText(id, VhdlHighlightingColors.PORT);
        } else if (declaration instanceof VhdlConstantDeclaration) {
            annotateText(id, VhdlHighlightingColors.CONSTANT);
        } else if (declaration instanceof VhdlSignalDeclaration) {
            annotateText(id, VhdlHighlightingColors.SIGNAL);
        } else if (declaration instanceof VhdlVariableDeclaration) {
            annotateText(id, VhdlHighlightingColors.VARIABLE);
        } else if (declaration instanceof VhdlFileDeclaration) {
            annotateText(id, VhdlHighlightingColors.FILE_VARIABLE);
        } else if (declaration instanceof VhdlAliasDeclaration) {
            annotateText(id, VhdlHighlightingColors.ALIAS);
        } else if (declaration instanceof VhdlAttributeDeclaration) {
            annotateText(id, VhdlHighlightingColors.ATTRIBUTE);
        } else if (declaration instanceof VhdlFullTypeDeclaration) {
            annotateText(id, VhdlHighlightingColors.TYPE);
        } else if (declaration instanceof VhdlSubtypeDeclaration) {
            annotateText(id, VhdlHighlightingColors.SUBTYPE);
        } else if (VhdlPsiImplUtil.isSubprogramParameter(declaration)) {
            annotateText(id, VhdlHighlightingColors.SUBPROGRAM_PARAMETER);
        } else if (declaration instanceof VhdlSubprogramSpecification) {
            annotateText(id, VhdlHighlightingColors.SUBPROGRAM_CALL);
        } else if (declaration instanceof VhdlComponentDeclaration) {
            annotateText(id, VhdlHighlightingColors.PRIMARY_DESIGN_UNIT);
        } else if (declaration instanceof VhdlEntityDeclaration) {
            annotateText(id, VhdlHighlightingColors.PRIMARY_DESIGN_UNIT);
        } else if (declaration instanceof VhdlArchitectureBody) {
            annotateText(id, VhdlHighlightingColors.SECONDARY_DESIGN_UNIT);
        } else if (declaration instanceof VhdlPackageDeclaration) {
            annotateText(id, VhdlHighlightingColors.PRIMARY_DESIGN_UNIT);
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
        annotateLabels(ids, VhdlHighlightingColors.LABEL);
    }

    /**
     * Creates annotations for {@code attributeName} if it's a predefined attribute.
     * User defined attributes are handled by {@link #annotateText(PsiElement, TextAttributesKey)}.
     *
     * @param attributeName The {@link VhdlAttributeName} to annotate.
     */
    private void annotatePredefinedAttributeName(VhdlAttributeName attributeName) {
        if (attributeName.getRangeType() != null) {
            annotateText(attributeName.getRangeType(), VhdlHighlightingColors.ATTRIBUTE);
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
                        annotateText(id, VhdlHighlightingColors.ATTRIBUTE);
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