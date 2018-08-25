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
//        int indentSize = settings.getIndentSize(VhdlFileType.INSTANCE);
        // TODO: Add dependency on settings
        // Rule order matters!
        return new SpacingBuilder(settings, VhdlLanguage.INSTANCE)
                .before(VhdlTypes.COMMENT).spaces(2)
                .around(VhdlTypes.T_SEMICOLON).none()
                .before(VhdlTypes.T_COMMA).none()
                .after(VhdlTypes.T_COMMA).spaces(1)
                .around(VhdlTypes.T_DOT).none()
                .around(VhdlTypes.T_COLON).spaces(1)
                .after(VhdlTypes.T_LEFT_BRACKET).none()
                .before(VhdlTypes.T_RIGHT_BRACKET).none()
                .around(VhdlSyntaxHighlighter.OPERATORS).spaces(1)
                .around(VhdlSyntaxHighlighter.KEYWORDS).spaces(1)
//                .around(VhdlTypes.CHARACTER_LITERAL).none()
//                .around(VhdlTypes.IDENTIFIER).none()
                .around(VhdlSyntaxHighlighter.PARENTHESES).none()
                //
//                .around(VhdlTypes.T_IS).spaces(1)
//                .around(VhdlTypes.T_TYPE).spaces(1)
//                .around(VhdlTypes.T_SUBTYPE).spaces(1)
//                .after(VhdlTypes.T_LEFT_PAREN).none()
//                .before(VhdlTypes.T_RIGHT_PAREN).none()
//                .around(VhdlTypes.T_OF).spaces(1)
//                .around(VhdlTypes.T_ARRAY).spaces(1)
//                .around(VhdlTypes.T_ENTITY).spaces(1)
//                .around(VhdlTypes.T_ARCHITECTURE).spaces(1)
//                .around(VhdlTypes.T_PACKAGE).spaces(1)
//                .around(VhdlTypes.T_BODY).spaces(1)
//                .around(VhdlTypes.T_COMPONENT).spaces(1)
                ;
//                .before(VhdlTypes.USE_CLAUSE).spaces(indentSize);
//                .around(VhdlTypes.USE_CLAUSE).spaces();
//                .after(TokenSet.WHITE_SPACE).none();
//                .around(VhdlTypes.SEPARATOR)
//                .spaceIf(settings.SPACE_AROUND_ASSIGNMENT_OPERATORS)
//                .before(VhdlTypes.PROPERTY)
//                .none();
    }

    @Nullable
    @Override
    public TextRange getRangeAffectingIndent(PsiFile file, int offset, ASTNode elementAtOffset) {
        return null;
    }
}
