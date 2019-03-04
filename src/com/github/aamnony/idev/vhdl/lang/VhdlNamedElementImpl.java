package com.github.aamnony.idev.vhdl.lang;

import com.intellij.extapi.psi.ASTWrapperPsiElement;
import com.intellij.lang.ASTNode;

import org.jetbrains.annotations.NotNull;

public abstract class VhdlNamedElementImpl extends ASTWrapperPsiElement implements VhdlNamedElement {
    public VhdlNamedElementImpl(@NotNull ASTNode node) {
        super(node);
    }
}
