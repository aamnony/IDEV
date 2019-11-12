package com.github.aamnony.idev.vhdl.completion;

import com.github.aamnony.idev.vhdl.lang.VhdlElementFactory;
import com.github.aamnony.idev.vhdl.lang.VhdlEntityDeclaration;
import com.github.aamnony.idev.vhdl.lang.VhdlIdentifier;
import com.github.aamnony.idev.vhdl.lang.VhdlPsiImplUtil;
import com.github.aamnony.idev.vhdl.lang.VhdlPsiTreeUtil;
import com.intellij.codeInsight.lookup.LookupElement;
import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.actionSystem.PlatformDataKeys;
import com.intellij.openapi.application.Result;
import com.intellij.openapi.command.WriteCommandAction;
import com.intellij.openapi.editor.CaretModel;
import com.intellij.openapi.editor.Document;
import com.intellij.openapi.editor.ex.EditorEx;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.util.TextRange;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiFile;
import com.intellij.psi.PsiManager;
import com.intellij.psi.PsiReference;

import org.jetbrains.annotations.NotNull;

public class VhdlInstantiationAction extends AnAction {

    @Override
    public void update(AnActionEvent e) {
        EditorEx editor = (EditorEx) e.getData(PlatformDataKeys.EDITOR);
        if (editor == null) {
            return;
        }
        final Project project = editor.getProject();
        if (project == null) {
            return;
        }
        final PsiFile psiFile = PsiManager.getInstance(project).findFile(editor.getVirtualFile());
        if (psiFile == null) {
            return;
        }
        final CaretModel caretModel = editor.getCaretModel();
        final Document document = editor.getDocument();
        final int offset = caretModel.getOffset();

        PsiElement element = psiFile.findElementAt(offset);
        e.getPresentation().setText("Create instance of " + element.getText().replace("_","__"));
    }

    @Override
    public void actionPerformed(AnActionEvent e) {
        EditorEx editor = (EditorEx) e.getData(PlatformDataKeys.EDITOR);
        if (editor == null) {
            return;
        }
        final Project project = editor.getProject();
        if (project == null) {
            return;
        }
        final PsiFile psiFile = PsiManager.getInstance(project).findFile(editor.getVirtualFile());
        if (psiFile == null) {
            return;
        }
        final CaretModel caretModel = editor.getCaretModel();
        final Document document = editor.getDocument();
        final int offset = caretModel.getOffset();

        PsiElement element = psiFile.findElementAt(offset);
        VhdlEntityDeclaration entity = VhdlPsiTreeUtil.getEntityDeclaration(element);
        if (entity == null) {
            PsiReference[] refs = element.getParent().getReferences();
            PsiElement declaration = ((LookupElement)refs[0].getVariants()[0]).getPsiElement();
            entity = VhdlPsiTreeUtil.getEntityDeclaration(declaration);
        }
        String inst = VhdlElementFactory.createInstantiation(project, entity);
        if (inst == null) {
            return;
        }
//        inst = "architecture temp of temp is begin " + inst + "end;";
//        PsiElement psi = CodeStyleManager.getInstance(project).reformat(VhdlElementFactory.createFile(project, inst));
//        String inst2 = psi.getText();
        new WriteCommandAction(project) {
            @Override
            protected void run(@NotNull Result result) {
                TextRange textRange = element.getTextRange();
                document.replaceString(textRange.getStartOffset(), textRange.getEndOffset(), inst);
            }
        }.execute();
    }
}
