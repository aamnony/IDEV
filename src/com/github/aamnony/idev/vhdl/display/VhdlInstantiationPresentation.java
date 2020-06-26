package com.github.aamnony.idev.vhdl.display;

import com.github.aamnony.idev.vhdl.lang.VhdlComponentInstantiationStatement;
import com.github.aamnony.idev.vhdl.lang.VhdlIdentifier;
import com.github.aamnony.idev.vhdl.lang.VhdlInstantiatedUnit;
import com.github.aamnony.idev.vhdl.lang.VhdlSelectedName;
import com.intellij.navigation.ItemPresentation;
import com.intellij.openapi.util.Pair;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

import javax.swing.Icon;

public class VhdlInstantiationPresentation implements ItemPresentation {
    private final VhdlComponentInstantiationStatement instantiation;

    public VhdlInstantiationPresentation(VhdlComponentInstantiationStatement instantiation) {
        this.instantiation = instantiation;
    }

    @Override
    public String getPresentableText() {
        String instanceName = instantiation.getLabel().getIdentifier().getName();
        String type = instantiation.getType();
        return String.format("%s: %s", instanceName, type);
    }

    @Nullable
    @Override
    public String getLocationString() {
        return null;
    }

    @Override
    public Icon getIcon(boolean unused) {
        return instantiation.getIcon(0);
    }

}
