package com.github.amnonya.hdleditor.vhdl.psi.impl;

import com.github.amnonya.hdleditor.vhdl.icons.VhdlIcons;
import com.github.amnonya.hdleditor.vhdl.psi.VhdlArchitectureBody;
import com.github.amnonya.hdleditor.vhdl.psi.VhdlDesignator;
import com.github.amnonya.hdleditor.vhdl.psi.VhdlElementFactory;
import com.github.amnonya.hdleditor.vhdl.psi.VhdlEntityDeclaration;
import com.github.amnonya.hdleditor.vhdl.psi.VhdlFile;
import com.github.amnonya.hdleditor.vhdl.psi.VhdlFullTypeDeclaration;
import com.github.amnonya.hdleditor.vhdl.psi.VhdlIdentifier;
import com.github.amnonya.hdleditor.vhdl.psi.VhdlIdentifierList;
import com.github.amnonya.hdleditor.vhdl.psi.VhdlPackageBody;
import com.github.amnonya.hdleditor.vhdl.psi.VhdlPackageDeclaration;
import com.github.amnonya.hdleditor.vhdl.psi.VhdlProcessStatement;
import com.github.amnonya.hdleditor.vhdl.psi.VhdlSubprogramBody;
import com.github.amnonya.hdleditor.vhdl.psi.VhdlSubprogramDeclaration;
import com.github.amnonya.hdleditor.vhdl.psi.VhdlSubtypeDeclaration;
import com.github.amnonya.hdleditor.vhdl.psi.VhdlTypes;
import com.github.amnonya.hdleditor.vhdl.psi.tree.VhdlPsiTreeUtil;
import com.intellij.navigation.ItemPresentation;
import com.intellij.psi.PsiComment;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiReference;
import com.intellij.psi.PsiWhiteSpace;
import com.intellij.psi.impl.source.resolve.reference.ReferenceProvidersRegistry;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import javax.swing.Icon;

public class VhdlIdentifierPsiImplUtil {
    //    public static String getKey(VhdlIdentifier element) {
//        ASTNode keyNode = element.getNode().findChildByType(VhdlTypes.IDENTIFIER);
//        if (keyNode != null) {
//            // IMPORTANT: Convert embedded escaped spaces to simple spaces
//            return keyNode.getText().replaceAll("\\\\ ", " ");
//        } else {
//            return null;
//        }
//    }
//
//    public static String getValue(VhdlIdentifier element) {
//        ASTNode valueNode = element.getNode().findChildByType(SimpleTypes.VALUE);
//        if (valueNode != null) {
//            return valueNode.getText();
//        } else {
//            return null;
//        }
//    }
//
//    public static String getName(VhdlIdentifier id) {
//        return id.getText();
//    }
//
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

    public static ItemPresentation getPresentation(final VhdlIdentifier id) {
        return new ItemPresentation() {
            @Nullable
            @Override
            public String getPresentableText() {
                return id.getName();
            }

            @Override
            public String getLocationString() {
                return id.getContainingFile().getName();
            }

            @Override
            public Icon getIcon(boolean unused) {
                return VhdlIcons.FILE;
            }
        };
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
                || parent instanceof VhdlSubprogramDeclaration) {
            return true;
        }

        if (parent instanceof VhdlDesignator) {
            // Subprogram (function/procedure).
            return parent.getParent().getParent() instanceof VhdlSubprogramDeclaration;
        }

        if (parent instanceof VhdlEntityDeclaration
                || parent instanceof VhdlArchitectureBody
                || parent instanceof VhdlPackageDeclaration
                || parent instanceof VhdlPackageBody) {

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
}