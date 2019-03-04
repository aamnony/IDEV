package com.github.aamnony.idev.vhdl.lang;

import com.intellij.lexer.FlexAdapter;

public class VhdlLexerAdapter extends FlexAdapter {
    public VhdlLexerAdapter() {
        super(new VhdlLexer(null));
    }
}