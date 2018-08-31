package com.github.amnonya.hdleditor.vhdl.navigation;

import com.github.amnonya.hdleditor.vhdl.icons.VhdlIcons;
import com.github.amnonya.hdleditor.vhdl.psi.VhdlPackageBody;
import com.intellij.navigation.ItemPresentation;

import org.jetbrains.annotations.Nullable;

import javax.swing.Icon;

public class VhdlPackageBodyPresentation implements ItemPresentation {
    private final VhdlPackageBody body;

    public VhdlPackageBodyPresentation(VhdlPackageBody body) {
        this.body = body;
    }

    @Nullable
    @Override
    public String getPresentableText() {
        return body.getIdentifierList().get(0).getName();
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
