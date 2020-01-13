package com.github.aamnony.idev.vhdl.project;

import com.github.aamnony.idev.vhdl.display.VhdlIcons;
import com.github.aamnony.idev.vhdl.lang.VhdlLanguage;
import com.intellij.ide.actions.CreateFileFromTemplateAction;
import com.intellij.ide.actions.CreateFileFromTemplateDialog;
import com.intellij.ide.fileTemplates.FileTemplateManager;
import com.intellij.ide.fileTemplates.JavaTemplateUtil;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.ui.InputValidatorEx;
import com.intellij.openapi.util.text.StringUtil;
import com.intellij.psi.PsiDirectory;

class CreateVhdlFileAction extends CreateFileFromTemplateAction {
    public CreateVhdlFileAction() {
        super("", "Create new VHDL file", VhdlIcons.FILE);
    }

    @Override
    protected void buildDialog(Project project, PsiDirectory directory, CreateFileFromTemplateDialog.Builder builder) {
        builder.setTitle("Create New VHDL File")
                .addKind("Module", VhdlIcons.ENTITY, "VhdlModule");
                //.addKind("Package", VhdlIcons.PACKAGE, JavaTemplateUtil.INTERNAL_INTERFACE_TEMPLATE_NAME);

        builder.setValidator(new InputValidatorEx() {
            @Override
            public String getErrorText(String inputString) {
                if (!VhdlLanguage.isLegalIdentifier(inputString)) {
                    return "This is not a valid VHDL identifier name";
                }
                return null;
            }

            @Override
            public boolean checkInput(String inputString) {
                return true;
            }

            @Override
            public boolean canClose(String inputString) {
                return !StringUtil.isEmptyOrSpaces(inputString) && getErrorText(inputString) == null;
            }
        });
    }

    @Override
    protected String getActionName(PsiDirectory directory, String newName, String templateName) {
        return getTemplatePresentation().getDescription() + " - " + "\"newName\"";
    }
}
