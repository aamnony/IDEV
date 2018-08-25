package com.github.amnonya.hdleditor.vhdl.psi.impl;

import com.github.amnonya.hdleditor.vhdl.psi.VhdlNamedElement;
import com.intellij.extapi.psi.ASTWrapperPsiElement;
import com.intellij.lang.ASTNode;

import org.jetbrains.annotations.NotNull;

public abstract class VhdlNamedElementImpl extends ASTWrapperPsiElement implements VhdlNamedElement {
    public VhdlNamedElementImpl(@NotNull ASTNode node) {
        super(node);
    }
}
