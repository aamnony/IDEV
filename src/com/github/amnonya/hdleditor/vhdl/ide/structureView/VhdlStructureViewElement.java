package com.github.amnonya.hdleditor.vhdl.ide.structureView;

import com.github.amnonya.hdleditor.vhdl.psi.VhdlArchitectureDeclarativePart;
import com.github.amnonya.hdleditor.vhdl.psi.VhdlComponentDeclaration;
import com.github.amnonya.hdleditor.vhdl.psi.VhdlConstantDeclaration;
import com.github.amnonya.hdleditor.vhdl.psi.VhdlEntityHeader;
import com.github.amnonya.hdleditor.vhdl.psi.VhdlFile;
import com.github.amnonya.hdleditor.vhdl.psi.VhdlGenericClause;
import com.github.amnonya.hdleditor.vhdl.psi.VhdlInterfaceGenericDeclaration;
import com.github.amnonya.hdleditor.vhdl.psi.VhdlInterfacePortDeclaration;
import com.github.amnonya.hdleditor.vhdl.psi.VhdlPackageBodyDeclarativePart;
import com.github.amnonya.hdleditor.vhdl.psi.VhdlPackageDeclarativePart;
import com.github.amnonya.hdleditor.vhdl.psi.VhdlPortClause;
import com.github.amnonya.hdleditor.vhdl.psi.VhdlSignalDeclaration;
import com.github.amnonya.hdleditor.vhdl.psi.impl.VhdlArchitectureBodyImpl;
import com.github.amnonya.hdleditor.vhdl.psi.impl.VhdlEntityDeclarationImpl;
import com.github.amnonya.hdleditor.vhdl.psi.impl.VhdlPackageBodyImpl;
import com.github.amnonya.hdleditor.vhdl.psi.impl.VhdlPackageDeclarationImpl;
import com.intellij.ide.projectView.PresentationData;
import com.intellij.ide.structureView.StructureViewTreeElement;
import com.intellij.ide.util.treeView.smartTree.SortableTreeElement;
import com.intellij.ide.util.treeView.smartTree.TreeElement;
import com.intellij.navigation.ItemPresentation;
import com.intellij.psi.NavigatablePsiElement;
import com.intellij.psi.PsiElement;
import com.intellij.psi.util.PsiTreeUtil;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

public class VhdlStructureViewElement implements StructureViewTreeElement, SortableTreeElement {
    private final int index;
    private NavigatablePsiElement element;

    public VhdlStructureViewElement(NavigatablePsiElement element) {
        this(element, -1);
    }

    public VhdlStructureViewElement(NavigatablePsiElement element, int index) {
        this.element = element;
        this.index = index;
    }

    @Override
    public Object getValue() {
        return element;
    }

    @Override
    public void navigate(boolean requestFocus) {
        element.navigate(requestFocus);
    }

    @Override
    public boolean canNavigate() {
        return element.canNavigate();
    }

    @Override
    public boolean canNavigateToSource() {
        return element.canNavigateToSource();
    }

    @NotNull
    @Override
    public String getAlphaSortKey() {
        String presentableText = getPresentation().getPresentableText();
        return presentableText != null ? presentableText : "dummy";
    }

    @NotNull
    @Override
    public ItemPresentation getPresentation() {
        ItemPresentation presentation = element.getPresentation();
        if (index >= 0) {
            if (element instanceof VhdlInterfaceGenericDeclaration) {
                presentation = ((VhdlInterfaceGenericDeclaration) element).getPresentation(index);
            } else if (element instanceof VhdlInterfacePortDeclaration) {
                presentation = ((VhdlInterfacePortDeclaration) element).getPresentation(index);
            } else if (element instanceof VhdlConstantDeclaration) {
                presentation = ((VhdlConstantDeclaration) element).getPresentation(index);
            } else if (element instanceof VhdlSignalDeclaration) {
                presentation = ((VhdlSignalDeclaration) element).getPresentation(index);
            }
        }
        return presentation != null ? presentation : new PresentationData();
    }

