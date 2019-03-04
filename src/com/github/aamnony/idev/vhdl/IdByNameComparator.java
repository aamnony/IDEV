package com.github.aamnony.idev.vhdl;

import com.github.aamnony.idev.vhdl.lang.VhdlIdentifier;
import com.intellij.codeInsight.completion.CompletionUtil;

import java.util.Comparator;

/**
 * Comparator between {@link VhdlIdentifier}s.
 * The comparison is made by comparing the identifiers' text fields while ignoring letter case.
 */
public class IdByNameComparator implements Comparator<VhdlIdentifier> {
    public static final IdByNameComparator INSTANCE = new IdByNameComparator();

    @Override
    public int compare(VhdlIdentifier id1, VhdlIdentifier id2) {
        String text1 = id1.getText();
        String text2 = id2.getText();

        if (text1.endsWith(CompletionUtil.DUMMY_IDENTIFIER_TRIMMED)) {
            return startsWith(text1, text2);
        }
        if (text2.endsWith(CompletionUtil.DUMMY_IDENTIFIER_TRIMMED)) {
            return startsWith(text2, text1);
        }
        return text1.compareToIgnoreCase(text2);
    }

    private int startsWith(String needleWithDummy, String haystack) {
        String needle = needleWithDummy.substring(0, needleWithDummy.length() - CompletionUtil.DUMMY_IDENTIFIER_TRIMMED.length());
        return haystack.toLowerCase().startsWith(needle.toLowerCase()) ? 0 : 1;
    }

    public static boolean match(VhdlIdentifier id1, VhdlIdentifier id2) {
        return (id1 != id2) && (INSTANCE.compare(id1, id2) == 0);
    }
}
