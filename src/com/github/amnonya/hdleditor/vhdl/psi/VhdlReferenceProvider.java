package com.github.amnonya.hdleditor.vhdl.psi;

import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiNamedElement;
import com.intellij.psi.PsiReference;
import com.intellij.psi.PsiReferenceProvider;
import com.intellij.util.ProcessingContext;

import org.jetbrains.annotations.NotNull;

class VhdlReferenceProvider extends PsiReferenceProvider {
    @NotNull
    @Override
    public PsiReference[] getReferencesByElement(@NotNull PsiElement element, @NotNull ProcessingContext context) {
        if (element instanceof PsiNamedElement) {
            PsiNamedElement namedElement = (PsiNamedElement) element;
            return new PsiReference[]{new VhdlReference(namedElement, namedElement.getTextRange())};
        } else {
            return PsiReference.EMPTY_ARRAY;
        }
    }
}
