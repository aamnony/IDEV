package com.github.aamnony.idev.vhdl.formatting;

import com.github.aamnony.idev.vhdl.lang.VhdlElementTypes;
import com.github.aamnony.idev.vhdl.lang.VhdlLanguage;
import com.github.aamnony.idev.vhdl.lang.VhdlTypes;
import com.intellij.formatting.ASTBlock;
import com.intellij.formatting.Alignment;
import com.intellij.formatting.Block;
import com.intellij.formatting.ChildAttributes;
import com.intellij.formatting.Indent;
import com.intellij.formatting.Spacing;
import com.intellij.formatting.SpacingBuilder;
import com.intellij.formatting.Wrap;
import com.intellij.formatting.WrapType;
import com.intellij.lang.ASTNode;
import com.intellij.openapi.util.TextRange;
import com.intellij.psi.TokenType;
import com.intellij.psi.codeStyle.CodeStyleSettings;
import com.intellij.psi.codeStyle.CommonCodeStyleSettings;
import com.intellij.psi.formatter.FormatterUtil;
import com.intellij.psi.tree.IElementType;
import com.intellij.psi.tree.TokenSet;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

class VhdlBlock implements ASTBlock {

    private final VhdlBlock myParent;
    private final Alignment myAlignment;
    private final Indent myIndent;
    private final ASTNode myNode;
    private final Wrap myWrap;
    private final SpacingBuilder mySpacingBuilder;
    private final CodeStyleSettings mySettings;
    private List<VhdlBlock> mySubBlocks = null;
    private Map<ASTNode, VhdlBlock> mySubBlockByNode = null;

    // Shared among multiple children sub-blocks
    private Alignment[] myListAlignments = null;
    private Boolean myIncomplete;

