package com.github.amnonya.hdleditor.vhdl.formatting;

import com.github.amnonya.hdleditor.vhdl.fileTypes.VhdlSyntaxHighlighter;
import com.github.amnonya.hdleditor.vhdl.lang.VhdlLanguage;
import com.github.amnonya.hdleditor.vhdl.psi.VhdlTypes;
import com.intellij.formatting.FormattingModel;
import com.intellij.formatting.FormattingModelBuilder;
import com.intellij.formatting.FormattingModelProvider;
import com.intellij.formatting.Indent;
import com.intellij.formatting.SpacingBuilder;
import com.intellij.lang.ASTNode;
import com.intellij.openapi.util.TextRange;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiFile;
import com.intellij.psi.codeStyle.CodeStyleSettings;
import com.intellij.psi.codeStyle.CommonCodeStyleSettings;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class VhdlFormattingModelBuilder implements FormattingModelBuilder {
    @NotNull
    @Override
    public FormattingModel createModel(PsiElement element, CodeStyleSettings settings) {
        return FormattingModelProvider.createFormattingModelForPsiFile(
                element.getContainingFile(),
                new VhdlBlock(
                        null,
                        element.getNode(),
                        null,
                        Indent.getNoneIndent(),
                        null,
                        createSpaceBuilder(settings)
                ),
                settings
        );
    }

    private static SpacingBuilder createSpaceBuilder(CodeStyleSettings settings) {
        // Rule order matters!
        CommonCodeStyleSettings langSettings = settings.getCommonSettings(VhdlLanguage.INSTANCE);
        return new SpacingBuilder(settings, VhdlLanguage.INSTANCE)
                .before(VhdlTypes.COMMENT).spaces(2)
                .before(VhdlTypes.T_SEMICOLON).spaceIf(langSettings.SPACE_BEFORE_SEMICOLON)
                .after(VhdlTypes.T_SEMICOLON).none()
                .before(VhdlTypes.T_COMMA).spaceIf(langSettings.SPACE_BEFORE_COMMA)
                .after(VhdlTypes.T_COMMA).spaceIf(langSettings.SPACE_AFTER_COMMA)
                .around(VhdlTypes.T_DOT).none()
                .before(VhdlTypes.T_COLON).spaceIf(langSettings.SPACE_BEFORE_COLON)
                .after(VhdlTypes.T_COLON).spaceIf(langSettings.SPACE_AFTER_COLON)
                .after(VhdlTypes.T_LEFT_BRACKET).spaceIf(langSettings.SPACE_WITHIN_BRACKETS)
                .before(VhdlTypes.T_RIGHT_BRACKET).spaceIf(langSettings.SPACE_WITHIN_BRACKETS)
                .around(VhdlSyntaxHighlighter.ADDITIVE_OPERATORS).spaceIf(langSettings.SPACE_AROUND_ADDITIVE_OPERATORS)
                .around(VhdlSyntaxHighlighter.ASSIGNMENT_OPERATORS).spaceIf(langSettings.SPACE_AROUND_ASSIGNMENT_OPERATORS)
                .around(VhdlSyntaxHighlighter.EQUALITY_OPERATORS).spaceIf(langSettings.SPACE_AROUND_EQUALITY_OPERATORS)
                .around(VhdlSyntaxHighlighter.LOGICAL_OPERATORS).spaces(1)
                .around(VhdlSyntaxHighlighter.MULTIPLICATIVE_OPERATORS).spaceIf(langSettings.SPACE_AROUND_MULTIPLICATIVE_OPERATORS)
                .around(VhdlSyntaxHighlighter.MULTIPLICATIVE_WORD_OPERATORS).spaces(1)
                .around(VhdlSyntaxHighlighter.RELATIONAL_OPERATORS).spaceIf(langSettings.SPACE_AROUND_RELATIONAL_OPERATORS)
                .around(VhdlSyntaxHighlighter.SHIFT_OPERATORS).spaces(1)
                .around(VhdlSyntaxHighlighter.KEYWORDS).spaces(1)
                .after(VhdlTypes.T_LEFT_PAREN).spaceIf(langSettings.SPACE_WITHIN_PARENTHESES)
                .before(VhdlTypes.T_RIGHT_PAREN).spaceIf(langSettings.SPACE_WITHIN_PARENTHESES)
                .around(VhdlSyntaxHighlighter.PARENTHESES).none();
    }

    @Nullable
    @Override
    public TextRange getRangeAffectingIndent(PsiFile file, int offset, ASTNode elementAtOffset) {
        return null;
    }
}
