package com.github.aamnony.idev.vhdl.lang;

import com.intellij.psi.tree.IElementType;

import org.jetbrains.annotations.NonNls;
import org.jetbrains.annotations.NotNull;

public class VhdlTokenType extends IElementType {
    public VhdlTokenType(@NotNull @NonNls String debugName) {
        super(debugName, VhdlLanguage.INSTANCE);
    }

    @Override
    public String toString() {
        return super.toString();
    }
}

