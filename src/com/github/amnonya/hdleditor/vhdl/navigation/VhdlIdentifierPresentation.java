package com.github.amnonya.hdleditor.vhdl.navigation;

import com.github.amnonya.hdleditor.vhdl.VhdlIcons;
import com.github.amnonya.hdleditor.vhdl.psi.VhdlIdentifier;
import com.intellij.navigation.ItemPresentation;

import org.jetbrains.annotations.Nullable;

import javax.swing.Icon;

public class VhdlIdentifierPresentation implements ItemPresentation {
    private final VhdlIdentifier id;

    public VhdlIdentifierPresentation(VhdlIdentifier id) {
        this.id = id;
    }

    @Nullable
    @Override
    public String getPresentableText() {
        return id.getName();
    }

    @Override
    public String getLocationString() {
        return id.getContainingFile().getName();
    }

    @Nullable
    @Override
    public Icon getIcon(boolean unused) {
        return null;
    }
}
