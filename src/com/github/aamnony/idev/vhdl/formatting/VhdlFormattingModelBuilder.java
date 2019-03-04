package com.github.aamnony.idev.vhdl.formatting;

import com.github.aamnony.idev.vhdl.lang.VhdlElementTypes;
import com.github.aamnony.idev.vhdl.lang.VhdlLanguage;
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

import static com.github.aamnony.idev.vhdl.psi.VhdlTypes.ARCHITECTURE_BODY;
import static com.github.aamnony.idev.vhdl.psi.VhdlTypes.COMMENT;
import static com.github.aamnony.idev.vhdl.psi.VhdlTypes.CONFIGURATION_DECLARATION;
import static com.github.aamnony.idev.vhdl.psi.VhdlTypes.CONTEXT_CLAUSE;
import static com.github.aamnony.idev.vhdl.psi.VhdlTypes.ENTITY_DECLARATION;
import static com.github.aamnony.idev.vhdl.psi.VhdlTypes.LIBRARY_CLAUSE;
import static com.github.aamnony.idev.vhdl.psi.VhdlTypes.PACKAGE_BODY;
import static com.github.aamnony.idev.vhdl.psi.VhdlTypes.PACKAGE_DECLARATION;
import static com.github.aamnony.idev.vhdl.psi.VhdlTypes.T_COLON;
import static com.github.aamnony.idev.vhdl.psi.VhdlTypes.T_COMMA;
import static com.github.aamnony.idev.vhdl.psi.VhdlTypes.T_DOT;
import static com.github.aamnony.idev.vhdl.psi.VhdlTypes.T_LEFT_BRACKET;
import static com.github.aamnony.idev.vhdl.psi.VhdlTypes.T_LEFT_PAREN;
import static com.github.aamnony.idev.vhdl.psi.VhdlTypes.T_RIGHT_BRACKET;
import static com.github.aamnony.idev.vhdl.psi.VhdlTypes.T_RIGHT_PAREN;
import static com.github.aamnony.idev.vhdl.psi.VhdlTypes.T_SEMICOLON;
import static com.github.aamnony.idev.vhdl.psi.VhdlTypes.USE_CLAUSE;

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

                .between(VhdlElementTypes.STATEMENTS, VhdlElementTypes.STATEMENTS).spacing(0, Integer.MAX_VALUE, 1, false, commonSettings.KEEP_BLANK_LINES_IN_CODE)
                .between(VhdlElementTypes.DECLARATIONS, VhdlElementTypes.DECLARATIONS).spacing(0, Integer.MAX_VALUE, 1, false, commonSettings.KEEP_BLANK_LINES_IN_DECLARATIONS)
                .between(COMMENT, VhdlElementTypes.STATEMENTS).spacing(0, Integer.MAX_VALUE, 1, false, commonSettings.KEEP_BLANK_LINES_IN_CODE)
                .between(COMMENT, VhdlElementTypes.DECLARATIONS).spacing(0, Integer.MAX_VALUE, 1, false, commonSettings.KEEP_BLANK_LINES_IN_DECLARATIONS)

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
                .around(VhdlElementTypes.ADDITIVE_OPERATORS).spaceIf(commonSettings.SPACE_AROUND_ADDITIVE_OPERATORS)
                .around(VhdlElementTypes.ASSIGNMENT_OPERATORS).spaceIf(commonSettings.SPACE_AROUND_ASSIGNMENT_OPERATORS)
                .around(VhdlElementTypes.EQUALITY_OPERATORS).spaceIf(commonSettings.SPACE_AROUND_EQUALITY_OPERATORS)
                .around(VhdlElementTypes.LOGICAL_OPERATORS).spaces(1)
                .around(VhdlElementTypes.MULTIPLICATIVE_OPERATORS).spaceIf(commonSettings.SPACE_AROUND_MULTIPLICATIVE_OPERATORS)
                .around(VhdlElementTypes.MULTIPLICATIVE_WORD_OPERATORS).spaces(1)
                .around(VhdlElementTypes.RELATIONAL_OPERATORS).spaceIf(commonSettings.SPACE_AROUND_RELATIONAL_OPERATORS)
                .around(VhdlElementTypes.SHIFT_OPERATORS).spaces(1)
                .around(VhdlElementTypes.KEYWORDS).spaces(1)
                .after(T_LEFT_PAREN).spaceIf(commonSettings.SPACE_WITHIN_PARENTHESES)
                .before(T_RIGHT_PAREN).spaceIf(commonSettings.SPACE_WITHIN_PARENTHESES)
                .around(VhdlElementTypes.PARENTHESES).none();
    }

    @Nullable
    @Override
    public TextRange getRangeAffectingIndent(PsiFile file, int offset, ASTNode elementAtOffset) {
        return null;
    }
}
