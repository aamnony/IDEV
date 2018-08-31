package com.github.amnonya.hdleditor.vhdl.navigation;

import com.github.amnonya.hdleditor.vhdl.icons.VhdlIcons;
import com.github.amnonya.hdleditor.vhdl.psi.VhdlArchitectureBody;
import com.intellij.navigation.ItemPresentation;

import javax.swing.Icon;

public class VhdlArchitecturePresentation implements ItemPresentation {
    private final VhdlArchitectureBody body;

    public VhdlArchitecturePresentation(VhdlArchitectureBody body) {
        this.body = body;
    }

    @Override
    public String getPresentableText() {
        String archName = body.getIdentifierList().get(0).getName();
        String entityName = body.getRefname().getText();
        return archName + "(" + entityName + ")";
    }

    @Override
    public String getLocationString() {
        return body.getContainingFile().getName();
    }

    @Override
    public Icon getIcon(boolean unused) {
        return VhdlIcons.FILE;
    }
}
