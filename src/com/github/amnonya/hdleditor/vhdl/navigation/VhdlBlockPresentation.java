package com.github.amnonya.hdleditor.vhdl.navigation;

import com.github.amnonya.hdleditor.vhdl.VhdlIcons;
import com.github.amnonya.hdleditor.vhdl.psi.VhdlBlockStatement;
import com.intellij.navigation.ItemPresentation;

import org.jetbrains.annotations.Nullable;

import javax.swing.Icon;

public class VhdlBlockPresentation implements ItemPresentation {
    private final VhdlBlockStatement block;

    public VhdlBlockPresentation(VhdlBlockStatement block) {
        this.block = block;
    }

    @Override
    public String getPresentableText() {
        return block.getLabelList().get(0).getIdentifier().getName();
    }

    @Nullable
    @Override
    public String getLocationString() {
        return null;
    }

    @Override
    public Icon getIcon(boolean unused) {
        return VhdlIcons.BLOCK;
    }
}
