package com.github.amnonya.hdleditor.vhdl.psi;

import com.github.amnonya.hdleditor.vhdl.lang.VhdlLanguage;
import com.intellij.patterns.PlatformPatterns;
import com.intellij.patterns.PsiElementPattern;
import com.intellij.psi.PsiNamedElement;
import com.intellij.psi.PsiReferenceContributor;
import com.intellij.psi.PsiReferenceRegistrar;

import org.jetbrains.annotations.NotNull;

public class VhdlReferenceContributor extends PsiReferenceContributor {
    @Override
    public void registerReferenceProviders(@NotNull PsiReferenceRegistrar registrar) {
        PsiElementPattern.Capture<PsiNamedElement> elementCapture =
                PlatformPatterns.psiElement(PsiNamedElement.class).withLanguage(VhdlLanguage.INSTANCE);

        registrar.registerReferenceProvider(elementCapture, new VhdlReferenceProvider());
    }

}