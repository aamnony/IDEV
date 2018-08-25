package com.github.amnonya.hdleditor.vhdl.exceptions;

import com.github.amnonya.hdleditor.vhdl.psi.VhdlIdentifier;

public class DeclarationAlreadyExistsException extends Exception {

    private final VhdlIdentifier identifier;

    public DeclarationAlreadyExistsException(String message, VhdlIdentifier declarationId) {
        super(String.format(message, declarationId.getText()));
        this.identifier = declarationId;
    }

    public VhdlIdentifier getIdentifier() {
        return identifier;
    }
}
