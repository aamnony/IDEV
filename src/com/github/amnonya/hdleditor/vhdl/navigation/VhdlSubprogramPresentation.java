package com.github.amnonya.hdleditor.vhdl.navigation;

import com.github.amnonya.hdleditor.vhdl.VhdlIcons;
import com.github.amnonya.hdleditor.vhdl.psi.VhdlDesignator;
import com.github.amnonya.hdleditor.vhdl.psi.VhdlFunctionParameterConstantDeclaration;
import com.github.amnonya.hdleditor.vhdl.psi.VhdlFunctionParameterList;
import com.github.amnonya.hdleditor.vhdl.psi.VhdlFunctionParameterSignalDeclaration;
import com.github.amnonya.hdleditor.vhdl.psi.VhdlIdentifier;
import com.github.amnonya.hdleditor.vhdl.psi.VhdlOperatorSymbol;
import com.github.amnonya.hdleditor.vhdl.psi.VhdlProcedureParameterConstantDeclaration;
import com.github.amnonya.hdleditor.vhdl.psi.VhdlProcedureParameterList;
import com.github.amnonya.hdleditor.vhdl.psi.VhdlProcedureParameterSignalDeclaration;
import com.github.amnonya.hdleditor.vhdl.psi.VhdlProcedureParameterVariableDeclaration;
import com.github.amnonya.hdleditor.vhdl.psi.VhdlRefname;
import com.github.amnonya.hdleditor.vhdl.psi.VhdlSubprogramBody;
import com.github.amnonya.hdleditor.vhdl.psi.VhdlSubprogramDeclaration;
import com.github.amnonya.hdleditor.vhdl.psi.VhdlSubprogramParameterFileDeclaration;
import com.github.amnonya.hdleditor.vhdl.psi.VhdlSubprogramSpecification;
import com.intellij.navigation.ItemPresentation;
import com.intellij.psi.PsiElement;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

import javax.swing.Icon;

import static com.github.amnonya.hdleditor.utils.StringUtils.shrinkParenthesis;

public class VhdlSubprogramPresentation implements ItemPresentation {
    private final VhdlSubprogramSpecification specs;

    public VhdlSubprogramPresentation(VhdlSubprogramSpecification specification) {
        this.specs = specification;
    }

    @Override
    public String getPresentableText() {
        String name = getName();
        List<String> parametersTypes = getParametersTypes();
        VhdlRefname returnValue = specs.getRefname();
        if (returnValue != null) {
            return String.format("%s(%s): %s", name, String.join(", ", parametersTypes), shrinkParenthesis(returnValue.getText()));
        } else {
            return String.format("%s(%s)", name, String.join(", ", parametersTypes));
        }
    }

    @Nullable
    @Override
    public String getLocationString() {
        return null;
    }

    @Override
    public Icon getIcon(boolean unused) {
        PsiElement parent = specs.getParent();
        if (parent instanceof VhdlSubprogramDeclaration) {
            return VhdlIcons.SUBPROGRAM;
        } else if (parent instanceof VhdlSubprogramBody) {
            return VhdlIcons.SUBPROGRAM_BODY;
        } else {
            throw new UnsupportedOperationException("Subprogram specification parent is of illegal type: " + parent.getClass());
        }
    }

    @Nullable
    private String getName() {
        VhdlDesignator designator = specs.getDesignator();
        VhdlIdentifier identifier = designator.getIdentifier();
        if (identifier != null) {
            return identifier.getName();
        }
        VhdlOperatorSymbol operatorSymbol = designator.getOperatorSymbol();
        return operatorSymbol != null ? operatorSymbol.getStringLiteral().getText() : null;
    }

    @NotNull
    private List<String> getParametersTypes() {
        List<String> parameterTypes = new ArrayList<>(2);
        VhdlFunctionParameterList functionParameters = specs.getFunctionParameterList();
        VhdlProcedureParameterList procedureParameters = specs.getProcedureParameterList();

        if (functionParameters != null) {
            for (VhdlFunctionParameterConstantDeclaration constant : functionParameters.getFunctionParameterConstantDeclarationList()) {
                parameterTypes.add(shrinkParenthesis(constant.getSubtypeIndication().getText()));
            }
            for (VhdlFunctionParameterSignalDeclaration signal : functionParameters.getFunctionParameterSignalDeclarationList()) {
                parameterTypes.add(shrinkParenthesis(signal.getSubtypeIndication().getText()));
            }
            for (VhdlSubprogramParameterFileDeclaration file : functionParameters.getSubprogramParameterFileDeclarationList()) {
                parameterTypes.add(shrinkParenthesis(file.getSubtypeIndication().getText()));
            }
        } else if (procedureParameters != null) {
            for (VhdlProcedureParameterConstantDeclaration constant : procedureParameters.getProcedureParameterConstantDeclarationList()) {
                parameterTypes.add(shrinkParenthesis(constant.getSubtypeIndication().getText()));
            }
            for (VhdlProcedureParameterSignalDeclaration signal : procedureParameters.getProcedureParameterSignalDeclarationList()) {
                parameterTypes.add(shrinkParenthesis(signal.getSubtypeIndication().getText()));
            }
            for (VhdlProcedureParameterVariableDeclaration variable : procedureParameters.getProcedureParameterVariableDeclarationList()) {
                parameterTypes.add(shrinkParenthesis(variable.getSubtypeIndication().getText()));
            }
            for (VhdlSubprogramParameterFileDeclaration file : procedureParameters.getSubprogramParameterFileDeclarationList()) {
                parameterTypes.add(shrinkParenthesis(file.getSubtypeIndication().getText()));
            }
        }
        return parameterTypes;
    }
}