    private static final TokenSet OBJECT_STATEMENT_BLOCKS = TokenSet.create(
            VhdlTypes.ARCHITECTURE_STATEMENT_PART, VhdlTypes.BLOCK_STATEMENT_PART,
            VhdlTypes.GENERATE_STATEMENT, VhdlTypes.SEQUENCE_OF_STATEMENTS, VhdlTypes.AGGREGATE, VhdlTypes.MAP_LIST
    );
    private static final TokenSet OBJECT_ASSIGNMENT_STATEMENT_TYPES = TokenSet.create(
            VhdlTypes.SIGNAL_ASSIGNMENT_STATEMENT, VhdlTypes.CONCURRENT_SIGNAL_ASSIGNMENT_STATEMENT,
            VhdlTypes.VARIABLE_ASSIGNMENT_STATEMENT, VhdlTypes.ELEMENT_ASSOCIATION
    );
    private static final TokenSet OBJECT_DECLARATION_BLOCKS = TokenSet.create(
            VhdlTypes.ARCHITECTURE_DECLARATIVE_PART, VhdlTypes.BLOCK_DECLARATIVE_PART, VhdlTypes.PACKAGE_DECLARATIVE_PART,
            VhdlTypes.PACKAGE_BODY_DECLARATIVE_PART, VhdlTypes.PROCESS_DECLARATIVE_PART, VhdlTypes.SUBPROGRAM_DECLARATIVE_PART,
            VhdlTypes.GENERATE_STATEMENT
    );
    private static final TokenSet OBJECT_DECLARATION_TYPES = TokenSet.create(
            VhdlTypes.CONSTANT_DECLARATION, VhdlTypes.SIGNAL_DECLARATION, VhdlTypes.VARIABLE_DECLARATION,
            VhdlTypes.FILE_DECLARATION, VhdlTypes.ATTRIBUTE_DECLARATION
    );
    private static final TokenSet SUBPROGRAM_PARAMETER_LISTS = TokenSet.create(
            VhdlTypes.FUNCTION_PARAMETER_LIST, VhdlTypes.PROCEDURE_PARAMETER_LIST
    );
    private static final TokenSet SUBPROGRAM_PARAMETER_DECLARATION_TYPES = TokenSet.create(
            VhdlTypes.FUNCTION_PARAMETER_CONSTANT_DECLARATION, VhdlTypes.FUNCTION_PARAMETER_SIGNAL_DECLARATION,
            VhdlTypes.PROCEDURE_PARAMETER_CONSTANT_DECLARATION, VhdlTypes.PROCEDURE_PARAMETER_SIGNAL_DECLARATION,
            VhdlTypes.PROCEDURE_PARAMETER_VARIABLE_DECLARATION, VhdlTypes.SUBPROGRAM_PARAMETER_FILE_DECLARATION
    );
    private static final TokenSet NORMAL_INDENTED_BLOCKS = TokenSet.create(
            VhdlTypes.USE_CLAUSE, VhdlTypes.ENTITY_HEADER, VhdlTypes.GENERIC_INTERFACE_LIST,
            VhdlTypes.PORT_INTERFACE_LIST, VhdlTypes.ARCHITECTURE_DECLARATIVE_PART,
            VhdlTypes.ARCHITECTURE_STATEMENT_PART, VhdlTypes.PROCESS_DECLARATIVE_PART,
            VhdlTypes.CASE_STATEMENT_ALTERNATIVE, VhdlTypes.SEQUENCE_OF_STATEMENTS, VhdlTypes.PACKAGE_DECLARATIVE_PART,
            VhdlTypes.PACKAGE_BODY_DECLARATIVE_PART, VhdlTypes.SUBPROGRAM_DECLARATIVE_PART,
            /*TODO: fix later: VhdlTypes.ENUMERATION_LIST,*/
            VhdlTypes.ELEMENT_DECLARATION, VhdlTypes.PROCEDURE_PARAMETER_LIST, VhdlTypes.FUNCTION_PARAMETER_LIST,
            VhdlTypes.BLOCK_DECLARATIVE_PART, VhdlTypes.BLOCK_STATEMENT_PART, VhdlTypes.BLOCK_HEADER,
            VhdlTypes.COMPONENT_INSTANTIATION_MAP, VhdlTypes.MAP_LIST,
            VhdlTypes.GENERATE_DECLARATIVE_PART, VhdlTypes.GENERATE_STATEMENT_PART
    );
    private static final TokenSet LABEL_INDENTED_BLOCKS = TokenSet.create(VhdlTypes.LABEL);
    private static final TokenSet ALWAYS_WRAPPED_BLOCKS = TokenSet.create(
            VhdlTypes.GENERIC_CLAUSE, VhdlTypes.PORT_CLAUSE, VhdlTypes.INTERFACE_GENERIC_DECLARATION,
            VhdlTypes.INTERFACE_PORT_DECLARATION, VhdlTypes.ARCHITECTURE_DECLARATIVE_PART,
            VhdlTypes.ARCHITECTURE_STATEMENT_PART, VhdlTypes.PROCESS_DECLARATIVE_PART,
            VhdlTypes.CASE_STATEMENT_ALTERNATIVE, VhdlTypes.SEQUENCE_OF_STATEMENTS, VhdlTypes.T_BEGIN, VhdlTypes.T_END,
            VhdlTypes.PACKAGE_DECLARATIVE_PART, VhdlTypes.PACKAGE_BODY_DECLARATIVE_PART,
            VhdlTypes.SUBPROGRAM_DECLARATIVE_PART, VhdlTypes.ENUMERATION_LITERAL,
            VhdlTypes.ELEMENT_DECLARATION, VhdlTypes.T_RECORD, VhdlTypes.PROCEDURE_PARAMETER_CONSTANT_DECLARATION,
            VhdlTypes.PROCEDURE_PARAMETER_SIGNAL_DECLARATION, VhdlTypes.PROCEDURE_PARAMETER_VARIABLE_DECLARATION,
            VhdlTypes.FUNCTION_PARAMETER_CONSTANT_DECLARATION, VhdlTypes.FUNCTION_PARAMETER_SIGNAL_DECLARATION
    );
    private CommonCodeStyleSettings myCommonSettings;

    public VhdlBlock(@Nullable VhdlBlock parent, CodeStyleSettings settings, SpacingBuilder spacingBuilder,
                     @NotNull ASTNode node, @Nullable Alignment alignment, @NotNull Indent indent, @Nullable Wrap wrap) {
        myParent = parent;
        myAlignment = alignment;
        myIndent = indent;
        myNode = node;
        myWrap = wrap;
        mySpacingBuilder = spacingBuilder;
        mySettings = settings;
        myCommonSettings = mySettings.getCommonSettings(VhdlLanguage.INSTANCE);

        final IElementType myType = node.getElementType();
        if (myType == VhdlTypes.PORT_CLAUSE) {
            myListAlignments = fillAlignments(new Alignment[5]);
        } else if (myType == VhdlTypes.GENERIC_CLAUSE) {
            myListAlignments = fillAlignments(new Alignment[3]);
        } else if (OBJECT_DECLARATION_BLOCKS.contains(myType)) {
            myListAlignments = fillAlignments(new Alignment[3]);
        } else if (SUBPROGRAM_PARAMETER_LISTS.contains(myType)) {
            myListAlignments = fillAlignments(new Alignment[3]);
        } else if (OBJECT_STATEMENT_BLOCKS.contains(myType)) {
            myListAlignments = fillAlignments(new Alignment[2]);
        }
    }

