package com.github.aamnony.idev.vhdl.navigation;

import com.github.aamnony.idev.vhdl.IdByScopeComparator;
import com.github.aamnony.idev.vhdl.lang.VhdlIdentifier;
import com.github.aamnony.idev.vhdl.lang.VhdlPsiTreeUtil;
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
            PsiElement[] scopes = id.getScopes();
            // This identifier is mentioned, we need to find the declared reference.
            List<VhdlIdentifier> ids = new ArrayList<>();
            VhdlPsiTreeUtil.findIdentifiers(id, ids);

            // Let's sort the references by their scope, so we'll find deeper declarations first.
            ids.sort(new IdByScopeComparator());

            for (VhdlIdentifier ref : ids) {
                if (ref.isDeclared()) {
                    // We found a declaration, let's check its scope.
                    PsiElement[] refScopes = ref.getScopes();
                    int refScopeScore = IdByScopeComparator.getScore(refScopes[0]);

                    if (refScopeScore >= IdByScopeComparator.DEEP_SCOPE) {
                        // The reference scope is deep (process/subprogram body...) - if it's the same as the identifier's scope,
                        // then this is the best reference declaration we could find. Otherwise we'll continue searching.
                        if (refScopes[0] == scopes[0]) {
                            return ref;
                        }
                    } else {
                        // We couldn't find a deep declaration, so let's return the closest reference.
                        return ref;
                    }
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
        // When trying to autocomplete a word (...), or when the word is incomplete, the text is:
        // ...IntellijIdeaRulezzz

        List<VhdlIdentifier> ids = new ArrayList<>();
        VhdlPsiTreeUtil.findIdentifiers(id, ids);

        // Let's sort the references by their scope, so we'll find deeper declarations first.
        ids.sort(new IdByScopeComparator());

        List<LookupElement> variants = new LookupElementArrayList(ids.size() / 4);
        for (VhdlIdentifier ref : ids) {
            if (!variants.contains(ref) && ref.isDeclared()) {
                String presentableText = ref.getPresentation().getPresentableText();
                variants.add(LookupElementBuilder.create(ref)
                        .withPresentableText(presentableText != null ? presentableText : ref.getText()) // TODO: work this out
                        .withIcon(ref.getIcon(0))
                        .withCaseSensitivity(false)
                        .withTypeText(ref.getType()));
            }
        }


//        for (VhdlIdentifier ref : ids) {
//            if (!variants.contains(ref.getText())) {
//                variants.add(ref.getText());
//            }
//            variants.add(LookupElementBuilder.create(ref)
//                    .withIcon(VhdlIcons.FILE)
//                    .withTypeText(ref.getContainingFile().getName());

//        }
        return variants.toArray();
    }

    @Override
    public PsiElement handleElementRename(String newElementName) throws IncorrectOperationException {
        return getElement().setName(newElementName);
    }

    @Override
    public TextRange getRangeInElement() {
        return new TextRange(0, getElement().getTextLength());
    }

    private static class LookupElementArrayList extends ArrayList<LookupElement> {
        LookupElementArrayList(int initialCapacity) {
            super(initialCapacity);
        }

        /**
         * {@inheritDoc}
         * If the given object is a {@link String} then its value is searched in the lookup element,
         * where the case sensitivity of the comparison is dependent on each lookup element.
         */
        @Override
        public boolean contains(Object o) {
            if (o instanceof VhdlIdentifier) {
                String text = ((VhdlIdentifier) o).getText();
                for (LookupElement element : this) {
                    if ((element.isCaseSensitive() && text.equals(element.getLookupString()))
                            || text.equalsIgnoreCase(element.getLookupString())) {
                        return true;
                    }
                }
                return false;
            } else {
                return super.contains(o);
            }
        }
    }
}