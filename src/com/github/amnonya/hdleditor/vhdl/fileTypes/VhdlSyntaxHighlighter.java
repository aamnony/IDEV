package com.github.amnonya.hdleditor.vhdl.fileTypes;

import com.github.amnonya.hdleditor.vhdl.lexer.VhdlLexerAdapter;
import com.github.amnonya.hdleditor.vhdl.psi.VhdlTypes;
import com.intellij.lexer.Lexer;
import com.intellij.openapi.editor.DefaultLanguageHighlighterColors;
import com.intellij.openapi.editor.colors.TextAttributesKey;
import com.intellij.openapi.fileTypes.SyntaxHighlighterBase;
import com.intellij.psi.TokenType;
import com.intellij.psi.tree.IElementType;
import com.intellij.psi.tree.TokenSet;

import org.jetbrains.annotations.NotNull;

import static com.intellij.openapi.editor.HighlighterColors.BAD_CHARACTER;
import static com.intellij.openapi.editor.colors.TextAttributesKey.createTextAttributesKey;

public class VhdlSyntaxHighlighter extends SyntaxHighlighterBase {
    public static final TextAttributesKey PRIMARY_DESIGN_UNIT = createTextAttributesKey("PRIMARY_DESIGN_UNIT", DefaultLanguageHighlighterColors.INTERFACE_NAME);
    public static final TextAttributesKey SECONDARY_DESIGN_UNIT = createTextAttributesKey("SECONDARY_DESIGN_UNIT", DefaultLanguageHighlighterColors.CLASS_NAME);
    public static final TextAttributesKey KEYWORD = createTextAttributesKey("KEYWORD", DefaultLanguageHighlighterColors.KEYWORD);
    public static final TextAttributesKey OPERATOR = createTextAttributesKey("OPERATOR", DefaultLanguageHighlighterColors.OPERATION_SIGN);
    public static final TextAttributesKey TYPE = createTextAttributesKey("TYPE", DefaultLanguageHighlighterColors.CLASS_REFERENCE);
    public static final TextAttributesKey SEMICOLON = createTextAttributesKey("SEMICOLON", DefaultLanguageHighlighterColors.SEMICOLON);
    public static final TextAttributesKey COMMA = createTextAttributesKey("COMMA", DefaultLanguageHighlighterColors.COMMA);
    public static final TextAttributesKey DOT = createTextAttributesKey("DOT", DefaultLanguageHighlighterColors.DOT);
    public static final TextAttributesKey PARENTHESIS = createTextAttributesKey("PARENTHESIS", DefaultLanguageHighlighterColors.PARENTHESES);
    public static final TextAttributesKey COMMENT = createTextAttributesKey("COMMENT", DefaultLanguageHighlighterColors.LINE_COMMENT);
    public static final TextAttributesKey CHARACTER = createTextAttributesKey("CHARACTER", DefaultLanguageHighlighterColors.STRING);
    public static final TextAttributesKey STRING = createTextAttributesKey("STRING", DefaultLanguageHighlighterColors.STRING);
    public static final TextAttributesKey NUMBER = createTextAttributesKey("NUMBER", DefaultLanguageHighlighterColors.NUMBER);
    public static final TextAttributesKey GENERIC = createTextAttributesKey("GENERIC", DefaultLanguageHighlighterColors.PARAMETER);
    public static final TextAttributesKey PORT = createTextAttributesKey("PORT", DefaultLanguageHighlighterColors.INSTANCE_FIELD);
    public static final TextAttributesKey SIGNAL = createTextAttributesKey("SIGNAL", DefaultLanguageHighlighterColors.LOCAL_VARIABLE);
    public static final TextAttributesKey CONSTANT = createTextAttributesKey("CONSTANT", DefaultLanguageHighlighterColors.CONSTANT);
    public static final TextAttributesKey VARIABLE = createTextAttributesKey("VARIABLE", DefaultLanguageHighlighterColors.LOCAL_VARIABLE);
    public static final TextAttributesKey ATTRIBUTE = createTextAttributesKey("ATTRIBUTE", DefaultLanguageHighlighterColors.METADATA);
    public static final TextAttributesKey ALIAS = createTextAttributesKey("ALIAS", DefaultLanguageHighlighterColors.MARKUP_ATTRIBUTE);
    public static final TextAttributesKey SUBPROGRAM_PARAMETER = createTextAttributesKey("SUBPROGRAM_PARAMETER", DefaultLanguageHighlighterColors.PARAMETER);
    public static final TextAttributesKey SUBPROGRAM_DECLARATION = createTextAttributesKey("SUBPROGRAM_DECLARATION", DefaultLanguageHighlighterColors.FUNCTION_DECLARATION);
    public static final TextAttributesKey SUBPROGRAM_CALL = createTextAttributesKey("SUBPROGRAM_CALL", DefaultLanguageHighlighterColors.FUNCTION_CALL);
    public static final TextAttributesKey LABEL = createTextAttributesKey("LABEL", DefaultLanguageHighlighterColors.LABEL);

