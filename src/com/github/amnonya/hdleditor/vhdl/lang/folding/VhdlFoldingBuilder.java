package com.github.amnonya.hdleditor.vhdl.lang.folding;

import com.github.amnonya.hdleditor.utils.StringUtils;
import com.github.amnonya.hdleditor.vhdl.psi.VhdlContextClause;
import com.intellij.lang.ASTNode;
import com.intellij.lang.folding.FoldingBuilderEx;
import com.intellij.lang.folding.FoldingDescriptor;
import com.intellij.openapi.editor.Document;
import com.intellij.openapi.util.TextRange;
import com.intellij.psi.PsiComment;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiWhiteSpace;
import com.intellij.psi.util.PsiTreeUtil;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class VhdlFoldingBuilder extends FoldingBuilderEx {
    @NotNull
    @Override
    public FoldingDescriptor[] buildFoldRegions(@NotNull PsiElement root, @NotNull Document document, boolean quick) {
        List<FoldingDescriptor> descriptors = new ArrayList<>();

        buildContextFoldRegions(root, descriptors);
        buildCommentBlockFoldRegions(root, descriptors);

        return descriptors.toArray(new FoldingDescriptor[0]);
    }

    @Nullable
    @Override
    public String getPlaceholderText(@NotNull ASTNode node) {
        PsiElement psi = node.getPsi();
        if (psi instanceof PsiComment) {
            return "-- ...";
        } else if (psi instanceof VhdlContextClause) {
            return "use ...";
        }
        return "...";
    }

    @Override
    public boolean isCollapsedByDefault(@NotNull ASTNode node) {
        return true;
    }

    private static void buildContextFoldRegions(@NotNull PsiElement root, List<FoldingDescriptor> descriptors) {
        Collection<VhdlContextClause> contextClauses = PsiTreeUtil.findChildrenOfType(root, VhdlContextClause.class);
        for (VhdlContextClause contextClause : contextClauses) {
            if (contextClause.getFirstChild() != null) {
                descriptors.add(new FoldingDescriptor(contextClause, contextClause.getTextRange()));
            }
        }
    }

    private static void buildCommentBlockFoldRegions(@NotNull PsiElement root, List<FoldingDescriptor> descriptors) {
        PsiComment commentStart = null;
        PsiComment commentEnd = null;

        Collection<PsiComment> comments = PsiTreeUtil.findChildrenOfType(root, PsiComment.class);
        for (PsiComment comment : comments) {
            if (commentStart == null) {
                PsiElement prevSibling = comment.getPrevSibling();
                if (prevSibling == null || (prevSibling instanceof PsiWhiteSpace && prevSibling.textContains('\n'))) {
                    // New comment block.
                    commentStart = comment;
                    commentEnd = comment;
                } else {
                    continue;
                }
            }
            PsiElement nextSibling = comment.getNextSibling();
            if (nextSibling instanceof PsiWhiteSpace && StringUtils.contains(nextSibling.getText(), "\n", 2)) {
                PsiElement nextNextSibling = nextSibling.getNextSibling();
                if (nextNextSibling instanceof PsiComment) {
                    commentEnd = (PsiComment) nextNextSibling;
                    continue;
                }
            }
            // End of comment block.
            if (commentEnd != commentStart) {
                TextRange range = new TextRange(commentStart.getTextOffset(), commentEnd.getTextOffset() + commentEnd.getTextLength());
                descriptors.add(new FoldingDescriptor(commentStart, range));
            }
            commentStart = null;
            commentEnd = null;
        }
    }
}

