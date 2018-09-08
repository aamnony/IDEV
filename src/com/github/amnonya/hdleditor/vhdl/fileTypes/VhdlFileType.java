package com.github.amnonya.hdleditor.vhdl.fileTypes;

import com.github.amnonya.hdleditor.vhdl.VhdlIcons;
import com.github.amnonya.hdleditor.vhdl.lang.VhdlLanguage;
import com.intellij.openapi.fileTypes.LanguageFileType;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import javax.swing.Icon;


public class VhdlFileType extends LanguageFileType {
    public static final VhdlFileType INSTANCE = new VhdlFileType();

    private VhdlFileType() {
        super(VhdlLanguage.INSTANCE);
    }

    @NotNull
    @Override
    public String getName() {
        return "VHDL file";
    }

    @NotNull
    @Override
    public String getDescription() {
        return "VHDL language file";
    }

    @NotNull
    @Override
    public String getDefaultExtension() {
        return "vhd";
    }

    @Nullable
    @Override
    public Icon getIcon() {
        return VhdlIcons.FILE;
    }
}