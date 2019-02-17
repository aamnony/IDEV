package com.github.aamnony.idev.vhdl.navigation;

import com.github.aamnony.idev.vhdl.psi.VhdlComponentDeclaration;
import com.intellij.navigation.ItemPresentation;

import org.jetbrains.annotations.Nullable;

import javax.swing.Icon;

public class VhdlComponentPresentation implements ItemPresentation {
    private final VhdlComponentDeclaration declaration;

    public VhdlComponentPresentation(VhdlComponentDeclaration declaration) {
        this.declaration = declaration;
    }

    @Nullable
    @Override
    public String getPresentableText() {
        return declaration.getIdentifierList().get(0).getName();
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
