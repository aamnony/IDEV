package com.github.aamnony.idev.vhdl.lang;

import com.intellij.lang.Language;

public class VhdlLanguage extends Language {
    public static final VhdlLanguage INSTANCE = new VhdlLanguage();

    private VhdlLanguage() {
        super("VHDL");
    }
}