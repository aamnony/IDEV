package com.github.aamnony.idev.vhdl.psi.impl;

import com.github.aamnony.idev.vhdl.navigation.VhdlArchitecturePresentation;
import com.github.aamnony.idev.vhdl.navigation.VhdlBlockPresentation;
import com.github.aamnony.idev.vhdl.navigation.VhdlComponentPresentation;
import com.github.aamnony.idev.vhdl.navigation.VhdlConstantPresentation;
import com.github.aamnony.idev.vhdl.navigation.VhdlEntityPresentation;
import com.github.aamnony.idev.vhdl.navigation.VhdlGeneratePresentation;
import com.github.aamnony.idev.vhdl.navigation.VhdlGenericPresentation;
import com.github.aamnony.idev.vhdl.navigation.VhdlIdentifierPresentation;
import com.github.aamnony.idev.vhdl.navigation.VhdlInstantiationPresentation;
import com.github.aamnony.idev.vhdl.navigation.VhdlPackageBodyPresentation;
import com.github.aamnony.idev.vhdl.navigation.VhdlPackagePresentation;
import com.github.aamnony.idev.vhdl.navigation.VhdlPortPresentation;
import com.github.aamnony.idev.vhdl.navigation.VhdlProcessPresentation;
import com.github.aamnony.idev.vhdl.navigation.VhdlSignalPresentation;
import com.github.aamnony.idev.vhdl.navigation.VhdlSubprogramPresentation;
import com.github.aamnony.idev.vhdl.navigation.VhdlVariablePresentation;
import com.github.aamnony.idev.vhdl.psi.VhdlElementFactory;
import com.github.aamnony.idev.vhdl.psi.VhdlFile;
import com.github.aamnony.idev.vhdl.psi.tree.VhdlPsiTreeUtil;
import com.github.aamnony.idev.vhdl.navigation.VhdlArchitecturePresentation;
import com.github.aamnony.idev.vhdl.navigation.VhdlBlockPresentation;
import com.github.aamnony.idev.vhdl.navigation.VhdlComponentPresentation;
import com.github.aamnony.idev.vhdl.navigation.VhdlConstantPresentation;
import com.github.aamnony.idev.vhdl.navigation.VhdlEntityPresentation;
import com.github.aamnony.idev.vhdl.navigation.VhdlGeneratePresentation;
import com.github.aamnony.idev.vhdl.navigation.VhdlGenericPresentation;
import com.github.aamnony.idev.vhdl.navigation.VhdlIdentifierPresentation;
import com.github.aamnony.idev.vhdl.navigation.VhdlInstantiationPresentation;
import com.github.aamnony.idev.vhdl.navigation.VhdlPackageBodyPresentation;
import com.github.aamnony.idev.vhdl.navigation.VhdlPackagePresentation;
import com.github.aamnony.idev.vhdl.navigation.VhdlPortPresentation;
import com.github.aamnony.idev.vhdl.navigation.VhdlProcessPresentation;
import com.github.aamnony.idev.vhdl.navigation.VhdlSignalPresentation;
import com.github.aamnony.idev.vhdl.navigation.VhdlSubprogramPresentation;
import com.github.aamnony.idev.vhdl.navigation.VhdlVariablePresentation;
import com.github.aamnony.idev.vhdl.psi.VhdlAliasDeclaration;
import com.github.aamnony.idev.vhdl.psi.VhdlArchitectureBody;
import com.github.aamnony.idev.vhdl.psi.VhdlAttributeDeclaration;
import com.github.aamnony.idev.vhdl.psi.VhdlBlockStatement;
import com.github.aamnony.idev.vhdl.psi.VhdlComponentDeclaration;
import com.github.aamnony.idev.vhdl.psi.VhdlComponentInstantiationStatement;
import com.github.aamnony.idev.vhdl.psi.VhdlConstantDeclaration;
import com.github.aamnony.idev.vhdl.psi.VhdlDesignator;
import com.github.aamnony.idev.vhdl.psi.VhdlElementFactory;
import com.github.aamnony.idev.vhdl.psi.VhdlEntityDeclaration;
import com.github.aamnony.idev.vhdl.psi.VhdlFile;
import com.github.aamnony.idev.vhdl.psi.VhdlFullTypeDeclaration;
import com.github.aamnony.idev.vhdl.psi.VhdlFunctionParameterConstantDeclaration;
import com.github.aamnony.idev.vhdl.psi.VhdlFunctionParameterSignalDeclaration;
import com.github.aamnony.idev.vhdl.psi.VhdlGenerateStatement;
import com.github.aamnony.idev.vhdl.psi.VhdlIdentifier;
import com.github.aamnony.idev.vhdl.psi.VhdlIdentifierList;
import com.github.aamnony.idev.vhdl.psi.VhdlInterfaceGenericDeclaration;
import com.github.aamnony.idev.vhdl.psi.VhdlInterfacePortDeclaration;
import com.github.aamnony.idev.vhdl.psi.VhdlPackageBody;
import com.github.aamnony.idev.vhdl.psi.VhdlPackageDeclaration;
import com.github.aamnony.idev.vhdl.psi.VhdlProcedureParameterConstantDeclaration;
import com.github.aamnony.idev.vhdl.psi.VhdlProcedureParameterSignalDeclaration;
import com.github.aamnony.idev.vhdl.psi.VhdlProcedureParameterVariableDeclaration;
import com.github.aamnony.idev.vhdl.psi.VhdlProcessStatement;
import com.github.aamnony.idev.vhdl.psi.VhdlSignalDeclaration;
import com.github.aamnony.idev.vhdl.psi.VhdlSubprogramBody;
import com.github.aamnony.idev.vhdl.psi.VhdlSubprogramDeclaration;
import com.github.aamnony.idev.vhdl.psi.VhdlSubprogramParameterFileDeclaration;
import com.github.aamnony.idev.vhdl.psi.VhdlSubprogramSpecification;
import com.github.aamnony.idev.vhdl.psi.VhdlSubtypeDeclaration;
import com.github.aamnony.idev.vhdl.psi.VhdlTypes;
import com.github.aamnony.idev.vhdl.psi.VhdlVariableDeclaration;
import com.github.aamnony.idev.vhdl.psi.tree.VhdlPsiTreeUtil;
import com.intellij.navigation.ItemPresentation;
import com.intellij.psi.PsiComment;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiReference;
import com.intellij.psi.PsiWhiteSpace;
import com.intellij.psi.impl.source.resolve.reference.ReferenceProvidersRegistry;

