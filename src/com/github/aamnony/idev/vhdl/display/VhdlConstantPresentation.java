package com.github.aamnony.idev.vhdl.display;

import com.github.aamnony.idev.utils.StringUtils;
import com.github.aamnony.idev.vhdl.lang.VhdlConstantDeclaration;
import com.intellij.navigation.ItemPresentation;

import org.jetbrains.annotations.Nullable;

import javax.swing.Icon;

public class VhdlConstantPresentation implements ItemPresentation {
    private final VhdlConstantDeclaration declaration;
    private final int index;

    public VhdlConstantPresentation(VhdlConstantDeclaration declaration, int index) {
        this.declaration = declaration;
        this.index = index;
    }

    @Override
    public String getPresentableText() {
        String name = declaration.getIdentifierList().getIdentifierList().get(index).getName();
        String type = StringUtils.shrinkParenthesis(declaration.getType());
//        VhdlExpression expression = declaration.getExpression();

//        if (expression == null) {
        return String.format("%s: %s", name, type);
//        } else {
//            return String.format("%s: %s = %s", name, type, expression.getText());
//        }
    }

    @Nullable
    @Override
    public String getLocationString() {
        return null;
    }

    @Override
    public Icon getIcon(boolean unused) {
        return declaration.getIcon(0);
    }
}
