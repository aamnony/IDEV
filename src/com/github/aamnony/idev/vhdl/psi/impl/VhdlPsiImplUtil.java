package com.github.aamnony.idev.vhdl.psi.impl;

import com.github.aamnony.idev.utils.StringUtils;
import com.github.aamnony.idev.vhdl.VhdlIcons;
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
import com.github.aamnony.idev.vhdl.psi.VhdlInstantiatedUnit;
import com.github.aamnony.idev.vhdl.psi.VhdlInterfaceGenericDeclaration;
import com.github.aamnony.idev.vhdl.psi.VhdlInterfacePortDeclaration;
import com.github.aamnony.idev.vhdl.psi.VhdlPackageBody;
import com.github.aamnony.idev.vhdl.psi.VhdlPackageDeclaration;
import com.github.aamnony.idev.vhdl.psi.VhdlProcedureParameterConstantDeclaration;
import com.github.aamnony.idev.vhdl.psi.VhdlProcedureParameterSignalDeclaration;
import com.github.aamnony.idev.vhdl.psi.VhdlProcedureParameterVariableDeclaration;
import com.github.aamnony.idev.vhdl.psi.VhdlProcessStatement;
import com.github.aamnony.idev.vhdl.psi.VhdlRefname;
import com.github.aamnony.idev.vhdl.psi.VhdlSelectedName;
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
import com.intellij.psi.tree.IElementType;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.LinkedList;
import java.util.List;