    private static final TextAttributesKey[] BAD_CHAR_KEYS = new TextAttributesKey[]{BAD_CHARACTER};
    private static final TextAttributesKey[] KEYWORDS_KEYS = new TextAttributesKey[]{KEYWORD};
    private static final TextAttributesKey[] OPERATORS_KEYS = new TextAttributesKey[]{OPERATOR};
    private static final TextAttributesKey[] SEMICOLON_KEYS = new TextAttributesKey[]{SEMICOLON};
    private static final TextAttributesKey[] COMMA_KEYS = new TextAttributesKey[]{COMMA};
    private static final TextAttributesKey[] DOT_KEYS = new TextAttributesKey[]{DOT};
    private static final TextAttributesKey[] PARENTHESES_KEYS = new TextAttributesKey[]{PARENTHESIS};
    private static final TextAttributesKey[] COMMENTS_KEYS = new TextAttributesKey[]{COMMENT};
    private static final TextAttributesKey[] CHARACTER_KEYS = new TextAttributesKey[]{CHARACTER};
    private static final TextAttributesKey[] STRING_KEYS = new TextAttributesKey[]{STRING};
    private static final TextAttributesKey[] NUMERIC_KEYS = new TextAttributesKey[]{NUMBER};
    private static final TextAttributesKey[] EMPTY_KEYS = new TextAttributesKey[0];

    public static final String[] PREDEFINED_ATTRIBUTES = {
            "ACTIVE", "ASCENDING", "BASE", "DELAYED", "DRIVING", "DRIVING_VALUE", "EVENT", "HIGH",
            "IMAGE", "INSTANCE_NAME", "LAST_ACTIVE", "LAST_EVENT", "LAST_VALUE", "LEFT", "LEFTOF",
            "LENGTH", "LOW", "PATH_NAME", "POS", "PRED", "QUIET", "RANGE", "REVERSE_RANGE",
            "RIGHT", "RIGHTOF", "SIMPLE_NAME", "STABLE", "SUCC", "TRANSACTION", "VAL", "VALUE",
    };

