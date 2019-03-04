package com.github.aamnony.idev.vhdl.lang;

import com.intellij.openapi.project.Project;
import com.intellij.psi.PsiFileFactory;
import com.intellij.psi.impl.source.tree.Factory;
import com.intellij.psi.impl.source.tree.LeafElement;

public class VhdlElementFactory {
    public static void rename(VhdlIdentifier id, String name) {
        LeafElement newId = Factory.createSingleLeafElement(VhdlTypes.ID, name, null, id.getManager());
        id.getNode().replaceChild(id.getFirstChild().getNode(), newId);
//        final VhdlFile file = createFile(id.getProject(), name);
//        PsiElement actualId = file.getFirstChild().getFirstChild();
//        id.getNode().replaceChild(id.getFirstChild().getNode(), actualId.getNode());
    }

    public static VhdlFile createFile(Project project, String text) {
        String name = "dummy.vhd";
        return (VhdlFile) PsiFileFactory.getInstance(project).
                createFileFromText(name, VhdlFileType.INSTANCE, text);
    }
}