    public ASTNode getNode() {
        return myNode;
    }

    @NotNull
    @Override
    public TextRange getTextRange() {
        return myNode.getTextRange();
    }

    @NotNull
    @Override
    public List<Block> getSubBlocks() {
        if (mySubBlocks == null) {
            mySubBlockByNode = buildSubBlocks();
            mySubBlocks = new ArrayList<>(mySubBlockByNode.values());
        }
        return Collections.unmodifiableList(mySubBlocks);
    }

    @Nullable
    @Override
    public Wrap getWrap() {
        return myWrap;
    }

    @Nullable
    @Override
    public Indent getIndent() {
        assert myIndent != null;
        return myIndent;
    }

    @Nullable
    @Override
    public Alignment getAlignment() {
        return myAlignment;
    }

    @Nullable
    @Override
    public Spacing getSpacing(@Nullable Block child1, @NotNull Block child2) {
        // TODO:
        return mySpacingBuilder.getSpacing(this, child1, child2);
    }

    @NotNull
    @Override
    public ChildAttributes getChildAttributes(final int newChildIndex) {
        if (newChildIndex < mySubBlocks.size()) {
            VhdlBlock childBlock = mySubBlocks.get(newChildIndex);
            IElementType childType = childBlock.myNode.getElementType();

            Indent childIndent = childBlock.myIndent;
            Alignment childAlignment = childBlock.myAlignment;

            if (childType == VhdlTypes.T_END) {
                childIndent = Indent.getNormalIndent();
            }

            return new ChildAttributes(childIndent, childAlignment);
        }
        else {
            return new ChildAttributes(myIndent, myAlignment);
        }
    }

    @Override
    public boolean isIncomplete() {
        if (myIncomplete == null) {
            myIncomplete = FormatterUtil.isIncomplete(myNode);
        }
        return myIncomplete;
    }

    @Override
    public boolean isLeaf() {
        return myNode.getFirstChildNode() == null;
    }

    @NotNull
    private Map<ASTNode, VhdlBlock> buildSubBlocks() {
        final Map<ASTNode, VhdlBlock> blocks = new LinkedHashMap<>();
        Iterable<ASTNode> childNodes = getSubBlockNodes();
        for (ASTNode child : childNodes) {
            final IElementType childType = child.getElementType();

            if (child.getTextRange().isEmpty()) {
                continue;
            }

            if (childType == TokenType.WHITE_SPACE) {
                continue;
            }

            blocks.put(child, buildSubBlock(child));
        }
        return Collections.unmodifiableMap(blocks);
    }

    @NotNull
    private Iterable<ASTNode> getSubBlockNodes() {
        return Arrays.asList(myNode.getChildren(null));
    }

