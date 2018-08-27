package com.github.amnonya.hdleditor.vhdl;

import com.github.amnonya.hdleditor.vhdl.psi.VhdlArchitectureBody;
import com.github.amnonya.hdleditor.vhdl.psi.VhdlEntityDeclaration;
import com.github.amnonya.hdleditor.vhdl.psi.VhdlIdentifier;
import com.github.amnonya.hdleditor.vhdl.psi.VhdlPackageDeclaration;
import com.github.amnonya.hdleditor.vhdl.psi.VhdlProcessStatement;
import com.github.amnonya.hdleditor.vhdl.psi.VhdlSubprogramBody;
import com.github.amnonya.hdleditor.vhdl.psi.VhdlSubprogramDeclaration;
import com.intellij.psi.PsiElement;

import java.util.Comparator;

/**
 * Comparator between {@link VhdlIdentifier}s.
 * The comparison is made by comparing the identifiers' scopes - the deeper the scope, the "smaller" the scope, i.e.
 * deeper scopes are first when sorting (ascending).
 */
public class IdByScopeComparator implements Comparator<VhdlIdentifier> {

    private static final int PROCESS_AND_SUBPROGRAM_BODY_SCORE = 4;
    private static final int SUBPROGRAM_DECLARATION_SCORE = 3;

    /**
     * Scope of {@link VhdlSubprogramDeclaration}s, {@link VhdlSubprogramBody}s and {@link VhdlProcessStatement}s.
     */
    public static final int DEEP_SCOPE = PROCESS_AND_SUBPROGRAM_BODY_SCORE;


    @Override
    public int compare(VhdlIdentifier id1, VhdlIdentifier id2) {
        PsiElement[] scopes1 = id1.getScopes();
        PsiElement[] scopes2 = id2.getScopes();
        int score1 = getScore(scopes1[0]);
        int score2 = getScore(scopes2[0]);
        return score2 - score1;
    }

    /**
     * Gets the score value of {@code scope}. The higher the score, the deeper the scope.
     *
     * @param scope The scope to get the score of.
     * @return The score of {@code scope}.
     */
    public static int getScore(PsiElement scope) {
        if (scope instanceof VhdlEntityDeclaration) {
            return 1;
        } else if (scope instanceof VhdlArchitectureBody) {
            return 2;
        } else if (scope instanceof VhdlSubprogramDeclaration) {
            return SUBPROGRAM_DECLARATION_SCORE;
        } else if (scope instanceof VhdlSubprogramBody) {
            return PROCESS_AND_SUBPROGRAM_BODY_SCORE;
        } else if (scope instanceof VhdlPackageDeclaration) {
            return 1;
        } else if (scope instanceof VhdlProcessStatement) {
            return PROCESS_AND_SUBPROGRAM_BODY_SCORE;
        }
        return 0;
    }
}
