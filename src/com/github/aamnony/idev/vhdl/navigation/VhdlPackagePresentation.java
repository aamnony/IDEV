package com.github.aamnony.idev.vhdl.navigation;

import com.github.aamnony.idev.vhdl.psi.VhdlPackageDeclaration;
import com.intellij.navigation.ItemPresentation;

import org.jetbrains.annotations.Nullable;

import javax.swing.Icon;

public class VhdlPackagePresentation implements ItemPresentation {
    private final VhdlPackageDeclaration declaration;

    public VhdlPackagePresentation(VhdlPackageDeclaration declaration) {
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
