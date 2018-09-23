package com.github.amnonya.hdleditor.vhdl.lexer;

import com.intellij.lexer.FlexLexer;
import com.intellij.psi.tree.IElementType;
import com.intellij.psi.TokenType;
import com.github.amnonya.hdleditor.vhdl.psi.VhdlTypes;

%%

%class VhdlLexer
%implements FlexLexer
%unicode
%function advance
%type IElementType
%caseless
%ignorecase
%eof{  return;
%eof}

WHITE_SPACE_CHAR = [\ \n\r\t\f]
END_OF_LINE_COMMENT = ("--")[^\r\n]*

//IDENTIFIER = [a-zA-Z_][a-zA-Z0-9_]*
IDENTIFIER = [a-zA-Z]           // Starts with a letter.
             (_?[a-zA-Z0-9]+)*  // Continues with groups of letters and digits, which are separated by a *single* underscore.
                                // Ends with either a letter or a digit (not an underscore).

REAL_LIT = // Based real, no exponent allowed.
           ([2-9]|(1[0-6])#      // Base, between 2-16.
           [0-9a-fA-F_]+         // Integer, optionally seperated by underscores.
           (\.[0-9a-fA-F_]+)?#)  // Optional decimal point and fraction integer.
           |
           // Default decimal base.
           ([0-9_]+              // Integer, optionally seperated by underscores.
           (\.[0-9_]+)?          // Optional decimal point and fraction integer.
           (e[-+]?[0-9_]+)?)     // Optional exponent and integer.
           (\s*(fs|ps|ns|us|ms|sec|min|hr))?  // TODO: make units work somehow.

BIN_LIT = [bB]?[\"][01_UXZWLHuxzwlh]+[\"]
OCT_LIT = [oO][\"][0-7_]+[\"]
HEX_LIT = [xX][\"][0-9a-fA-F_]+[\"]
CHR_LIT = [\'].[\']
STR_LIT = [\"]           // Starts with quote (").
          ([^\"]|\"\")*  // Contains nothing (empty string) or non-quote characters or escaped quotes ("").
          [\"]           // Ends with quote (").

%state IN_VALUE
%state IN_KEY_VALUE_SEPARATOR

%%


<YYINITIAL> {
    /* Keywords */
    /* Statements */
    "assert"                       { return VhdlTypes.T_ASSERT; }
    "severity"                     { return VhdlTypes.T_SEVERITY; }
    "report"                       { return VhdlTypes.T_REPORT; }

    "null"                         { return VhdlTypes.T_NULL; }

    "if"                           { return VhdlTypes.T_IF; }
    "then"                         { return VhdlTypes.T_THEN; }
    "elsif"                        { return VhdlTypes.T_ELSIF; }
    "else"                         { return VhdlTypes.T_ELSE; }

    "for"                          { return VhdlTypes.T_FOR; }
    "while"                        { return VhdlTypes.T_WHILE; }
    "loop"                         { return VhdlTypes.T_LOOP; }
    "next"                         { return VhdlTypes.T_NEXT; }
    "exit"                         { return VhdlTypes.T_EXIT; }

    "generate"                     { return VhdlTypes.T_GENERATE; }

    "select"                       { return VhdlTypes.T_SELECT; }
    "with"                         { return VhdlTypes.T_WITH; }

    "case"                         { return VhdlTypes.T_CASE; }
    "when"                         { return VhdlTypes.T_WHEN; }

    "unaffected"                   { return VhdlTypes.T_UNAFFECTED; }

    /* Design Units */
    "library"                      { return VhdlTypes.T_LIBRARY; }
    "use"                          { return VhdlTypes.T_USE; }
    "all"                          { return VhdlTypes.T_ALL; }

    "architecture"                 { return VhdlTypes.T_ARCHITECTURE; }
    "configuration"                { return VhdlTypes.T_CONFIGURATION; }
    "entity"                       { return VhdlTypes.T_ENTITY; }

    "postponed"                    { return VhdlTypes.T_POSTPONED; }
    "process"                      { return VhdlTypes.T_PROCESS; }

    "package"                      { return VhdlTypes.T_PACKAGE; }
    "body"                         { return VhdlTypes.T_BODY; }

    "disconnect"                   { return VhdlTypes.T_DISCONNECT; }
    "guarded"                      { return VhdlTypes.T_GUARDED; }
    "block"                        { return VhdlTypes.T_BLOCK; }

    "component"                    { return VhdlTypes.T_COMPONENT; }

    "function"                     { return VhdlTypes.T_FUNCTION; }
    "impure"                       { return VhdlTypes.T_IMPURE; }
    "pure"                         { return VhdlTypes.T_PURE; }
    "procedure"                    { return VhdlTypes.T_PROCEDURE; }
    "return"                       { return VhdlTypes.T_RETURN; }

    /* Port directions */
    "buffer"                       { return VhdlTypes.T_BUFFER; }
    "in"                           { return VhdlTypes.T_IN; }
    "inout"                        { return VhdlTypes.T_INOUT; }
    "linkage"                      { return VhdlTypes.T_LINKAGE; }
    "out"                          { return VhdlTypes.T_OUT; }

    /* Declarations  */
    "array"                        { return VhdlTypes.T_ARRAY; }
    "access"                       { return VhdlTypes.T_ACCESS; }
    "constant"                     { return VhdlTypes.T_CONSTANT; }
    "file"                         { return VhdlTypes.T_FILE; }
    "new"                          { return VhdlTypes.T_NEW; }
    "signal"                       { return VhdlTypes.T_SIGNAL; }
    "shared"                       { return VhdlTypes.T_SHARED; }
    "subtype"                      { return VhdlTypes.T_SUBTYPE; }
    "range"                        { return VhdlTypes.T_RANGE; }
    "record"                       { return VhdlTypes.T_RECORD; }
    "type"                         { return VhdlTypes.T_TYPE; }
    "units"                        { return VhdlTypes.T_UNITS; }
    "others"                       { return VhdlTypes.T_OTHERS; }
    "downto"                       { return VhdlTypes.T_DOWNTO; }
    "to"                           { return VhdlTypes.T_TO; }
    "variable"                     { return VhdlTypes.T_VARIABLE; }

    "generic"                      { return VhdlTypes.T_GENERIC; }
    "port"                         { return VhdlTypes.T_PORT; }
    "map"                          { return VhdlTypes.T_MAP; }
    "open"                         { return VhdlTypes.T_OPEN; }

    "attribute"                    { return VhdlTypes.T_ATTRIBUTE; }
    "alias"                        { return VhdlTypes.T_ALIAS; }
    "label"                        { return VhdlTypes.T_LABEL; }

    "bus"                          { return VhdlTypes.T_BUS; }
    "register"                     { return VhdlTypes.T_REGISTER; }

    "group"                        { return VhdlTypes.T_GROUP; }
    "literal"                      { return VhdlTypes.T_LITERAL; }

    /* Operators */
    "("                            { return VhdlTypes.T_LEFT_PAREN; }
    ")"                            { return VhdlTypes.T_RIGHT_PAREN; }
    "["                            { return VhdlTypes.T_LEFT_BRACKET; }
    "]"                            { return VhdlTypes.T_RIGHT_BRACKET; }
    ";"                            { return VhdlTypes.T_SEMICOLON; }
    ":"                            { return VhdlTypes.T_COLON; }
    "."                            { return VhdlTypes.T_DOT; }
    ","                            { return VhdlTypes.T_COMMA; }
    "'"                            { return VhdlTypes.T_APOSTROPHE; }
    "|"                            { return VhdlTypes.T_CASE_OR; }
    "**"                           { return VhdlTypes.T_EXP; }
    "*"                            { return VhdlTypes.T_MUL; }
    "/"                            { return VhdlTypes.T_DIV; }
    "+"                            { return VhdlTypes.T_ADD; }
    "-"                            { return VhdlTypes.T_SUB; }
    "&"                            { return VhdlTypes.T_CONCAT; }
    "="                            { return VhdlTypes.T_EQ; }
    "/="                           { return VhdlTypes.T_NE; }
    "<"                            { return VhdlTypes.T_LT; }
    "<="                           { return VhdlTypes.T_LE; }
    ">"                            { return VhdlTypes.T_GT; }
    ">="                           { return VhdlTypes.T_GE; }

//    "<="                           { return VhdlTypes.T_NON_BLOCKING_ASSIGNMENT; }
    ":="                           { return VhdlTypes.T_BLOCKING_ASSIGNMENT; }
    "=>"                           { return VhdlTypes.T_MAP_ASSIGNMENT; }

    "abs"                          { return VhdlTypes.T_ABS; }
    "and"                          { return VhdlTypes.T_AND; }
    "mod"                          { return VhdlTypes.T_MOD; }
    "nand"                         { return VhdlTypes.T_NAND; }
    "nor"                          { return VhdlTypes.T_NOR; }
    "not"                          { return VhdlTypes.T_NOT; }
    "or"                           { return VhdlTypes.T_OR; }
    "rem"                          { return VhdlTypes.T_REM; }
    "rol"                          { return VhdlTypes.T_ROL; }
    "ror"                          { return VhdlTypes.T_ROR; }
    "sla"                          { return VhdlTypes.T_SLA; }
    "sll"                          { return VhdlTypes.T_SLL; }
    "sra"                          { return VhdlTypes.T_SRA; }
    "srl"                          { return VhdlTypes.T_SRL; }
    "xnor"                         { return VhdlTypes.T_XNOR; }
    "xor"                          { return VhdlTypes.T_XOR; }
    /* VHDL 2008 Operators */
    "??"                           { return VhdlTypes.T_QQ; }
    "?="                           { return VhdlTypes.T_QE; }
    "?/="                          { return VhdlTypes.T_QNE; }
    "?<"                           { return VhdlTypes.T_QLT; }
    "?<="                          { return VhdlTypes.T_QLE; }
    "?>"                           { return VhdlTypes.T_QGT; }
    "?>="                          { return VhdlTypes.T_QGE; }

    /* Builtin types*/
//    "bit"                          { return VhdlTypes.T_BIT; }
//    "bit_vector"                   { return VhdlTypes.T_BIT_VECTOR; }
//    "integer"                      { return VhdlTypes.T_INTEGER; }
//    "natural"                      { return VhdlTypes.T_NATURAL; }
//    "positive"                     { return VhdlTypes.T_POSITIVE; }
//    "real"                         { return VhdlTypes.T_REAL; }
//    "boolean"                      { return VhdlTypes.T_BOOLEAN; }
//    "character"                    { return VhdlTypes.T_CHARACTER; }
//    "string"                       { return VhdlTypes.T_STRING; }
//    "time"                         { return VhdlTypes.T_TIME; }
//    "delay_length"                 { return VhdlTypes.T_DELAY_LENGTH; }
//    "severity_level"               { return VhdlTypes.T_SEVERITY_LEVEL; }
//    "file_open_kind"               { return VhdlTypes.T_FILE_OPEN_KIND; }
//    "file_open_status"             { return VhdlTypes.T_FILE_OPEN_STATUS; }
//
//    /* std_logic_1164 types */
//    "std_logic"                    { return VhdlTypes.T_STD_LOGIC; }
//    "std_ulogic"                   { return VhdlTypes.T_STD_ULOGIC; }
//    "std_logic_vector"             { return VhdlTypes.T_STD_LOGIC_VECTOR; }
//    "std_ulogic_vector"            { return VhdlTypes.T_STD_ULOGIC_VECTOR; }
//
//    /* numeric_std types */
//    "signed"                       { return VhdlTypes.T_SIGNED; }
//    "unsigned"                     { return VhdlTypes.T_UNSIGNED; }

    /* Delay statements */
    "after"                        { return VhdlTypes.T_AFTER; }
    "inertial"                     { return VhdlTypes.T_INERTIAL; }
    "reject"                       { return VhdlTypes.T_REJECT; }
    "transport"                    { return VhdlTypes.T_TRANSPORT; }
    "until"                        { return VhdlTypes.T_UNTIL; }
    "wait"                         { return VhdlTypes.T_WAIT; }

    /* Misc. */
    "begin"                        { return VhdlTypes.T_BEGIN; }
    "end"                          { return VhdlTypes.T_END; }
    "is"                           { return VhdlTypes.T_IS; }
    "of"                           { return VhdlTypes.T_OF; }
    "on"                           { return VhdlTypes.T_ON; }
    "reverse_range"                { return VhdlTypes.T_REVERSE_RANGE; }

    {BIN_LIT}                      { return VhdlTypes.BINLIT; }
    {OCT_LIT}                      { return VhdlTypes.OCTLIT; }
    {HEX_LIT}                      { return VhdlTypes.HEXLIT; }

    {STR_LIT}                      { return VhdlTypes.STRLIT; }
    {IDENTIFIER}                   { return VhdlTypes.ID; }
    {END_OF_LINE_COMMENT}          { return VhdlTypes.COMMENT; }
    {REAL_LIT}                     { return VhdlTypes.REALLIT;}
    {CHR_LIT}                      { return VhdlTypes.CHRLIT;}
}

{WHITE_SPACE_CHAR}+                { return TokenType.WHITE_SPACE; }
.                                  { return TokenType.BAD_CHARACTER; }