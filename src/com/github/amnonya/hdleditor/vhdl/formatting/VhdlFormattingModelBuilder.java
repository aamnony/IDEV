package com.github.amnonya.hdleditor.vhdl.formatting;

import com.github.amnonya.hdleditor.vhdl.lang.VhdlLanguage;
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

import static com.github.amnonya.hdleditor.vhdl.psi.VhdlElementTypes.*;
import static com.github.amnonya.hdleditor.vhdl.psi.VhdlTypes.*;

public class VhdlFormattingModelBuilder implements FormattingModelBuilder {

    @NotNull
    @Override
    public FormattingModel createModel(PsiElement element, CodeStyleSettings settings) {
        VhdlBlock rootBlock = new VhdlBlock(
                null,
                settings,                      // Alignments are handled by the VhdlBlocks themselves.
                createSpaceBuilder(settings),  // Other spacing settings are handled here.
                element.getNode(),
                null,
                Indent.getNoneIndent(),
                null
        );
        return FormattingModelProvider.createFormattingModelForPsiFile(element.getContainingFile(), rootBlock, settings);
    }

    private static SpacingBuilder createSpaceBuilder(CodeStyleSettings settings) {
        // Rule order matters!
        CommonCodeStyleSettings commonSettings = settings.getCommonSettings(VhdlLanguage.INSTANCE);
        return new SpacingBuilder(settings, VhdlLanguage.INSTANCE)

                // Blank lines:
                // Set no blank lines between use->use clauses, and between library -> use clauses:
                .between(USE_CLAUSE, USE_CLAUSE).spacing(0, Integer.MAX_VALUE, 1, false, 0)
                .between(LIBRARY_CLAUSE, USE_CLAUSE).spacing(0, Integer.MAX_VALUE, 1, false, 0)
                // Set one blank line between use->library clauses:
                .between(USE_CLAUSE, LIBRARY_CLAUSE).spacing(0, Integer.MAX_VALUE, 2, false, 0)

                .before(CONTEXT_CLAUSE).blankLines(commonSettings.BLANK_LINES_BEFORE_IMPORTS)
                .after(CONTEXT_CLAUSE).blankLines(commonSettings.BLANK_LINES_AFTER_IMPORTS)
                .after(ENTITY_DECLARATION).blankLines(commonSettings.BLANK_LINES_AROUND_CLASS)
                .after(PACKAGE_BODY).blankLines(commonSettings.BLANK_LINES_AROUND_CLASS)
                .after(CONFIGURATION_DECLARATION).blankLines(commonSettings.BLANK_LINES_AROUND_CLASS)
                .after(ARCHITECTURE_BODY).blankLines(commonSettings.BLANK_LINES_AROUND_CLASS)
                .after(PACKAGE_DECLARATION).blankLines(commonSettings.BLANK_LINES_AROUND_CLASS)

                .between(STATEMENTS, STATEMENTS).spacing(0, Integer.MAX_VALUE, 1, false, commonSettings.KEEP_BLANK_LINES_IN_CODE)
                .between(DECLARATIONS, DECLARATIONS).spacing(0, Integer.MAX_VALUE, 1, false, commonSettings.KEEP_BLANK_LINES_IN_DECLARATIONS)
                .between(COMMENT, STATEMENTS).spacing(0, Integer.MAX_VALUE, 1, false, commonSettings.KEEP_BLANK_LINES_IN_CODE)
                .between(COMMENT, DECLARATIONS).spacing(0, Integer.MAX_VALUE, 1, false, commonSettings.KEEP_BLANK_LINES_IN_DECLARATIONS)

                // Spaces:
                .before(COMMENT).spaces(2)
                .before(T_SEMICOLON).spaceIf(commonSettings.SPACE_BEFORE_SEMICOLON)
                .after(T_SEMICOLON).none()
                .before(T_COMMA).spaceIf(commonSettings.SPACE_BEFORE_COMMA)
                .after(T_COMMA).spaceIf(commonSettings.SPACE_AFTER_COMMA)
                .around(T_DOT).none()
                .before(T_COLON).spaceIf(commonSettings.SPACE_BEFORE_COLON)
                .after(T_COLON).spaceIf(commonSettings.SPACE_AFTER_COLON)
                .after(T_LEFT_BRACKET).spaceIf(commonSettings.SPACE_WITHIN_BRACKETS)
                .before(T_RIGHT_BRACKET).spaceIf(commonSettings.SPACE_WITHIN_BRACKETS)
                .around(ADDITIVE_OPERATORS).spaceIf(commonSettings.SPACE_AROUND_ADDITIVE_OPERATORS)
                .around(ASSIGNMENT_OPERATORS).spaceIf(commonSettings.SPACE_AROUND_ASSIGNMENT_OPERATORS)
                .around(EQUALITY_OPERATORS).spaceIf(commonSettings.SPACE_AROUND_EQUALITY_OPERATORS)
                .around(LOGICAL_OPERATORS).spaces(1)
                .around(MULTIPLICATIVE_OPERATORS).spaceIf(commonSettings.SPACE_AROUND_MULTIPLICATIVE_OPERATORS)
                .around(MULTIPLICATIVE_WORD_OPERATORS).spaces(1)
                .around(RELATIONAL_OPERATORS).spaceIf(commonSettings.SPACE_AROUND_RELATIONAL_OPERATORS)
                .around(SHIFT_OPERATORS).spaces(1)
                .around(KEYWORDS).spaces(1)
                .after(T_LEFT_PAREN).spaceIf(commonSettings.SPACE_WITHIN_PARENTHESES)
                .before(T_RIGHT_PAREN).spaceIf(commonSettings.SPACE_WITHIN_PARENTHESES)
                .around(PARENTHESES).none();
    }

    @Nullable
    @Override
    public TextRange getRangeAffectingIndent(PsiFile file, int offset, ASTNode elementAtOffset) {
        return null;
    }
}