    @NotNull
    @Override
    public TreeElement[] getChildren() {
        if (element instanceof VhdlFile) {
            Collection<TreeElement> entities = getTreeElements(VhdlEntityDeclarationImpl.class);
            Collection<TreeElement> architectures = getTreeElements(VhdlArchitectureBodyImpl.class);
            Collection<TreeElement> packages = getTreeElements(VhdlPackageDeclarationImpl.class);
            Collection<TreeElement> packageBodies = getTreeElements(VhdlPackageBodyImpl.class);
            List<TreeElement> children = new ArrayList<>(2);
            children.addAll(entities);
            children.addAll(architectures);
            children.addAll(packages);
            children.addAll(packageBodies);
            return children.toArray(new TreeElement[0]);
        } else if (element instanceof VhdlEntityDeclarationImpl) {
            VhdlEntityHeader entityHeader = ((VhdlEntityDeclarationImpl) element).getEntityHeader();
            Collection<TreeElement> generics = getGenericTreeElements(entityHeader);
            Collection<TreeElement> ports = getPortTreeElements(entityHeader);
            List<TreeElement> children = new ArrayList<>(10);
            children.addAll(generics);
            children.addAll(ports);
            return children.toArray(new TreeElement[0]);
        } else if (element instanceof VhdlArchitectureBodyImpl) {
            VhdlArchitectureDeclarativePart declarations = ((VhdlArchitectureBodyImpl) element).getArchitectureDeclarativePart();
            Collection<TreeElement> constants = getConstantTreeElements(declarations.getConstantDeclarationList());
            Collection<TreeElement> signals = getSignalTreeElements(declarations.getSignalDeclarationList());
            Collection<TreeElement> components = getComponentTreeElements(declarations.getComponentDeclarationList());
            List<TreeElement> children = new ArrayList<>(10);
            children.addAll(constants);
            children.addAll(signals);
            children.addAll(components);
            return children.toArray(new TreeElement[0]);
        } else if (element instanceof VhdlPackageDeclarationImpl) {
            VhdlPackageDeclarativePart declarations = ((VhdlPackageDeclarationImpl) element).getPackageDeclarativePart();
            Collection<TreeElement> constants = getConstantTreeElements(declarations.getConstantDeclarationList());
            Collection<TreeElement> signals = getSignalTreeElements(declarations.getSignalDeclarationList());
            Collection<TreeElement> components = getComponentTreeElements(declarations.getComponentDeclarationList());
            List<TreeElement> children = new ArrayList<>(10);
            children.addAll(constants);
            children.addAll(signals);
            children.addAll(components);
            return children.toArray(new TreeElement[0]);
        } else if (element instanceof VhdlPackageBodyImpl) {
            VhdlPackageBodyDeclarativePart declarations = ((VhdlPackageBodyImpl) element).getPackageBodyDeclarativePart();
            Collection<TreeElement> constants = getConstantTreeElements(declarations.getConstantDeclarationList());
            List<TreeElement> children = new ArrayList<>(10);
            children.addAll(constants);
            return children.toArray(new TreeElement[0]);
        } else if (element instanceof VhdlComponentDeclaration) {
            VhdlEntityHeader entityHeader = ((VhdlComponentDeclaration) element).getEntityHeader();
            Collection<TreeElement> generics = getGenericTreeElements(entityHeader);
            Collection<TreeElement> ports = getPortTreeElements(entityHeader);
            List<TreeElement> children = new ArrayList<>(10);
            children.addAll(generics);
            children.addAll(ports);
            return children.toArray(new TreeElement[0]);
        } else {
            return EMPTY_ARRAY;
        }
    }

    private Collection<TreeElement> getGenericTreeElements(VhdlEntityHeader entityHeader) {
        VhdlGenericClause clause = entityHeader.getGenericClause();
        if (clause != null) {
            List<VhdlInterfaceGenericDeclaration> declarations = clause.getGenericInterfaceList().getInterfaceGenericDeclarationList();
            List<TreeElement> children = new ArrayList<>(declarations.size());
            for (VhdlInterfaceGenericDeclaration declaration : declarations) {
                for (int i = 0; i < declaration.getIdentifierList().getIdentifierList().size(); i++) {
                    children.add(new VhdlStructureViewElement((NavigatablePsiElement) declaration, i));
                }
            }
            return children;
        }
        return Collections.emptyList();
    }

    private Collection<TreeElement> getPortTreeElements(VhdlEntityHeader entityHeader) {
        VhdlPortClause clause = entityHeader.getPortClause();
        if (clause != null) {
            List<VhdlInterfacePortDeclaration> declarations = clause.getPortInterfaceList().getInterfacePortDeclarationList();
            List<TreeElement> children = new ArrayList<>(declarations.size());
            for (VhdlInterfacePortDeclaration declaration : declarations) {
                for (int i = 0; i < declaration.getIdentifierList().getIdentifierList().size(); i++) {
                    children.add(new VhdlStructureViewElement((NavigatablePsiElement) declaration, i));
                }
            }
            return children;
        }
        return Collections.emptyList();
    }

    private Collection<TreeElement> getConstantTreeElements(List<VhdlConstantDeclaration> declarations) {
        List<TreeElement> children = new ArrayList<>(declarations.size());
        for (VhdlConstantDeclaration declaration : declarations) {
            for (int i = 0; i < declaration.getIdentifierList().getIdentifierList().size(); i++) {
                children.add(new VhdlStructureViewElement((NavigatablePsiElement) declaration, i));
            }
        }
        return children;
    }

    private Collection<TreeElement> getSignalTreeElements(List<VhdlSignalDeclaration> declarations) {
        List<TreeElement> children = new ArrayList<>(declarations.size());
        for (VhdlSignalDeclaration declaration : declarations) {
            for (int i = 0; i < declaration.getIdentifierList().getIdentifierList().size(); i++) {
                children.add(new VhdlStructureViewElement((NavigatablePsiElement) declaration, i));
            }
        }
        return children;
    }

    private Collection<TreeElement> getComponentTreeElements(List<VhdlComponentDeclaration> declarations) {
        List<TreeElement> children = new ArrayList<>(declarations.size());
        for (VhdlComponentDeclaration declaration : declarations) {
            children.add(new VhdlStructureViewElement((NavigatablePsiElement) declaration));
        }
        return children;
    }

    private <T extends NavigatablePsiElement> Collection<TreeElement> getTreeElements(Class<T> tClass) {
        return getTreeElements(tClass, element);
    }

    private static <T extends NavigatablePsiElement> Collection<TreeElement> getTreeElements(Class<T> tClass, PsiElement element) {
        T[] ts = PsiTreeUtil.getChildrenOfType(element, tClass);
        if (ts != null) {
            List<TreeElement> children = new ArrayList<>(ts.length);
            for (T t : ts) {
                children.add(new VhdlStructureViewElement(t));
            }
            return children;
        }
        return Collections.emptyList();
    }
}
