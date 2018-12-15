package com.github.aamnony.idev.vhdl.highlighting;

import com.github.aamnony.idev.vhdl.lexer.VhdlLexerAdapter;
import com.github.aamnony.idev.vhdl.lexer.VhdlLexerAdapter;
import com.github.aamnony.idev.vhdl.psi.VhdlElementTypes;
import com.github.aamnony.idev.vhdl.psi.VhdlTypes;
import com.intellij.lexer.Lexer;
import com.intellij.openapi.editor.colors.TextAttributesKey;
import com.intellij.openapi.fileTypes.SyntaxHighlighterBase;
import com.intellij.psi.TokenType;
import com.intellij.psi.tree.IElementType;

import org.jetbrains.annotations.NotNull;

import static com.intellij.openapi.editor.HighlighterColors.BAD_CHARACTER;
import static com.intellij.openapi.editor.colors.TextAttributesKey.createTextAttributesKey;

public class VhdlSyntaxHighlighter extends SyntaxHighlighterBase {

    private static final TextAttributesKey[] BAD_CHAR_KEYS = new TextAttributesKey[]{BAD_CHARACTER};
    private static final TextAttributesKey[] KEYWORDS_KEYS = new TextAttributesKey[]{VhdlHighlightingColors.KEYWORD};
    private static final TextAttributesKey[] OPERATORS_KEYS = new TextAttributesKey[]{VhdlHighlightingColors.OPERATOR};
    private static final TextAttributesKey[] SEMICOLON_KEYS = new TextAttributesKey[]{VhdlHighlightingColors.SEMICOLON};
    private static final TextAttributesKey[] COMMA_KEYS = new TextAttributesKey[]{VhdlHighlightingColors.COMMA};
    private static final TextAttributesKey[] DOT_KEYS = new TextAttributesKey[]{VhdlHighlightingColors.DOT};
    private static final TextAttributesKey[] PARENTHESES_KEYS = new TextAttributesKey[]{VhdlHighlightingColors.PARENTHESIS};
    private static final TextAttributesKey[] COMMENTS_KEYS = new TextAttributesKey[]{VhdlHighlightingColors.COMMENT};
    private static final TextAttributesKey[] CHARACTER_KEYS = new TextAttributesKey[]{VhdlHighlightingColors.CHARACTER};
    private static final TextAttributesKey[] STRING_KEYS = new TextAttributesKey[]{VhdlHighlightingColors.STRING};
    private static final TextAttributesKey[] NUMERIC_KEYS = new TextAttributesKey[]{VhdlHighlightingColors.NUMBER};
    private static final TextAttributesKey[] EMPTY_KEYS = new TextAttributesKey[0];

    public static final String[] PREDEFINED_ATTRIBUTES = {
            "ACTIVE", "ASCENDING", "BASE", "DELAYED", "DRIVING", "DRIVING_VALUE", "EVENT", "HIGH",
            "IMAGE", "INSTANCE_NAME", "LAST_ACTIVE", "LAST_EVENT", "LAST_VALUE", "LEFT", "LEFTOF",
            "LENGTH", "LOW", "PATH_NAME", "POS", "PRED", "QUIET", "RANGE", "REVERSE_RANGE",
            "RIGHT", "RIGHTOF", "SIMPLE_NAME", "STABLE", "SUCC", "TRANSACTION", "VAL", "VALUE",
    };

    @NotNull
    @Override
    public Lexer getHighlightingLexer() {
        return new VhdlLexerAdapter();
    }

    @NotNull
    @Override
    public TextAttributesKey[] getTokenHighlights(IElementType tokenType) {
        if (VhdlElementTypes.KEYWORDS.contains(tokenType)) {
            return KEYWORDS_KEYS;
        }
        if (VhdlElementTypes.SEMICOLONS.contains(tokenType)) {
            return SEMICOLON_KEYS;
        }
        if (VhdlElementTypes.PARENTHESES.contains(tokenType)) {
            return PARENTHESES_KEYS;
        }
        if (VhdlElementTypes.OPERATORS.contains(tokenType)) {
            return OPERATORS_KEYS;
        }
        if (VhdlElementTypes.OPERATORS_2.contains(tokenType)) {
            return OPERATORS_KEYS;
        }
//        if (KNOWN_TYPES.contains(tokenType)) {
//            return TYPES_KEYS;
//        }
        if (tokenType.equals(VhdlTypes.COMMENT)) {
            return COMMENTS_KEYS;
        }
        if (tokenType.equals(VhdlTypes.T_COMMA)) {
            return COMMA_KEYS;
        }
        if (tokenType.equals(VhdlTypes.T_DOT)) {
            return DOT_KEYS;
        }
        if (tokenType.equals(VhdlTypes.STRLIT)
                || tokenType.equals(VhdlTypes.BINLIT)
                || tokenType.equals(VhdlTypes.OCTLIT)
                || tokenType.equals(VhdlTypes.HEXLIT)) {
            return STRING_KEYS;
        }
        if (tokenType.equals(VhdlTypes.CHRLIT)) {
            return CHARACTER_KEYS;
        }
        if (tokenType.equals(VhdlTypes.REALLIT)) {
            return NUMERIC_KEYS;
        }
        if (tokenType.equals(TokenType.BAD_CHARACTER)) {
            return BAD_CHAR_KEYS;
        }
        return EMPTY_KEYS;
    }
}
