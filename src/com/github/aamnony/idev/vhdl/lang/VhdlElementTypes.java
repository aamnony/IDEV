package com.github.aamnony.idev.vhdl.lang;

import com.intellij.psi.tree.TokenSet;

public class VhdlElementTypes {
    public static final TokenSet DECLARATIONS = TokenSet.create(
            VhdlTypes.ALIAS_DECLARATION, VhdlTypes.ATTRIBUTE_DECLARATION, VhdlTypes.COMPONENT_DECLARATION, VhdlTypes.CONSTANT_DECLARATION,
            VhdlTypes.SIGNAL_DECLARATION, VhdlTypes.VARIABLE_DECLARATION, VhdlTypes.FILE_DECLARATION,
            VhdlTypes.INTERFACE_GENERIC_DECLARATION, VhdlTypes.INTERFACE_PORT_DECLARATION,
            VhdlTypes.ELEMENT_DECLARATION, VhdlTypes.FULL_TYPE_DECLARATION, VhdlTypes.INCOMPLETE_TYPE_DECLARATION, VhdlTypes.SUBTYPE_DECLARATION,

            VhdlTypes.SUBPROGRAM_DECLARATION, VhdlTypes.SUBPROGRAM_BODY, VhdlTypes.FUNCTION_PARAMETER_CONSTANT_DECLARATION,
            VhdlTypes.FUNCTION_PARAMETER_SIGNAL_DECLARATION, VhdlTypes.PROCEDURE_PARAMETER_CONSTANT_DECLARATION,
            VhdlTypes.PROCEDURE_PARAMETER_SIGNAL_DECLARATION, VhdlTypes.PROCEDURE_PARAMETER_VARIABLE_DECLARATION,
            VhdlTypes.SUBPROGRAM_PARAMETER_FILE_DECLARATION
    );
    public static final TokenSet STATEMENTS = TokenSet.create(
            VhdlTypes.ASSERTION_STATEMENT, VhdlTypes.REPORT_STATEMENT, VhdlTypes.BLOCK_STATEMENT, VhdlTypes.CASE_STATEMENT,
            VhdlTypes.CASE_STATEMENT_ALTERNATIVE, VhdlTypes.COMPONENT_INSTANTIATION_STATEMENT, VhdlTypes.CONCURRENT_ASSERTION_STATEMENT,
            VhdlTypes.CONCURRENT_PROCEDURE_CALL_STATEMENT, VhdlTypes.CONDITIONAL_SIGNAL_ASSIGNMENT, VhdlTypes.SELECTED_SIGNAL_ASSIGNMENT,
            VhdlTypes.EXIT_STATEMENT, VhdlTypes.GENERATE_STATEMENT, VhdlTypes.IF_STATEMENT, VhdlTypes.LOOP_STATEMENT, VhdlTypes.NEXT_STATEMENT,
            VhdlTypes.NULL_STATEMENT, VhdlTypes.PROCEDURE_CALL_STATEMENT, VhdlTypes.PROCESS_STATEMENT,VhdlTypes.RETURN_STATEMENT,
            VhdlTypes.SIGNAL_ASSIGNMENT_STATEMENT, VhdlTypes.VARIABLE_ASSIGNMENT_STATEMENT, VhdlTypes.WAIT_STATEMENT
    );
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
    public static final TokenSet ADDITIVE_OPERATORS = TokenSet.create(VhdlTypes.T_ADD, VhdlTypes.T_SUB, VhdlTypes.T_CONCAT);
    public static final TokenSet ASSIGNMENT_OPERATORS = TokenSet.create(VhdlTypes.T_LE, VhdlTypes.T_BLOCKING_ASSIGNMENT, VhdlTypes.T_MAP_ASSIGNMENT);
    public static final TokenSet EQUALITY_OPERATORS = TokenSet.create(
            VhdlTypes.T_EQ, VhdlTypes.T_NE,
            // VHDL 2008 Operators:
            VhdlTypes.T_QQ, VhdlTypes.T_QE, VhdlTypes.T_QNE
    );
    public static final TokenSet LOGICAL_OPERATORS = TokenSet.create(
            VhdlTypes.T_NOT, VhdlTypes.T_AND, VhdlTypes.T_OR, VhdlTypes.T_NAND, VhdlTypes.T_NOR, VhdlTypes.T_XOR, VhdlTypes.T_XNOR
    );
    public static final TokenSet MULTIPLICATIVE_OPERATORS = TokenSet.create(VhdlTypes.T_EXP, VhdlTypes.T_MUL, VhdlTypes.T_DIV);
    public static final TokenSet MULTIPLICATIVE_WORD_OPERATORS = TokenSet.create(VhdlTypes.T_ABS, VhdlTypes.T_MOD, VhdlTypes.T_REM);
    public static final TokenSet RELATIONAL_OPERATORS = TokenSet.create(
            VhdlTypes.T_LT, VhdlTypes.T_GT, VhdlTypes.T_GE,
            // VHDL 2008 Operators:
            VhdlTypes.T_QLT, VhdlTypes.T_QLE, VhdlTypes.T_QGT, VhdlTypes.T_QGE
    );
    public static final TokenSet SHIFT_OPERATORS = TokenSet.create(
            VhdlTypes.T_ROL, VhdlTypes.T_ROR, VhdlTypes.T_SLA, VhdlTypes.T_SLL, VhdlTypes.T_SRA, VhdlTypes.T_SRL
    );
    public static final TokenSet OPERATORS = TokenSet.orSet(
            ADDITIVE_OPERATORS, ASSIGNMENT_OPERATORS, EQUALITY_OPERATORS, LOGICAL_OPERATORS, RELATIONAL_OPERATORS,
            MULTIPLICATIVE_OPERATORS, MULTIPLICATIVE_WORD_OPERATORS, SHIFT_OPERATORS
    );
    public static final TokenSet OPERATORS_2 = TokenSet.create(VhdlTypes.T_APOSTROPHE);
    public static final TokenSet PARENTHESES = TokenSet.create(VhdlTypes.T_LEFT_PAREN, VhdlTypes.T_RIGHT_PAREN);
    public static final TokenSet SEMICOLONS = TokenSet.create(VhdlTypes.T_COLON, VhdlTypes.T_SEMICOLON);
}
