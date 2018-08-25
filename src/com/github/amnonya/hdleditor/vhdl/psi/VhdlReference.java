package com.github.amnonya.hdleditor.vhdl.psi;

import com.github.amnonya.hdleditor.vhdl.icons.VhdlIcons;
import com.github.amnonya.hdleditor.vhdl.psi.tree.VhdlPsiTreeUtil;
import com.intellij.codeInsight.lookup.LookupElement;
import com.intellij.codeInsight.lookup.LookupElementBuilder;
import com.intellij.openapi.util.TextRange;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiElementResolveResult;
import com.intellij.psi.PsiNamedElement;
import com.intellij.psi.PsiReferenceBase;
import com.intellij.psi.ResolveResult;
import com.intellij.util.IncorrectOperationException;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

public class VhdlReference extends PsiReferenceBase<PsiNamedElement> {// implements PsiPolyVariantReference {

    public VhdlReference(@NotNull PsiNamedElement element, TextRange textRange) {
        super(element, textRange);
    }

    @NotNull
//    @Override
    public ResolveResult[] multiResolve(boolean incompleteCode) {
        VhdlIdentifier id = (VhdlIdentifier) getElement();
        List<VhdlIdentifier> ids = new ArrayList<>();
        VhdlPsiTreeUtil.findIdentifiers(id, ids);

        if (id.isDeclared()) {
            // The identifier is the declared, we add all mentioned references.
            ResolveResult[] results = new ResolveResult[ids.size()];
            for (int i = 0; i < ids.size(); i++) {
                results[i] = new PsiElementResolveResult(ids.get(i));
            }
            return results;
        } else {
            // This identifier is mentioned, we add only the declared reference.
            for (VhdlIdentifier ref : ids) {
                if (ref.isDeclared()) {
                    return new PsiElementResolveResult[]{new PsiElementResolveResult(ref)};
                }
            }
        }
        return ResolveResult.EMPTY_ARRAY;
    }

    @Nullable
    @Override
    public PsiElement resolve() {
        VhdlIdentifier id = (VhdlIdentifier) getElement();
        if (!id.isDeclared()) {
            // This identifier is mentioned, we need to find the declared reference.
            List<VhdlIdentifier> ids = new ArrayList<>();
            VhdlPsiTreeUtil.findIdentifiers(id, ids);
            for (VhdlIdentifier ref : ids) {
                if (ref.isDeclared()) {
                    return ref;
                }
            }
        }
        return null;
    }

    @NotNull
    @Override
    public Object[] getVariants() {
//        return EMPTY_ARRAY; // todo
        VhdlIdentifier id = (VhdlIdentifier) getElement();

        List<VhdlIdentifier> ids = new ArrayList<>();
        VhdlPsiTreeUtil.findIdentifiers(id, ids);

        LookupElement[] variants = new LookupElement[ids.size()];
        for (int i = 0; i < variants.length; i++) {
            VhdlIdentifier ref = ids.get(i);
            variants[i] = LookupElementBuilder.create(ref)
                    .withIcon(VhdlIcons.FILE)
                    .withTypeText(ref.getContainingFile().getName());

        }
        return variants;
    }

    @Override
    public PsiElement handleElementRename(String newElementName) throws IncorrectOperationException {
        return getElement().setName(newElementName);
    }

    @Override
    public TextRange getRangeInElement() {
        return new TextRange(0, getElement().getTextLength());
    }
}