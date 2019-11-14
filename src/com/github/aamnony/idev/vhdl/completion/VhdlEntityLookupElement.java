package com.github.aamnony.idev.vhdl.completion;

import com.github.aamnony.idev.vhdl.display.VhdlIcons;
import com.github.aamnony.idev.vhdl.lang.VhdlArchitectureBody;
import com.github.aamnony.idev.vhdl.lang.VhdlElementFactory;
import com.github.aamnony.idev.vhdl.lang.VhdlEntityDeclaration;
import com.github.aamnony.idev.vhdl.lang.VhdlIdentifier;
import com.github.aamnony.idev.vhdl.lang.VhdlPsiTreeUtil;
import com.intellij.codeInsight.completion.InsertionContext;
import com.intellij.codeInsight.lookup.LookupElement;
import com.intellij.codeInsight.lookup.LookupElementPresentation;
import com.intellij.psi.PsiElement;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class VhdlEntityLookupElement extends LookupElement {

    private final VhdlIdentifier idToReplace;
    private final VhdlEntityDeclaration entity;
    private final boolean useExplicitInstantiation;

    public VhdlEntityLookupElement(VhdlIdentifier idToReplace, VhdlEntityDeclaration entity, boolean useExplicitInstantiation) {
        this.idToReplace = idToReplace;
        this.entity = entity;
        this.useExplicitInstantiation = useExplicitInstantiation;
    }

    @NotNull
    @Override
    public Object getObject() {
        return super.getObject();
    }

    @Nullable
    @Override
    public PsiElement getPsiElement() {
        return entity;
    }

    @Override
    public void renderElement(LookupElementPresentation presentation) {
        super.renderElement(presentation);
        presentation.appendTailTextItalic("_inst", false);
        presentation.setIcon(VhdlIcons.INSTANTIATION);
        if (useExplicitInstantiation) {
            presentation.setTypeText("Explicit Instantiation");
        } else {
            presentation.setTypeText("Implicit Instantiation");
        }
    }

    @NotNull
    @Override
    public String getLookupString() {
        VhdlIdentifier vhdlIdentifier = entity.getIdentifierList().get(0);
        String name = vhdlIdentifier.getName();
        assert name != null;
        return name;
    }

    @Override
    public void handleInsert(InsertionContext context) {
        String[] inst = VhdlElementFactory.createInstantiation(context.getProject(), entity, useExplicitInstantiation);
        if (!useExplicitInstantiation) {
            VhdlArchitectureBody arch = VhdlPsiTreeUtil.getArchitectureBody(idToReplace);
            if (arch != null) {
                context.getDocument().insertString(arch.getArchitectureDeclarativePart().getTextOffset(), inst[1]);
            }
        }
        context.getDocument().replaceString(context.getStartOffset(), context.getSelectionEndOffset(), inst[0]);
    }
}
