package com.github.aamnony.idev.vhdl.codeInsight.completion;

import com.github.aamnony.idev.vhdl.psi.VhdlElementTypes;
import com.github.aamnony.idev.vhdl.psi.VhdlElementTypes;
import com.github.aamnony.idev.vhdl.psi.VhdlFile;
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
import com.intellij.util.ProcessingContext;

import org.jetbrains.annotations.NotNull;

public class VhdlCompletionContributor extends CompletionContributor {
    public VhdlCompletionContributor() {
        CompletionProvider<CompletionParameters> keywordsProvider = new CompletionProvider<CompletionParameters>() {
            public void addCompletions(@NotNull CompletionParameters parameters, ProcessingContext context, @NotNull CompletionResultSet resultSet) {
                for (IElementType keyword : VhdlElementTypes.KEYWORDS.getTypes()) {
                    resultSet.addElement(LookupElementBuilder.create(keyword.toString().toLowerCase()).withCaseSensitivity(false));
                }
            }
        };
//        PsiElementPattern.Capture<PsiElement> place = PlatformPatterns.psiElement(VhdlTypes.ARCHITECTURE_DECLARATIVE_PART).withLanguage(VhdlLanguage.INSTANCE);
        PsiElementPattern.Capture<PsiElement> place = PlatformPatterns.psiElement().inFile(PlatformPatterns.psiFile(VhdlFile.class));
        extend(CompletionType.BASIC, place, keywordsProvider);
    }
}

