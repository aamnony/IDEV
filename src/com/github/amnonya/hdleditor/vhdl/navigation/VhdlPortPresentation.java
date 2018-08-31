package com.github.amnonya.hdleditor.vhdl.navigation;

import com.github.amnonya.hdleditor.vhdl.icons.VhdlIcons;
import com.github.amnonya.hdleditor.vhdl.psi.VhdlExpression;
import com.github.amnonya.hdleditor.vhdl.psi.VhdlInterfacePortDeclaration;
import com.intellij.navigation.ItemPresentation;

import javax.swing.Icon;

public class VhdlPortPresentation implements ItemPresentation {
    private final VhdlInterfacePortDeclaration declaration;
    private final int index;

    public VhdlPortPresentation(VhdlInterfacePortDeclaration declaration, int index) {
        this.declaration = declaration;
        this.index = index;
    }

    @Override
    public String getPresentableText() {
        String name = declaration.getIdentifierList().getIdentifierList().get(index).getName();
        String mode = declaration.getMode().getText();
        String type = declaration.getSubtypeIndication().getText();
        VhdlExpression expression = declaration.getExpression();

        if (expression == null) {
            return String.format("%s: %s %s", name, mode, type);
        } else {
            return String.format("%s: %s %s := %s", name, mode, type, expression.getText());
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
