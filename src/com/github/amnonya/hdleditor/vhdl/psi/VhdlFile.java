package com.github.amnonya.hdleditor.vhdl.psi;

import com.github.amnonya.hdleditor.vhdl.fileTypes.VhdlFileType;
import com.github.amnonya.hdleditor.vhdl.lang.VhdlLanguage;
import com.intellij.extapi.psi.PsiFileBase;
import com.intellij.openapi.fileTypes.FileType;
import com.intellij.psi.FileViewProvider;

import org.jetbrains.annotations.NotNull;

public class VhdlFile extends PsiFileBase {
    public VhdlFile(@NotNull FileViewProvider viewProvider) {
        super(viewProvider, VhdlLanguage.INSTANCE);
    }

    @NotNull
    @Override
    public FileType getFileType() {
        return VhdlFileType.INSTANCE;
    }

    @Override
    public String toString() {
        return "VHDL File";
    }
}