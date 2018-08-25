package com.github.amnonya.hdleditor.vhdl.lexer;

import com.intellij.lexer.FlexAdapter;

public class VhdlLexerAdapter extends FlexAdapter {
    public VhdlLexerAdapter() {
        super(new VhdlLexer(null));
    }
}