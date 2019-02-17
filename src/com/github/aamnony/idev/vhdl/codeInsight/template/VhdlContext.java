package com.github.aamnony.idev.vhdl.codeInsight.template;

import com.github.aamnony.idev.vhdl.psi.VhdlFile;
import com.intellij.codeInsight.template.TemplateContextType;
import com.intellij.psi.PsiFile;

import org.jetbrains.annotations.NotNull;

public class VhdlContext extends TemplateContextType {
    protected VhdlContext() {
        super("VHDL", "VHDL");
    }

    @Override
    public boolean isInContext(@NotNull PsiFile file, int offset) {
        return file instanceof VhdlFile;
    }
}

