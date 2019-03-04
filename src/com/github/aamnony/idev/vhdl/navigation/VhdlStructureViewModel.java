package com.github.aamnony.idev.vhdl.navigation;

import com.github.aamnony.idev.vhdl.lang.VhdlFile;
import com.intellij.ide.structureView.StructureViewModel;
import com.intellij.ide.structureView.StructureViewModelBase;
import com.intellij.ide.structureView.StructureViewTreeElement;
import com.intellij.ide.util.treeView.smartTree.Sorter;
import com.intellij.openapi.editor.Editor;
import com.intellij.psi.PsiFile;

import org.jetbrains.annotations.NotNull;

public class VhdlStructureViewModel extends StructureViewModelBase implements StructureViewModel.ElementInfoProvider {
    public VhdlStructureViewModel(PsiFile psiFile, Editor editor) {
        super(psiFile, editor, new VhdlStructureViewElement(psiFile));
    }

    @NotNull
    public Sorter[] getSorters() {
        return new Sorter[]{Sorter.ALPHA_SORTER};
    }


    @Override
    public boolean isAlwaysShowsPlus(StructureViewTreeElement element) {
        return false;
    }

    @Override
    public boolean isAlwaysLeaf(StructureViewTreeElement element) {
        return element instanceof VhdlFile;
    }
}
