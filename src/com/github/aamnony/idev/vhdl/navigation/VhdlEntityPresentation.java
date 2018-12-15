package com.github.aamnony.idev.vhdl.navigation;

import com.github.aamnony.idev.vhdl.VhdlIcons;
import com.github.aamnony.idev.vhdl.VhdlIcons;
import com.github.aamnony.idev.vhdl.psi.VhdlEntityDeclaration;
import com.intellij.navigation.ItemPresentation;

import org.jetbrains.annotations.Nullable;

import javax.swing.Icon;

public class VhdlEntityPresentation implements ItemPresentation {
    private final VhdlEntityDeclaration declaration;

    public VhdlEntityPresentation(VhdlEntityDeclaration declaration) {
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
        return VhdlIcons.ENTITY;
    }
}
