package com.github.amnonya.hdleditor.vhdl.navigation;

import com.github.amnonya.hdleditor.vhdl.psi.VhdlComponentInstantiationStatement;
import com.github.amnonya.hdleditor.vhdl.psi.VhdlIdentifier;
import com.github.amnonya.hdleditor.vhdl.psi.VhdlInstantiatedUnit;
import com.github.amnonya.hdleditor.vhdl.psi.VhdlSelectedName;
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
        Pair<String, String> componentName = getComponentName();

        if (componentName.getSecond() != null) {
            return String.format("%s: %s(%s)", instanceName, componentName.getFirst(), componentName.getSecond());
        } else {
            return String.format("%s: %s", instanceName, componentName.getFirst());
        }
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
        return null;
    }

}
