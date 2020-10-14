package com.github.aamnony.idev.vhdl.completion;

import com.github.aamnony.idev.vhdl.lang.VhdlElementTypes;
import com.github.aamnony.idev.vhdl.lang.VhdlFile;
import com.github.aamnony.idev.vhdl.lang.VhdlIdentifier;
import com.github.aamnony.idev.vhdl.lang.VhdlPsiImplUtil;
import com.intellij.codeInsight.completion.CompletionContributor;
import com.intellij.codeInsight.completion.CompletionParameters;
import com.intellij.codeInsight.completion.CompletionProvider;
import com.intellij.codeInsight.completion.CompletionResultSet;
import com.intellij.codeInsight.completion.CompletionType;
import com.intellij.codeInsight.lookup.LookupElementBuilder;
import com.intellij.patterns.PlatformPatterns;
import com.intellij.patterns.PsiElementPattern;
import com.intellij.psi.PsiElement;
import com.intellij.psi.tree.IElementType;
import com.intellij.psi.tree.TokenSet;
import com.intellij.util.ProcessingContext;

import org.jetbrains.annotations.NotNull;

public class VhdlCompletionContributor extends CompletionContributor {
    public VhdlCompletionContributor() {
        CompletionProvider<CompletionParameters> keywordsProvider = new CompletionProvider<CompletionParameters>() {
            public void addCompletions(@NotNull CompletionParameters parameters, ProcessingContext context, @NotNull CompletionResultSet resultSet) {
                TokenSet keywords = VhdlElementTypes.KEYWORDS;
                PsiElement id = parameters.getPosition().getParent();
                if (id instanceof VhdlIdentifier) {
                    PsiElement[] scopes = ((VhdlIdentifier) id).getScopes();
                    // Use first scope only to determine the keywords, because it should be the most restricting (inner-most scope).
                    TokenSet scopeDependentKeywords = VhdlElementTypes.SCOPE_DEPENDENT_KEYWORDS.get(scopes[0].getClass().getInterfaces()[0]);
                    if (scopeDependentKeywords != null) {
                        keywords = scopeDependentKeywords;
                    }
                }
                for (IElementType keyword : keywords.getTypes()) {
                    resultSet.addElement(LookupElementBuilder.create(keyword.toString().toLowerCase()).withCaseSensitivity(false));
                }
            }
        };
        PsiElementPattern.Capture<PsiElement> place = PlatformPatterns.psiElement().inFile(PlatformPatterns.psiFile(VhdlFile.class));
        extend(CompletionType.BASIC, place, keywordsProvider);
    }
}