    @NotNull
    private VhdlBlock buildSubBlock(@NotNull ASTNode child) {
        final IElementType myChildType = child.getElementType();
        final IElementType myType = myNode.getElementType();

        // Wrap:
        Wrap childWrap = Wrap.createWrap(WrapType.NONE, false);

//        if (ALWAYS_WRAPPED_BLOCKS.contains(myChildType)) {
//            childWrap = Wrap.createWrap(WrapType.ALWAYS, true);
//        }


        // Indent:
        Indent childIndent = Indent.getNoneIndent();

        if (NORMAL_INDENTED_BLOCKS.contains(myChildType)) {
            childIndent = Indent.getNormalIndent();
        } else if ((myChildType == VhdlTypes.T_SEVERITY)
                || (myChildType == VhdlTypes.T_REPORT && myType == VhdlTypes.ASSERTION)) {
            childIndent = Indent.getNormalIndent();
        } else if (LABEL_INDENTED_BLOCKS.contains(myChildType)) {
            childIndent = Indent.getLabelIndent();
        }


        // Alignment:
        Alignment childAlignment = null;

        if (myCommonSettings.ALIGN_GROUP_FIELD_DECLARATIONS) {
            if (myType == VhdlTypes.INTERFACE_GENERIC_DECLARATION) {
                if (myChildType == VhdlTypes.T_COLON) {
                    childAlignment = myParent.myParent.myListAlignments[0];
                } else if (myChildType == VhdlTypes.T_BLOCKING_ASSIGNMENT) {
                    childAlignment = myParent.myParent.myListAlignments[1];
                }
            } else if (myType == VhdlTypes.INTERFACE_PORT_DECLARATION) {
                if (myChildType == VhdlTypes.T_COLON) {
                    childAlignment = myParent.myParent.myListAlignments[0];
                } else if (myChildType == VhdlTypes.MODE) {
                    childAlignment = myParent.myParent.myListAlignments[1];
                } else if (myChildType == VhdlTypes.SUBTYPE_INDICATION) {
                    childAlignment = myParent.myParent.myListAlignments[2];
                } else if (myChildType == VhdlTypes.T_BLOCKING_ASSIGNMENT) {
                    childAlignment = myParent.myParent.myListAlignments[3];
                }
            } else if (myChildType == VhdlTypes.COMMENT) {
                Alignment commentAlignment = getGenericDeclarationCommentAlignment(child);
                if (commentAlignment == null) {
                    commentAlignment = getPortDeclarationCommentAlignment(child);
                }
                if (commentAlignment != null) {
                    childAlignment = commentAlignment;
                }
            }
        }
        if (myCommonSettings.ALIGN_CONSECUTIVE_VARIABLE_DECLARATIONS) {
            if (OBJECT_DECLARATION_TYPES.contains(myType) || SUBPROGRAM_PARAMETER_DECLARATION_TYPES.contains(myType)) {
                if (myChildType == VhdlTypes.T_COLON) {
                    childAlignment = myParent.myListAlignments[0];
                } else if (myChildType == VhdlTypes.T_BLOCKING_ASSIGNMENT) {
                    childAlignment = myParent.myListAlignments[1];
                }
            } else if (myChildType == VhdlTypes.COMMENT) {
                Alignment commentAlignment = getObjectDeclarationCommentAlignment(child);
                if (commentAlignment == null) {
                    commentAlignment = getSubprogramParameterDeclarationCommentAlignment(child);
                }
                if (commentAlignment == null) {
                    commentAlignment = getAttributeSpecificationCommentAlignment(child);
                }
                if (commentAlignment != null) {
                    childAlignment = commentAlignment;
                }
            }
        }
        if (myCommonSettings.ALIGN_SUBSEQUENT_SIMPLE_METHODS) {
            if (OBJECT_ASSIGNMENT_STATEMENT_TYPES.contains(myType)) {
                if (VhdlElementTypes.ASSIGNMENT_OPERATORS.contains(myChildType) ) {
                    childAlignment = myParent.myListAlignments[0];
                }
            } else if (myChildType == VhdlTypes.COMMENT) {
                Alignment commentAlignment = getObjectAssignmentStatementCommentAlignment(child);
                if (commentAlignment != null) {
                    childAlignment = commentAlignment;
                }
            }
        }

        // --------------------------------------------------------------------
        return new VhdlBlock(this, mySettings, mySpacingBuilder, child, childAlignment, childIndent, childWrap);
    }

