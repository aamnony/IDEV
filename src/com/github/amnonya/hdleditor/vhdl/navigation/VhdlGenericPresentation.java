package com.github.amnonya.hdleditor.vhdl.navigation;

import com.github.amnonya.hdleditor.vhdl.icons.VhdlIcons;
import com.github.amnonya.hdleditor.vhdl.psi.VhdlExpression;
import com.github.amnonya.hdleditor.vhdl.psi.VhdlInterfaceGenericDeclaration;
import com.intellij.navigation.ItemPresentation;

import javax.swing.Icon;

public class VhdlGenericPresentation implements ItemPresentation {
    private final VhdlInterfaceGenericDeclaration declaration;
    private final int index;

    public VhdlGenericPresentation(VhdlInterfaceGenericDeclaration declaration, int index) {
        this.declaration = declaration;
        this.index = index;
    }

    @Override
    public String getPresentableText() {
        String name = declaration.getIdentifierList().getIdentifierList().get(index).getName();
        String type = declaration.getSubtypeIndication().getText();
        VhdlExpression expression = declaration.getExpression();

        if (expression == null) {
            return String.format("%s: %s", name, type);
        } else {
            return String.format("%s: %s := %s", name, type, expression.getText());
        }
    }

    @Override
    public String getLocationString() {
        return declaration.getContainingFile().getName();
    }

    @Override
    public Icon getIcon(boolean unused) {
        return VhdlIcons.FILE;
    }
}
