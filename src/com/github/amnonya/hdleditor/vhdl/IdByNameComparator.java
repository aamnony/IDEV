package com.github.amnonya.hdleditor.vhdl;

import com.github.amnonya.hdleditor.vhdl.psi.VhdlIdentifier;

import java.util.Comparator;

/**
 * Comparator between {@link VhdlIdentifier}s.
 * The comparison is made by comparing the identifiers' text fields while ignoring letter case.
 */
public class IdByNameComparator implements Comparator<VhdlIdentifier> {
    public static final IdByNameComparator INSTANCE = new IdByNameComparator();

    @Override
    public int compare(VhdlIdentifier id1, VhdlIdentifier id2) {
        return id1.getText().compareToIgnoreCase(id2.getText());
    }

    public static boolean match(VhdlIdentifier id1, VhdlIdentifier id2) {
        return (id1 != id2) && (INSTANCE.compare(id1, id2) == 0);
    }
}