    /**
     * Gets the alignment for {@code child} if it is a {@link VhdlTypes#COMMENT} describing a generic declaration.
     *
     * @param child The comment {@link ASTNode} to get the alignment for.
     * @return The alignment, or null if {@code child} is not a valid declaration comment.
     */
    @Nullable
    private Alignment getGenericDeclarationCommentAlignment(@NotNull ASTNode child) {
        IElementType myType = myNode.getElementType();
        ASTNode childPrevSibling = child.getTreePrev();
        if (childPrevSibling == null) {
            return null;
        }
        IElementType childPrevSiblingType = childPrevSibling.getElementType();

        if (myType == VhdlTypes.GENERIC_INTERFACE_LIST) {
            // No space between generic declaration and comment:
            boolean noSpace = childPrevSiblingType == VhdlTypes.T_SEMICOLON;
            // Space between generic declaration and comment, but they are still on the same line:
            boolean spaceWithoutLineBreak = childPrevSiblingType == TokenType.WHITE_SPACE && !childPrevSibling.getText().contains("\n");

            if (noSpace || spaceWithoutLineBreak) {
                // Comment is in the same line of the generic declaration, i.e. it describes the generic.
                // Generic is not the last on the list.
                return myParent.myListAlignments[2];
            }
        } else if (myType == VhdlTypes.GENERIC_CLAUSE) {
            // No space between generic declaration and comment:
            boolean noSpace = childPrevSiblingType == VhdlTypes.GENERIC_INTERFACE_LIST;
            // Space between generic declaration and comment, but they are still on the same line:
            boolean spaceWithoutLineBreak = childPrevSiblingType == TokenType.WHITE_SPACE && !childPrevSibling.getText().contains("\n");

            if (noSpace || spaceWithoutLineBreak) {
                // Comment is in the same line of the generic declaration, i.e. it describes the generic.
                // Generic is the last on the list.
                return myListAlignments[2];
            }
        }
        return null;
    }

    /**
     * Gets the alignment for {@code child} if it is a {@link VhdlTypes#COMMENT} describing a port declaration.
     *
     * @param child The comment {@link ASTNode} to get the alignment for.
     * @return The alignment, or null if {@code child} is not a valid declaration comment.
     */
    @Nullable
    private Alignment getPortDeclarationCommentAlignment(@NotNull ASTNode child) {
        IElementType myType = myNode.getElementType();
        ASTNode childPrevSibling = child.getTreePrev();
        if (childPrevSibling == null) {
            return null;
        }
        IElementType childPrevSiblingType = childPrevSibling.getElementType();

        if (myType == VhdlTypes.PORT_INTERFACE_LIST) {
            // No space between port declaration and comment:
            boolean noSpace = childPrevSiblingType == VhdlTypes.T_SEMICOLON;
            // Space between port declaration and comment, but they are still on the same line:
            boolean spaceWithoutLineBreak = childPrevSiblingType == TokenType.WHITE_SPACE && !childPrevSibling.getText().contains("\n");

            if (noSpace || spaceWithoutLineBreak) {
                // Comment is in the same line of the port declaration, i.e. it describes the port.
                // Port is not the last on the list.
                return myParent.myListAlignments[4];
            }
        } else if (myType == VhdlTypes.PORT_CLAUSE) {
            // No space between port declaration and comment:
            boolean noSpace = childPrevSiblingType == VhdlTypes.PORT_INTERFACE_LIST;
            // Space between port declaration and comment, but they are still on the same line:
            boolean spaceWithoutLineBreak = childPrevSiblingType == TokenType.WHITE_SPACE && !childPrevSibling.getText().contains("\n");

            if (noSpace || spaceWithoutLineBreak) {
                // Comment is in the same line of the port declaration, i.e. it describes the port.
                // Port is the last on the list.
                return myListAlignments[4];
            }
        }
        return null;
    }

    /**
     * Gets the alignment for {@code child} if it is a {@link VhdlTypes#COMMENT} describing an object declaration,
     * i.e. {@link #OBJECT_DECLARATION_TYPES}.
     *
     * @param child The comment {@link ASTNode} to get the alignment for.
     * @return The alignment, or null if {@code child} is not a valid declaration comment.
     */
    @Nullable
    private Alignment getObjectDeclarationCommentAlignment(@NotNull ASTNode child) {
        IElementType myType = myNode.getElementType();
        ASTNode childPrevSibling = child.getTreePrev();
        if (childPrevSibling == null) {
            return null;
        }
        IElementType childPrevSiblingType = childPrevSibling.getElementType();

        if (OBJECT_DECLARATION_BLOCKS.contains(myType)) {
            // No space between object declaration and comment:
            boolean noSpace = OBJECT_DECLARATION_TYPES.contains(childPrevSiblingType);
            // Space between object declaration and comment, but they are still on the same line:
            boolean spaceWithoutLineBreak = childPrevSiblingType == TokenType.WHITE_SPACE && !childPrevSibling.getText().contains("\n");

            if (noSpace || spaceWithoutLineBreak) {
                // Comment is in the same line of the object declaration, i.e. it describes the object.
                return myListAlignments[2];
            }
        }
        return null;
    }

