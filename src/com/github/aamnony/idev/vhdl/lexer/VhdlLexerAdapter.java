package com.github.aamnony.idev.vhdl.lexer;

import com.intellij.lexer.FlexAdapter;

public class VhdlLexerAdapter extends FlexAdapter {
    public VhdlLexerAdapter() {
        super(new VhdlLexer(null));
    }
}