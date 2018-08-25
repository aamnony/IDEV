package com.github.amnonya.hdleditor.vhdl.psi.tree;

import com.github.amnonya.hdleditor.vhdl.lang.VhdlLanguage;
import com.intellij.psi.tree.IElementType;

import org.jetbrains.annotations.NonNls;
import org.jetbrains.annotations.NotNull;

public class VhdlElementType extends IElementType {
    public VhdlElementType(@NotNull @NonNls String debugName) {
        super(debugName, VhdlLanguage.INSTANCE);
    }
}