    /**
     * Gets the alignment for {@code child} if it is a {@link VhdlTypes#COMMENT} describing an attribute specification,
     * i.e. {@link VhdlTypes#ATTRIBUTE_SPECIFICATION}.
     *
     * @param child The comment {@link ASTNode} to get the alignment for.
     * @return The alignment, or null if {@code child} is not a valid attribute specification comment.
     */
    @Nullable
    private Alignment getAttributeSpecificationCommentAlignment(@NotNull ASTNode child) {
        IElementType myType = myNode.getElementType();
        ASTNode childPrevSibling = child.getTreePrev();
        if (childPrevSibling == null) {
            return null;
        }
        IElementType childPrevSiblingType = childPrevSibling.getElementType();

        if (myType == VhdlTypes.ATTRIBUTE_SPECIFICATION) {
            // No space between attribute specification and comment:
            boolean noSpace = childPrevSiblingType == VhdlTypes.ATTRIBUTE_SPECIFICATION;
            // Space between attribute specification and comment, but they are still on the same line:
            boolean spaceWithoutLineBreak = childPrevSiblingType == TokenType.WHITE_SPACE && !childPrevSibling.getText().contains("\n");

            if (noSpace || spaceWithoutLineBreak) {
                // Comment is in the same line of the attribute specification , i.e. it describes the attribute.
                return myListAlignments[2];
            }
        }
        return null;
    }

    /**
     * Gets the alignment for {@code child} if it is a {@link VhdlTypes#COMMENT} describing a parameter declaration,
     * i.e. {@link #SUBPROGRAM_PARAMETER_DECLARATION_TYPES}.
     *
     * @param child The comment {@link ASTNode} to get the alignment for.
     * @return The alignment, or null if {@code child} is not a valid declaration comment.
     */
    @Nullable
    private Alignment getSubprogramParameterDeclarationCommentAlignment(@NotNull ASTNode child) {
        IElementType myType = myNode.getElementType();
        ASTNode childPrevSibling = child.getTreePrev();
        if (childPrevSibling == null) {
            return null;
        }
        IElementType childPrevSiblingType = childPrevSibling.getElementType();

        if (SUBPROGRAM_PARAMETER_LISTS.contains(myType)) {
            // No space between parameter declaration and comment:
            boolean noSpace = SUBPROGRAM_PARAMETER_DECLARATION_TYPES.contains(childPrevSiblingType);
            // Space between parameter declaration and comment, but they are still on the same line:
            boolean spaceWithoutLineBreak = childPrevSiblingType == TokenType.WHITE_SPACE && !childPrevSibling.getText().contains("\n");

            if (noSpace || spaceWithoutLineBreak) {
                // Comment is in the same line of the parameter declaration, i.e. it describes the parameter.
                return myListAlignments[2];
            }
        }
        return null;
    }

    /**
     * Gets the alignment for {@code child} if it is a {@link VhdlTypes#COMMENT} describing an object assignment statement,
     * i.e. {@link #OBJECT_ASSIGNMENT_STATEMENT_TYPES}.
     *
     * @param child The comment {@link ASTNode} to get the alignment for.
     * @return The alignment, or null if {@code child} is not a valid assignment statement comment.
     */
    @Nullable
    private Alignment getObjectAssignmentStatementCommentAlignment(@NotNull ASTNode child) {
        IElementType myType = myNode.getElementType();
        ASTNode childPrevSibling = child.getTreePrev();
        if (childPrevSibling == null) {
            return null;
        }
        IElementType childPrevSiblingType = childPrevSibling.getElementType();

        if (OBJECT_STATEMENT_BLOCKS.contains(myType)) {
            // No space between assignment statement and comment:
            boolean noSpace = OBJECT_ASSIGNMENT_STATEMENT_TYPES.contains(childPrevSiblingType);
            // Space between assignment statement and comment, but they are still on the same line:
            boolean spaceWithoutLineBreak = childPrevSiblingType == TokenType.WHITE_SPACE && !childPrevSibling.getText().contains("\n");

            if (noSpace || spaceWithoutLineBreak) {
                // Comment is in the same line of the assignment statement, i.e. it describes the statement.
                return myListAlignments[1];
            }
        }
        return null;
    }

    @NotNull
    private static Alignment[] fillAlignments(@NotNull Alignment[] alignments) {
        for (int i = 0; i < alignments.length; i++) {
            alignments[i] = Alignment.createAlignment(true);
        }
        return alignments;
    }
}