package com.github.aamnony.idev.vhdl.lang;

import com.intellij.psi.tree.IElementType;

import org.jetbrains.annotations.NonNls;
import org.jetbrains.annotations.NotNull;

public class VhdlElementType extends IElementType {
    public VhdlElementType(@NotNull @NonNls String debugName) {
        super(debugName, VhdlLanguage.INSTANCE);
    }
}
