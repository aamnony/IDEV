package com.github.amnonya.hdleditor.vhdl.navigation;

import com.github.amnonya.hdleditor.vhdl.psi.VhdlBlockStatement;
import com.github.amnonya.hdleditor.vhdl.psi.VhdlGenerateStatement;
import com.intellij.navigation.ItemPresentation;

import org.jetbrains.annotations.Nullable;

import javax.swing.Icon;

public class VhdlGeneratePresentation implements ItemPresentation {
    private final VhdlGenerateStatement generate;

    public VhdlGeneratePresentation(VhdlGenerateStatement generate) {
        this.generate = generate;
    }

    @Override
    public String getPresentableText() {
        return generate.getLabelList().get(0).getIdentifier().getName();
    }

    @Nullable
    @Override
    public String getLocationString() {
        return null;
    }

    @Nullable
    @Override
    public Icon getIcon(boolean unused) {
        return null;
    }
}