import javax.swing.Icon;

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

    /////////////////////////////////////////

    @NotNull
    public static String getType(VhdlIdentifier id) {
        String type = "";
        if (id.isDeclared()) {
            PsiElement declarationWrapper = getDeclarationWrapper(id);
            if (declarationWrapper instanceof VhdlEntityDeclaration) {
                type = ((VhdlEntityDeclaration) declarationWrapper).getType();
            } else if (declarationWrapper instanceof VhdlArchitectureBody) {
                type = ((VhdlArchitectureBody) declarationWrapper).getType();
            } else if (declarationWrapper instanceof VhdlPackageDeclaration) {
                type = ((VhdlPackageDeclaration) declarationWrapper).getType();
            } else if (declarationWrapper instanceof VhdlPackageBody) {
                type = ((VhdlPackageBody) declarationWrapper).getType();
            } else if (declarationWrapper instanceof VhdlBlockStatement) {
                type = ((VhdlBlockStatement) declarationWrapper).getType();
            } else if (declarationWrapper instanceof VhdlGenerateStatement) {
                type = ((VhdlGenerateStatement) declarationWrapper).getType();
            } else if (declarationWrapper instanceof VhdlInterfaceGenericDeclaration) {
                type = ((VhdlInterfaceGenericDeclaration) declarationWrapper).getType();
            } else if (declarationWrapper instanceof VhdlInterfacePortDeclaration) {
                type = ((VhdlInterfacePortDeclaration) declarationWrapper).getType();
            } else if (declarationWrapper instanceof VhdlConstantDeclaration) {
                type = ((VhdlConstantDeclaration) declarationWrapper).getType();
            } else if (declarationWrapper instanceof VhdlSignalDeclaration) {
                type = ((VhdlSignalDeclaration) declarationWrapper).getType();
            } else if (declarationWrapper instanceof VhdlVariableDeclaration) {
                type = ((VhdlVariableDeclaration) declarationWrapper).getType();
            } else if (declarationWrapper instanceof VhdlComponentDeclaration) {
                type = ((VhdlComponentDeclaration) declarationWrapper).getType();
            } else if (declarationWrapper instanceof VhdlComponentInstantiationStatement) {
                type = ((VhdlComponentInstantiationStatement) declarationWrapper).getType();
            } else if (declarationWrapper instanceof VhdlSubprogramSpecification) {
                type = ((VhdlSubprogramSpecification) declarationWrapper).getType();
            } else if (declarationWrapper instanceof VhdlProcessStatement) {
                type = ((VhdlProcessStatement) declarationWrapper).getType();
            }
        }
        return StringUtils.shrinkParenthesis(type);
    }

    @NotNull
    public static String getType(VhdlEntityDeclaration entityDeclaration) {
        return "Entity";
    }

    @NotNull
    public static String getType(VhdlArchitectureBody architectureBody) {
        return "Architecture";
    }

    @NotNull
    public static String getType(VhdlPackageDeclaration packageDeclaration) {
        return "Package";
    }

    @NotNull
    public static String getType(VhdlPackageBody packageBody) {
        return "Package Body";
    }

    @NotNull
    public static String getType(VhdlBlockStatement block) {
        return "Block";
    }

    @NotNull
    public static String getType(VhdlGenerateStatement generate) {
        return "Generate";
    }

    @NotNull
    public static String getType(VhdlInterfaceGenericDeclaration declaration) {
        return declaration.getSubtypeIndication().getText();
    }

    @NotNull
    public static String getType(VhdlInterfacePortDeclaration declaration) {
        return declaration.getSubtypeIndication().getText();
    }

    @NotNull
    public static String getType(VhdlConstantDeclaration declaration) {
        return declaration.getSubtypeIndication().getText();
    }

    @NotNull
    public static String getType(VhdlSignalDeclaration declaration) {
        return declaration.getSubtypeIndication().getText();
    }

    @NotNull
    public static String getType(VhdlVariableDeclaration declaration) {
        return declaration.getSubtypeIndication().getText();
    }

    @NotNull
    public static String getType(VhdlComponentDeclaration declaration) {
        return "Component";
    }

    @NotNull
    public static String getType(VhdlComponentInstantiationStatement instantiation) {
        String entityName;
        String architectureName = null;
        VhdlInstantiatedUnit unit = instantiation.getInstantiatedUnit();
        VhdlSelectedName selectedName = unit.getSelectedName();
        List<VhdlIdentifier> ids = unit.getIdentifierList();
        if (selectedName != null) {
            entityName = selectedName.getText();
            if (ids.size() == 1) {
                architectureName = ids.get(0).getName();
            }
        } else {
            entityName = ids.get(0).getText();
            if (ids.size() == 2) {
                architectureName = ids.get(0).getName();
            }
        }
        if (architectureName != null) {
            return String.format("%s(%s)", entityName, architectureName);
        } else {
            return entityName;
        }
    }

    @NotNull
    public static String getType(VhdlSubprogramSpecification subprogram) {
        VhdlRefname returnValue = subprogram.getRefname();
        return returnValue != null ? returnValue.getText() : "";
    }

    @NotNull
    public static String getType(VhdlProcessStatement process) {
        return "Process";
    }


    /////////////////////////////////////////

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

    /////////////////////////////////////////

    public static Icon getIcon(VhdlIdentifier id, int flags) {
        if (id.isDeclared()) {
            PsiElement declarationWrapper = getDeclarationWrapper(id);
            if (declarationWrapper != null) {
                return declarationWrapper.getIcon(flags);
            }
        }
        return null;
    }

    public static Icon getIcon(VhdlEntityDeclaration entityDeclaration, int flags) {
        return VhdlIcons.ENTITY;
    }

    public static Icon getIcon(VhdlArchitectureBody architectureBody, int flags) {
        return VhdlIcons.ARCHITECTURE;
    }

    public static Icon getIcon(VhdlPackageDeclaration packageDeclaration, int flags) {
        return VhdlIcons.PACKAGE;
    }

    public static Icon getIcon(VhdlPackageBody packageBody, int flags) {
        return VhdlIcons.PACKAGE_BODY;
    }

    public static Icon getIcon(VhdlBlockStatement block, int flags) {
        return VhdlIcons.BLOCK;
    }

    public static Icon getIcon(VhdlGenerateStatement generate, int flags) {
        return VhdlIcons.GENERATE;
    }

    public static Icon getIcon(VhdlInterfaceGenericDeclaration declaration, int flags) {
        return VhdlIcons.GENERIC;
    }

    public static Icon getIcon(VhdlInterfacePortDeclaration declaration, int flags) {
        IElementType mode = declaration.getMode().getNode().getFirstChildNode().getElementType();
        if (mode == VhdlTypes.T_IN) {
            return VhdlIcons.PORT_IN;
        } else if (mode == VhdlTypes.T_OUT) {
            return VhdlIcons.PORT_OUT;
        } else if (mode == VhdlTypes.T_INOUT) {
            return VhdlIcons.PORT_INOUT;
        } else if (mode == VhdlTypes.T_BUFFER) {
            return VhdlIcons.PORT_BUFFER;
        } else {
            // Linkage is not supported.
            return null;
        }
    }

    public static Icon getIcon(VhdlConstantDeclaration declaration, int flags) {
        return VhdlIcons.CONSTANT;
    }

    public static Icon getIcon(VhdlSignalDeclaration declaration, int flags) {
        return VhdlIcons.SIGNAL;
    }

    public static Icon getIcon(VhdlVariableDeclaration declaration, int flags) {
        return VhdlIcons.VARIABLE;
    }

    public static Icon getIcon(VhdlComponentDeclaration declaration, int flags) {
        return VhdlIcons.COMPONENT;
    }

    public static Icon getIcon(VhdlComponentInstantiationStatement instantiation, int flags) {
        return VhdlIcons.INSTANTIATION;
    }

    public static Icon getIcon(VhdlSubprogramSpecification subprogram, int flags) {
        PsiElement parent = subprogram.getParent();
        if (parent instanceof VhdlSubprogramDeclaration) {
            return VhdlIcons.SUBPROGRAM;
        } else if (parent instanceof VhdlSubprogramBody) {
            return VhdlIcons.SUBPROGRAM_BODY;
        } else {
            throw new UnsupportedOperationException("Subprogram specification parent is of illegal type: " + parent.getClass());
        }
    }

    public static Icon getIcon(VhdlProcessStatement process, int flags) {
        return VhdlIcons.PROCESS;
    }


    ////////////////////////////////////////

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
                if (nextSibling == null || nextSibling.getNode().getElementType() == VhdlTypes.T_SEMICOLON) {
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

    /**
     * Returns the {@link PsiElement} which wraps {@code id}, if {@code id} is declared.
     * Otherwise, returns null.
     *
     * @param id The {@link VhdlIdentifier} to check.
     * @return The {@link PsiElement} wrapper if {@code id} is declared. <br>
     * {@code null} otherwise.
     */
    @Nullable
    private static PsiElement getDeclarationWrapper(@NotNull VhdlIdentifier id) {
        PsiElement parent = id.getParent();
        if (parent instanceof VhdlIdentifierList) {
            return parent.getParent();
        }
        if (parent instanceof VhdlFullTypeDeclaration
                || parent instanceof VhdlSubtypeDeclaration
                || parent instanceof VhdlSubprogramDeclaration
                || parent instanceof VhdlAliasDeclaration
                || parent instanceof VhdlAttributeDeclaration) {
            return parent;
        }

        if (parent instanceof VhdlDesignator) {
            // Subprogram (function/procedure).
            PsiElement parent1 = parent.getParent();
            PsiElement parent2 = parent1.getParent();
            if (parent2 instanceof VhdlSubprogramDeclaration) {
                return parent1;
            }
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
                    return null;
                } else if (!(nextSibling instanceof PsiWhiteSpace)
                        && !(nextSibling instanceof PsiComment)) {
                    return parent;
                }
                nextSibling = nextSibling.getNextSibling();
            }
        }
        return null;
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
        LinkedList<PsiElement> scopes = new LinkedList<>();
        PsiElement parent = id.getParent();
        while (!(parent instanceof VhdlFile)) {
            if (parent instanceof VhdlEntityDeclaration) {
                scopes.add(parent); // Entity.
                break;
            } else if (parent instanceof VhdlArchitectureBody) {
                VhdlEntityDeclaration entity = VhdlPsiTreeUtil.getEntity(((VhdlArchitectureBody) parent));
                scopes.add(parent); // Architecture.
                scopes.add(entity); // Entity.
                break;
            } else if (parent instanceof VhdlSubprogramDeclaration) {
                scopes.add(parent); // Subprogram, continue up until hitting a design unit.
            } else if (parent instanceof VhdlSubprogramBody) {
                scopes.add(parent); // Subprogram, continue up until hitting a design unit.
            } else if (parent instanceof VhdlPackageDeclaration) {
                scopes.add(parent); // Package.
                break;
            } else if (parent instanceof VhdlPackageBody) {
                VhdlPackageDeclaration declaration = VhdlPsiTreeUtil.getPackage(((VhdlPackageBody) parent));
                scopes.add(parent); // Package body.
                scopes.add(declaration); // Package declaration.
                break;
            } else if (parent instanceof VhdlProcessStatement) {
                scopes.add(parent); // Process, continue up until hitting the architecture.
            }
            parent = parent.getParent();
        }
        if (scopes.isEmpty()) {
            scopes.add(parent);
        }
        return scopes.toArray(new PsiElement[0]);
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