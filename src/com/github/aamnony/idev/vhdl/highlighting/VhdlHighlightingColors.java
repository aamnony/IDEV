package com.github.aamnony.idev.vhdl.highlighting;

import com.intellij.openapi.editor.DefaultLanguageHighlighterColors;
import com.intellij.openapi.editor.colors.TextAttributesKey;

import static com.intellij.openapi.editor.colors.TextAttributesKey.createTextAttributesKey;

public class VhdlHighlightingColors {
    public static final TextAttributesKey PRIMARY_DESIGN_UNIT = createTextAttributesKey("PRIMARY_DESIGN_UNIT", DefaultLanguageHighlighterColors.INTERFACE_NAME);
    public static final TextAttributesKey SECONDARY_DESIGN_UNIT = createTextAttributesKey("SECONDARY_DESIGN_UNIT", DefaultLanguageHighlighterColors.CLASS_NAME);
    public static final TextAttributesKey KEYWORD = createTextAttributesKey("KEYWORD", DefaultLanguageHighlighterColors.KEYWORD);
    public static final TextAttributesKey OPERATOR = createTextAttributesKey("OPERATOR", DefaultLanguageHighlighterColors.OPERATION_SIGN);
    public static final TextAttributesKey TYPE = createTextAttributesKey("TYPE", DefaultLanguageHighlighterColors.CLASS_REFERENCE);
    public static final TextAttributesKey SUBTYPE = createTextAttributesKey("SUBTYPE", DefaultLanguageHighlighterColors.CLASS_REFERENCE);
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
    public static final TextAttributesKey FILE_VARIABLE = createTextAttributesKey("FILE_VARIABLE", DefaultLanguageHighlighterColors.LOCAL_VARIABLE);
    public static final TextAttributesKey ATTRIBUTE = createTextAttributesKey("ATTRIBUTE", DefaultLanguageHighlighterColors.METADATA);
    public static final TextAttributesKey ALIAS = createTextAttributesKey("ALIAS", DefaultLanguageHighlighterColors.MARKUP_ATTRIBUTE);
    public static final TextAttributesKey SUBPROGRAM_PARAMETER = createTextAttributesKey("SUBPROGRAM_PARAMETER", DefaultLanguageHighlighterColors.PARAMETER);
    public static final TextAttributesKey SUBPROGRAM_DECLARATION = createTextAttributesKey("SUBPROGRAM_DECLARATION", DefaultLanguageHighlighterColors.FUNCTION_DECLARATION);
    public static final TextAttributesKey SUBPROGRAM_CALL = createTextAttributesKey("SUBPROGRAM_CALL", DefaultLanguageHighlighterColors.FUNCTION_CALL);
    public static final TextAttributesKey LABEL = createTextAttributesKey("LABEL", DefaultLanguageHighlighterColors.LABEL);
}
