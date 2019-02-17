package com.github.aamnony.idev.vhdl.navigation;

import com.github.aamnony.idev.vhdl.psi.VhdlDesignator;
import com.github.aamnony.idev.vhdl.psi.VhdlFunctionParameterConstantDeclaration;
import com.github.aamnony.idev.vhdl.psi.VhdlFunctionParameterList;
import com.github.aamnony.idev.vhdl.psi.VhdlFunctionParameterSignalDeclaration;
import com.github.aamnony.idev.vhdl.psi.VhdlIdentifier;
import com.github.aamnony.idev.vhdl.psi.VhdlOperatorSymbol;
import com.github.aamnony.idev.vhdl.psi.VhdlProcedureParameterConstantDeclaration;
import com.github.aamnony.idev.vhdl.psi.VhdlProcedureParameterList;
import com.github.aamnony.idev.vhdl.psi.VhdlProcedureParameterSignalDeclaration;
import com.github.aamnony.idev.vhdl.psi.VhdlProcedureParameterVariableDeclaration;
import com.github.aamnony.idev.vhdl.psi.VhdlSubprogramParameterFileDeclaration;
import com.github.aamnony.idev.vhdl.psi.VhdlSubprogramSpecification;
import com.intellij.navigation.ItemPresentation;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

import javax.swing.Icon;

import static com.github.aamnony.idev.utils.StringUtils.shrinkParenthesis;

public class VhdlSubprogramPresentation implements ItemPresentation {
    private final VhdlSubprogramSpecification specs;

    public VhdlSubprogramPresentation(VhdlSubprogramSpecification specification) {
        this.specs = specification;
    }

    @Override
    public String getPresentableText() {
        String name = getName();
        List<String> parametersTypes = getParametersTypes();
        String type = specs.getType();
        if (!type.equals("")) {
            return String.format("%s(%s): %s", name, String.join(", ", parametersTypes), shrinkParenthesis(type));
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
        return specs.getIcon(0);
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