    public static final TokenSet KEYWORDS = TokenSet.create(
            VhdlTypes.T_ASSERT, VhdlTypes.T_SEVERITY, VhdlTypes.T_REPORT, VhdlTypes.T_NULL, VhdlTypes.T_IF,
            VhdlTypes.T_THEN, VhdlTypes.T_ELSIF, VhdlTypes.T_ELSE, VhdlTypes.T_FOR, VhdlTypes.T_WHILE, VhdlTypes.T_LOOP,
            VhdlTypes.T_NEXT, VhdlTypes.T_EXIT, VhdlTypes.T_GENERATE, VhdlTypes.T_SELECT, VhdlTypes.T_WITH,
            VhdlTypes.T_CASE, VhdlTypes.T_WHEN, VhdlTypes.T_UNAFFECTED, VhdlTypes.T_LIBRARY, VhdlTypes.T_USE,
            VhdlTypes.T_ALL, VhdlTypes.T_ARCHITECTURE, VhdlTypes.T_CONFIGURATION, VhdlTypes.T_ENTITY,
            VhdlTypes.T_POSTPONED, VhdlTypes.T_PROCESS, VhdlTypes.T_PACKAGE, VhdlTypes.T_BODY, VhdlTypes.T_DISCONNECT,
            VhdlTypes.T_GUARDED, VhdlTypes.T_BLOCK, VhdlTypes.T_COMPONENT, VhdlTypes.T_FUNCTION, VhdlTypes.T_IMPURE,
            VhdlTypes.T_PURE, VhdlTypes.T_PROCEDURE, VhdlTypes.T_RETURN, VhdlTypes.T_BUFFER, VhdlTypes.T_IN,
            VhdlTypes.T_INOUT, VhdlTypes.T_LINKAGE, VhdlTypes.T_OUT, VhdlTypes.T_ARRAY, VhdlTypes.T_ACCESS,
            VhdlTypes.T_CONSTANT, VhdlTypes.T_FILE, VhdlTypes.T_NEW, VhdlTypes.T_SIGNAL, VhdlTypes.T_SHARED,
            VhdlTypes.T_SUBTYPE, VhdlTypes.T_RANGE, VhdlTypes.T_RECORD, VhdlTypes.T_TYPE, VhdlTypes.T_UNITS,
            VhdlTypes.T_OTHERS, VhdlTypes.T_DOWNTO, VhdlTypes.T_TO, VhdlTypes.T_VARIABLE, VhdlTypes.T_GENERIC,
            VhdlTypes.T_PORT, VhdlTypes.T_MAP, VhdlTypes.T_OPEN, VhdlTypes.T_ATTRIBUTE, VhdlTypes.T_ALIAS,
            VhdlTypes.T_LABEL, VhdlTypes.T_BUS, VhdlTypes.T_REGISTER, VhdlTypes.T_GROUP, VhdlTypes.T_LITERAL,
            VhdlTypes.T_AFTER, VhdlTypes.T_INERTIAL, VhdlTypes.T_REJECT, VhdlTypes.T_TRANSPORT, VhdlTypes.T_UNTIL,
            VhdlTypes.T_WAIT, VhdlTypes.T_BEGIN, VhdlTypes.T_END, VhdlTypes.T_IS, VhdlTypes.T_OF, VhdlTypes.T_ON
    );
    public static final TokenSet OPERATORS = TokenSet.create(
            VhdlTypes.T_EXP, VhdlTypes.T_MUL, VhdlTypes.T_DIV, VhdlTypes.T_ADD, VhdlTypes.T_SUB,
            VhdlTypes.T_CONCAT, VhdlTypes.T_EQ, VhdlTypes.T_NE, VhdlTypes.T_LT, VhdlTypes.T_LE, VhdlTypes.T_GT,
            VhdlTypes.T_GE, VhdlTypes.T_BLOCKING_ASSIGNMENT, VhdlTypes.T_MAP_ASSIGNMENT, VhdlTypes.T_ABS,
            VhdlTypes.T_AND, VhdlTypes.T_MOD, VhdlTypes.T_NAND, VhdlTypes.T_NOR, VhdlTypes.T_NOT, VhdlTypes.T_OR,
            VhdlTypes.T_REM, VhdlTypes.T_ROL, VhdlTypes.T_ROR, VhdlTypes.T_SLA, VhdlTypes.T_SLL, VhdlTypes.T_SRA,
            VhdlTypes.T_SRL, VhdlTypes.T_XNOR, VhdlTypes.T_XOR,
            // VHDL 2008 Operators:
            VhdlTypes.T_QQ, VhdlTypes.T_QE, VhdlTypes.T_QNE, VhdlTypes.T_QLT, VhdlTypes.T_QLE, VhdlTypes.T_QGT,
            VhdlTypes.T_QGE
    );

    private static final TokenSet OPERATORS_2 = TokenSet.create(VhdlTypes.T_APOSTROPHE);

    public static final TokenSet PARENTHESES = TokenSet.create(
            VhdlTypes.T_LEFT_PAREN, VhdlTypes.T_RIGHT_PAREN
    );
    private static final TokenSet SEMICOLONS = TokenSet.create(
            VhdlTypes.T_COLON, VhdlTypes.T_SEMICOLON
    );

    @NotNull
    @Override
    public Lexer getHighlightingLexer() {
        return new VhdlLexerAdapter();
    }

    @NotNull
    @Override
    public TextAttributesKey[] getTokenHighlights(IElementType tokenType) {
        if (KEYWORDS.contains(tokenType)) {
            return KEYWORDS_KEYS;
        }
        if (SEMICOLONS.contains(tokenType)) {
            return SEMICOLON_KEYS;
        }
        if (PARENTHESES.contains(tokenType)) {
            return PARENTHESES_KEYS;
        }
        if (OPERATORS.contains(tokenType)) {
            return OPERATORS_KEYS;
        }
        if (OPERATORS_2.contains(tokenType)) {
            return OPERATORS_KEYS;
        }
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
