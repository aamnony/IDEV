package com.github.amnonya.hdleditor.vhdl.psi.codeStyle;

import com.github.amnonya.hdleditor.vhdl.lang.VhdlLanguage;
import com.intellij.application.options.CodeStyleAbstractConfigurable;
import com.intellij.application.options.CodeStyleAbstractPanel;
import com.intellij.application.options.TabbedLanguageCodeStylePanel;
import com.intellij.openapi.options.Configurable;
import com.intellij.psi.codeStyle.CodeStyleSettings;
import com.intellij.psi.codeStyle.CodeStyleSettingsProvider;
import com.intellij.psi.codeStyle.CustomCodeStyleSettings;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class VhdlCodeStyleSettingsProvider extends CodeStyleSettingsProvider {
    @Override
    public CustomCodeStyleSettings createCustomSettings(CodeStyleSettings settings) {
        return new VhdlCodeStyleSettings(settings);
    }

    @Nullable
    @Override
    public String getConfigurableDisplayName() {
        return VhdlLanguage.INSTANCE.getDisplayName();
    }

    @NotNull
    @Override
    public Configurable createSettingsPage(CodeStyleSettings settings, CodeStyleSettings originalSettings) {
        return new CodeStyleAbstractConfigurable(settings, originalSettings, "VHDL") {
            @Override
            protected CodeStyleAbstractPanel createPanel(CodeStyleSettings settings) {
                return new VhdlCodeStyleMainPanel(getCurrentSettings(), settings);
            }

            @Nullable
            @Override
            public String getHelpTopic() {
                return null;
            }
        };
    }

    private static class VhdlCodeStyleMainPanel extends TabbedLanguageCodeStylePanel {
        public VhdlCodeStyleMainPanel(CodeStyleSettings currentSettings, CodeStyleSettings settings) {
            super(VhdlLanguage.INSTANCE, currentSettings, settings);
        }
    }
}