import org.jetbrains.annotations.NotNull;

public class VhdlPsiImplUtil {

    public static PsiElement setName(VhdlIdentifier id, String newName) {
        VhdlElementFactory.rename(id, newName);
        return id;
    }

    public static String getName(VhdlIdentifier id) {
        return id.getFirstChild().getText();
    }

    public static PsiElement getNameIdentifier(VhdlIdentifier id) {
        return id.getFirstChild();
//        ASTNode node = id.getNode();//.findChildByType(VhdlTypes.ID);
//        if (node != null) {
//            return node.getPsi();
//        } else {
//            return null;
//        }
    }

    @NotNull
    public static ItemPresentation getPresentation(VhdlIdentifier id) {
        return new VhdlIdentifierPresentation(id);
    }

    @NotNull
    public static ItemPresentation getPresentation(VhdlEntityDeclaration entityDeclaration) {
        return new VhdlEntityPresentation(entityDeclaration);
    }

    @NotNull
    public static ItemPresentation getPresentation(VhdlArchitectureBody architectureBody) {
        return new VhdlArchitecturePresentation(architectureBody);
    }

    @NotNull
    public static ItemPresentation getPresentation(VhdlPackageDeclaration packageDeclaration) {
        return new VhdlPackagePresentation(packageDeclaration);
    }

    @NotNull
    public static ItemPresentation getPresentation(VhdlPackageBody packageBody) {
        return new VhdlPackageBodyPresentation(packageBody);
    }

    @NotNull
    public static ItemPresentation getPresentation(VhdlBlockStatement block) {
        return new VhdlBlockPresentation(block);
    }

    @NotNull
    public static ItemPresentation getPresentation(VhdlGenerateStatement generate) {
        return new VhdlGeneratePresentation(generate);
    }

    @NotNull
    public static ItemPresentation getPresentation(VhdlInterfaceGenericDeclaration declaration, int index) {
        return new VhdlGenericPresentation(declaration, index);
    }

    @NotNull
    public static ItemPresentation getPresentation(VhdlInterfacePortDeclaration declaration, int index) {
        return new VhdlPortPresentation(declaration, index);
    }

    @NotNull
    public static ItemPresentation getPresentation(VhdlConstantDeclaration declaration, int index) {
        return new VhdlConstantPresentation(declaration, index);
    }

    @NotNull
    public static ItemPresentation getPresentation(VhdlSignalDeclaration declaration, int index) {
        return new VhdlSignalPresentation(declaration, index);
    }

    @NotNull
    public static ItemPresentation getPresentation(VhdlVariableDeclaration declaration, int index) {
        return new VhdlVariablePresentation(declaration, index);
    }

    @NotNull
    public static ItemPresentation getPresentation(VhdlComponentDeclaration declaration) {
        return new VhdlComponentPresentation(declaration);
    }

