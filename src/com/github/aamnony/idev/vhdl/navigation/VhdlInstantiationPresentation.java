package com.github.aamnony.idev.vhdl.navigation;

import com.github.aamnony.idev.vhdl.psi.VhdlComponentInstantiationStatement;
import com.github.aamnony.idev.vhdl.psi.VhdlIdentifier;
import com.github.aamnony.idev.vhdl.psi.VhdlInstantiatedUnit;
import com.github.aamnony.idev.vhdl.psi.VhdlSelectedName;
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

    @NotNull
    private Pair<String, String> getComponentName() {
        String entityName;
        String architectureName = null;
        VhdlInstantiatedUnit unit = instantiation.getInstantiatedUnit();
        VhdlSelectedName selectedName = unit.getSelectedName();
        List<VhdlIdentifier> ids = unit.getIdentifierList();
        if (selectedName != null) {
            entityName = selectedName.getText();
            if (ids.size() == 1) {
                architectureName = ids.get(0).getName();
            }
        } else {
            entityName = ids.get(0).getName();
            if (ids.size() == 2) {
                architectureName = ids.get(0).getName();
            }
        }
        return new Pair<>(entityName, architectureName);
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
