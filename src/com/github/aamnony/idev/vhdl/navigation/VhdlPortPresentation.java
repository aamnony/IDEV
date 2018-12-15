package com.github.aamnony.idev.vhdl.navigation;

import com.github.aamnony.idev.vhdl.VhdlIcons;
import com.github.aamnony.idev.utils.StringUtils;
import com.github.aamnony.idev.vhdl.VhdlIcons;
import com.github.aamnony.idev.vhdl.psi.VhdlExpression;
import com.github.aamnony.idev.vhdl.psi.VhdlInterfacePortDeclaration;
import com.github.aamnony.idev.vhdl.psi.VhdlTypes;
import com.intellij.navigation.ItemPresentation;
import com.intellij.psi.tree.IElementType;

import org.jetbrains.annotations.Nullable;

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
        String type = StringUtils.shrinkParenthesis(declaration.getSubtypeIndication().getText());
        VhdlExpression expression = declaration.getExpression();

        if (expression == null) {
            return String.format("%s: %s", name, type);
        } else {
            return String.format("%s:%s = %s", name, type, expression.getText());
        }
    }

    @Nullable
    @Override
    public String getLocationString() {
        return null;
    }

    @Nullable
    @Override
    public Icon getIcon(boolean unused) {
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
}