    @NotNull
    public static ItemPresentation getPresentation(VhdlComponentInstantiationStatement instantiation) {
        return new VhdlInstantiationPresentation(instantiation);
    }

    @NotNull
    public static ItemPresentation getPresentation(VhdlSubprogramSpecification subprogram) {
        return new VhdlSubprogramPresentation(subprogram);
    }

    @NotNull
    public static ItemPresentation getPresentation(VhdlProcessStatement process) {
        return new VhdlProcessPresentation(process);
    }

    /**
     * Returns whether {@code id} is mentioned or declared.
     *
     * @param id The {@link VhdlIdentifier} to check.
     * @return {@code true} if {@code id} is declared, <br>
     * {@code false} if {@code id} is mentioned.
     */
    public static boolean isDeclared(@NotNull VhdlIdentifier id) {
        PsiElement parent = id.getParent();
        if (parent instanceof VhdlIdentifierList
                || parent instanceof VhdlFullTypeDeclaration
                || parent instanceof VhdlSubtypeDeclaration
                || parent instanceof VhdlSubprogramDeclaration
                || parent instanceof VhdlAliasDeclaration
                || parent instanceof VhdlAttributeDeclaration) {
            return true;
        }

        if (parent instanceof VhdlDesignator) {
            // Subprogram (function/procedure).
            return parent.getParent().getParent() instanceof VhdlSubprogramDeclaration;
        }
        // The following are code constructs that might have end labels. We consider the start label as the declaration.
        if (parent instanceof VhdlEntityDeclaration
                || parent instanceof VhdlArchitectureBody
                || parent instanceof VhdlPackageDeclaration
                || parent instanceof VhdlPackageBody
                || parent instanceof VhdlComponentDeclaration) {

            PsiElement nextSibling = id.getNextSibling();
            while (true) {
                if (nextSibling.getNode().getElementType() == VhdlTypes.T_SEMICOLON) {
                    return false;
                } else if (!(nextSibling instanceof PsiWhiteSpace)
                        && !(nextSibling instanceof PsiComment)) {
                    return true;
                }
                nextSibling = nextSibling.getNextSibling();
            }
        }
        return false;
    }

    @NotNull
    public static PsiElement[] getScopes(@NotNull VhdlIdentifier id) {
        /*
        Walk up the PSI tree until reaching one of the following, and return it, unless stated otherwise:
            - entity
            - architecture - return it and its entity
            - subprogram declaration
            - subprogram body
            - package
            - package body - return it and its package. (TODO: handle not full declarations [e.g. constant without value in package, which the body sets)
            - process

         */
        PsiElement parent = id.getParent();
        while (!(parent instanceof VhdlFile)) {
            parent = parent.getParent();
            if (parent instanceof VhdlEntityDeclaration) {
                return new PsiElement[]{parent};
            } else if (parent instanceof VhdlArchitectureBody) {
                VhdlEntityDeclaration entity = VhdlPsiTreeUtil.getEntity(((VhdlArchitectureBody) parent));
                return new PsiElement[]{parent, entity};
            } else if (parent instanceof VhdlSubprogramDeclaration) {
                return new PsiElement[]{parent};
            } else if (parent instanceof VhdlSubprogramBody) {
                return new PsiElement[]{parent};
            } else if (parent instanceof VhdlPackageDeclaration) {
                return new PsiElement[]{parent};
            } else if (parent instanceof VhdlPackageBody) {
                VhdlPackageDeclaration packageDeclaration = VhdlPsiTreeUtil.getPackage(((VhdlPackageBody) parent));
                return new PsiElement[]{parent, packageDeclaration};
            } else if (parent instanceof VhdlProcessStatement) {
                return new PsiElement[]{parent};
            }
        }
        return new PsiElement[]{parent};
    }

    @NotNull
    public static PsiReference[] getReferences(PsiElement element) {
        return ReferenceProvidersRegistry.getReferencesFromProviders(element);
    }


    /**
     * Checks if the {@code declaration} is of a subprogram (function/procedure) parameter.
     *
     * @param declaration The {@link PsiElement} to check the type of.
     * @return Whether or not the {@code declaration} is of a subprogram (function/procedure) parameter.
     */
    public static boolean isSubprogramParameter(PsiElement declaration) {
        return declaration instanceof VhdlFunctionParameterConstantDeclaration
                || declaration instanceof VhdlFunctionParameterSignalDeclaration
                || declaration instanceof VhdlProcedureParameterConstantDeclaration
                || declaration instanceof VhdlProcedureParameterSignalDeclaration
                || declaration instanceof VhdlProcedureParameterVariableDeclaration
                || declaration instanceof VhdlSubprogramParameterFileDeclaration;
    }

}