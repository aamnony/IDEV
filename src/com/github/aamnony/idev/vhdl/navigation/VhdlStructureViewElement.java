package com.github.aamnony.idev.vhdl.navigation;

import com.github.aamnony.idev.vhdl.lang.VhdlArchitectureBody;
import com.github.aamnony.idev.vhdl.lang.VhdlArchitectureDeclarativePart;
import com.github.aamnony.idev.vhdl.lang.VhdlArchitectureStatementPart;
import com.github.aamnony.idev.vhdl.lang.VhdlBlockDeclarativePart;
import com.github.aamnony.idev.vhdl.lang.VhdlBlockHeader;
import com.github.aamnony.idev.vhdl.lang.VhdlBlockStatement;
import com.github.aamnony.idev.vhdl.lang.VhdlBlockStatementPart;
import com.github.aamnony.idev.vhdl.lang.VhdlComponentDeclaration;
import com.github.aamnony.idev.vhdl.lang.VhdlConstantDeclaration;
import com.github.aamnony.idev.vhdl.lang.VhdlEntityDeclaration;
import com.github.aamnony.idev.vhdl.lang.VhdlEntityHeader;
import com.github.aamnony.idev.vhdl.lang.VhdlFile;
import com.github.aamnony.idev.vhdl.lang.VhdlGenerateDeclarativePart;
import com.github.aamnony.idev.vhdl.lang.VhdlGenerateStatement;
import com.github.aamnony.idev.vhdl.lang.VhdlGenerateStatementPart;
import com.github.aamnony.idev.vhdl.lang.VhdlGenericClause;
import com.github.aamnony.idev.vhdl.lang.VhdlInterfaceGenericDeclaration;
import com.github.aamnony.idev.vhdl.lang.VhdlInterfacePortDeclaration;
import com.github.aamnony.idev.vhdl.lang.VhdlPackageBody;
import com.github.aamnony.idev.vhdl.lang.VhdlPackageBodyDeclarativePart;
import com.github.aamnony.idev.vhdl.lang.VhdlPackageDeclaration;
import com.github.aamnony.idev.vhdl.lang.VhdlPackageDeclarativePart;
import com.github.aamnony.idev.vhdl.lang.VhdlPortClause;
import com.github.aamnony.idev.vhdl.lang.VhdlProcessDeclarativePart;
import com.github.aamnony.idev.vhdl.lang.VhdlProcessStatement;
import com.github.aamnony.idev.vhdl.lang.VhdlSignalDeclaration;
import com.github.aamnony.idev.vhdl.lang.VhdlSubprogramBody;
import com.github.aamnony.idev.vhdl.lang.VhdlSubprogramDeclaration;
import com.github.aamnony.idev.vhdl.lang.VhdlSubprogramDeclarativePart;
import com.github.aamnony.idev.vhdl.lang.VhdlSubprogramSpecification;
import com.github.aamnony.idev.vhdl.lang.VhdlVariableDeclaration;
import com.github.aamnony.idev.vhdl.lang.impl.VhdlArchitectureBodyImpl;
import com.github.aamnony.idev.vhdl.lang.impl.VhdlEntityDeclarationImpl;
import com.github.aamnony.idev.vhdl.lang.impl.VhdlPackageBodyImpl;
import com.github.aamnony.idev.vhdl.lang.impl.VhdlPackageDeclarationImpl;
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
    private final NavigatablePsiElement element;

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
        return presentableText != null ? presentableText : "zzzdummy";
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
            } else if (element instanceof VhdlVariableDeclaration) {
                presentation = ((VhdlVariableDeclaration) element).getPresentation(index);
            }
        }
        return presentation != null ? presentation : new PresentationData();
    }

    @NotNull
    @Override
    public TreeElement[] getChildren() {
        if (element instanceof VhdlFile) {
            return getChildren((VhdlFile) element);
        } else if (element instanceof VhdlEntityDeclaration) {
            return getChildren((VhdlEntityDeclaration) element);
        } else if (element instanceof VhdlArchitectureBody) {
            return getChildren((VhdlArchitectureBody) this.element);
        } else if (element instanceof VhdlPackageDeclaration) {
            return getChildren((VhdlPackageDeclaration) element);
        } else if (element instanceof VhdlPackageBody) {
            return getChildren((VhdlPackageBody) element);
        } else if (element instanceof VhdlBlockStatement) {
            return getChildren((VhdlBlockStatement) this.element);
        } else if (element instanceof VhdlGenerateStatement) {
            return getChildren((VhdlGenerateStatement) this.element);
        } else if (element instanceof VhdlProcessStatement) {
            return getChildren((VhdlProcessStatement) element);
        } else if (element instanceof VhdlComponentDeclaration) {
            return getChildren((VhdlComponentDeclaration) element);
        } else if (element instanceof VhdlSubprogramSpecification) {
            PsiElement parent = element.getParent();
            if (parent instanceof VhdlSubprogramBody) {
                return getChildren((VhdlSubprogramBody) parent);
            }
        }
        return EMPTY_ARRAY;
    }

    @NotNull
    private static TreeElement[] getChildren(VhdlFile element) {
        List<TreeElement> children = new ArrayList<>(2);

        Collection<TreeElement> entities = getTreeElements(VhdlEntityDeclarationImpl.class, element);
        Collection<TreeElement> architectures = getTreeElements(VhdlArchitectureBodyImpl.class, element);
        Collection<TreeElement> packages = getTreeElements(VhdlPackageDeclarationImpl.class, element);
        Collection<TreeElement> packageBodies = getTreeElements(VhdlPackageBodyImpl.class, element);
        children.addAll(entities);
        children.addAll(architectures);
        children.addAll(packages);
        children.addAll(packageBodies);

        return children.toArray(new TreeElement[0]);
    }

    @NotNull
    private static TreeElement[] getChildren(VhdlEntityDeclaration element) {
        List<TreeElement> children = new ArrayList<>(10);

        VhdlEntityHeader header = element.getEntityHeader();
        if (header != null) {
            Collection<TreeElement> generics = getGenericTreeElements(header.getGenericClause());
            Collection<TreeElement> ports = getPortTreeElements(header.getPortClause());
            children.addAll(generics);
            children.addAll(ports);
        }

        return children.toArray(new TreeElement[0]);
    }

    @NotNull
    private static TreeElement[] getChildren(VhdlArchitectureBody element) {
        List<TreeElement> children = new ArrayList<>(10);

        VhdlArchitectureDeclarativePart declarations = element.getArchitectureDeclarativePart();
        if (declarations != null) {
            Collection<TreeElement> constants = getConstantTreeElements(declarations.getConstantDeclarationList());
            Collection<TreeElement> signals = getSignalTreeElements(declarations.getSignalDeclarationList());
            Collection<TreeElement> subprograms = getSubprogramSpecificationTreeElements(declarations.getSubprogramDeclarationList(), declarations.getSubprogramBodyList());
            Collection<TreeElement> components = getTreeElements(declarations.getComponentDeclarationList());
            children.addAll(constants);
            children.addAll(signals);
            children.addAll(subprograms);
            children.addAll(components);
        }

        VhdlArchitectureStatementPart statements = element.getArchitectureStatementPart();
        if (statements != null) {
            Collection<TreeElement> instantiations = getTreeElements(statements.getComponentInstantiationStatementList());
            Collection<TreeElement> processes = getTreeElements(statements.getProcessStatementList());
            Collection<TreeElement> blocks = getTreeElements(statements.getBlockStatementList());
            Collection<TreeElement> generates = getTreeElements(statements.getGenerateStatementList());
            children.addAll(instantiations);
            children.addAll(processes);
            children.addAll(blocks);
            children.addAll(generates);
        }

        return children.toArray(new TreeElement[0]);
    }

    @NotNull
    private static TreeElement[] getChildren(VhdlPackageDeclaration element) {
        List<TreeElement> children = new ArrayList<>(10);

        VhdlPackageDeclarativePart declarations = element.getPackageDeclarativePart();
        Collection<TreeElement> constants = getConstantTreeElements(declarations.getConstantDeclarationList());
        Collection<TreeElement> signals = getSignalTreeElements(declarations.getSignalDeclarationList());
        Collection<TreeElement> subprograms = getSubprogramSpecificationTreeElements(declarations.getSubprogramDeclarationList(), Collections.emptyList());
        Collection<TreeElement> components = getTreeElements(declarations.getComponentDeclarationList());
        children.addAll(constants);
        children.addAll(signals);
        children.addAll(subprograms);
        children.addAll(components);

        return children.toArray(new TreeElement[0]);
    }

    @NotNull
    private static TreeElement[] getChildren(VhdlPackageBody element) {
        List<TreeElement> children = new ArrayList<>(10);

        VhdlPackageBodyDeclarativePart declarations = element.getPackageBodyDeclarativePart();
        Collection<TreeElement> constants = getConstantTreeElements(declarations.getConstantDeclarationList());
        Collection<TreeElement> subprograms = getSubprogramSpecificationTreeElements(declarations.getSubprogramDeclarationList(), declarations.getSubprogramBodyList());
        children.addAll(constants);
        children.addAll(subprograms);

        return children.toArray(new TreeElement[0]);
    }

    @NotNull
    private static TreeElement[] getChildren(VhdlBlockStatement element) {
        List<TreeElement> children = new ArrayList<>(10);

        VhdlBlockHeader header = element.getBlockHeader();
        Collection<TreeElement> generics = getGenericTreeElements(header.getGenericClause());
        Collection<TreeElement> ports = getPortTreeElements(header.getPortClause());
        children.addAll(generics);
        children.addAll(ports);

        VhdlBlockDeclarativePart declarations = element.getBlockDeclarativePart();
        Collection<TreeElement> constants = getConstantTreeElements(declarations.getConstantDeclarationList());
        Collection<TreeElement> signals = getSignalTreeElements(declarations.getSignalDeclarationList());
        Collection<TreeElement> subprograms = getSubprogramSpecificationTreeElements(declarations.getSubprogramDeclarationList(), declarations.getSubprogramBodyList());
        Collection<TreeElement> components = getTreeElements(declarations.getComponentDeclarationList());
        children.addAll(constants);
        children.addAll(signals);
        children.addAll(subprograms);
        children.addAll(components);

        VhdlBlockStatementPart statements = element.getBlockStatementPart();
        Collection<TreeElement> instantiations = getTreeElements(statements.getComponentInstantiationStatementList());
        Collection<TreeElement> processes = getTreeElements(statements.getProcessStatementList());
        Collection<TreeElement> blocks = getTreeElements(statements.getBlockStatementList());
        Collection<TreeElement> generates = getTreeElements(statements.getGenerateStatementList());
        children.addAll(instantiations);
        children.addAll(processes);
        children.addAll(blocks);
        children.addAll(generates);

        return children.toArray(new TreeElement[0]);
    }

    @NotNull
    private static TreeElement[] getChildren(VhdlGenerateStatement element) {
        List<TreeElement> children = new ArrayList<>(5);

        VhdlGenerateDeclarativePart declarations = element.getGenerateDeclarativePart();
        if (declarations != null) {
            Collection<TreeElement> constants = getConstantTreeElements(declarations.getConstantDeclarationList());
            Collection<TreeElement> signals = getSignalTreeElements(declarations.getSignalDeclarationList());
            Collection<TreeElement> subprograms = getSubprogramSpecificationTreeElements(declarations.getSubprogramDeclarationList(), declarations.getSubprogramBodyList());
            Collection<TreeElement> components = getTreeElements(declarations.getComponentDeclarationList());
            children.addAll(constants);
            children.addAll(signals);
            children.addAll(subprograms);
            children.addAll(components);
        }

        VhdlGenerateStatementPart statements = element.getGenerateStatementPart();
        Collection<TreeElement> instantiations = getTreeElements(statements.getComponentInstantiationStatementList());
        Collection<TreeElement> processes = getTreeElements(statements.getProcessStatementList());
        Collection<TreeElement> blocks = getTreeElements(statements.getBlockStatementList());
        Collection<TreeElement> generates = getTreeElements(statements.getGenerateStatementList());
        children.addAll(instantiations);
        children.addAll(processes);
        children.addAll(blocks);
        children.addAll(generates);

        return children.toArray(new TreeElement[0]);
    }

    @NotNull
    private static TreeElement[] getChildren(VhdlProcessStatement element) {
        List<TreeElement> children = new ArrayList<>(10);

        VhdlProcessDeclarativePart declarations = element.getProcessDeclarativePart();
        if (declarations != null) {
            Collection<TreeElement> constants = getConstantTreeElements(declarations.getConstantDeclarationList());
            Collection<TreeElement> variables = getVariableTreeElements(declarations.getVariableDeclarationList());
            Collection<TreeElement> subprograms = getSubprogramSpecificationTreeElements(declarations.getSubprogramDeclarationList(), declarations.getSubprogramBodyList());
            children.addAll(constants);
            children.addAll(variables);
            children.addAll(subprograms);
        }

        return children.toArray(new TreeElement[0]);
    }

    @NotNull
    private static TreeElement[] getChildren(VhdlSubprogramBody element) {
        List<TreeElement> children = new ArrayList<>(3);

        VhdlSubprogramDeclarativePart declarations = element.getSubprogramDeclarativePart();
        if (declarations != null) {
            Collection<TreeElement> constants = getConstantTreeElements(declarations.getConstantDeclarationList());
            Collection<TreeElement> variables = getVariableTreeElements(declarations.getVariableDeclarationList());
            Collection<TreeElement> subprograms = getSubprogramSpecificationTreeElements(declarations.getSubprogramDeclarationList(), declarations.getSubprogramBodyList());
            children.addAll(constants);
            children.addAll(variables);
            children.addAll(subprograms);
        }

        return children.toArray(new TreeElement[0]);
    }

    @NotNull
    private static TreeElement[] getChildren(VhdlComponentDeclaration element) {
        List<TreeElement> children = new ArrayList<>(10);

        VhdlEntityHeader entityHeader = element.getEntityHeader();
        Collection<TreeElement> generics = getGenericTreeElements(entityHeader.getGenericClause());
        Collection<TreeElement> ports = getPortTreeElements(entityHeader.getPortClause());
        children.addAll(generics);
        children.addAll(ports);

        return children.toArray(new TreeElement[0]);
    }

    @NotNull
    private static Collection<TreeElement> getGenericTreeElements(VhdlGenericClause clause) {
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

    @NotNull
    private static Collection<TreeElement> getPortTreeElements(VhdlPortClause clause) {
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

    @NotNull
    private static Collection<TreeElement> getConstantTreeElements(List<VhdlConstantDeclaration> declarations) {
        List<TreeElement> children = new ArrayList<>(declarations.size());
        for (VhdlConstantDeclaration declaration : declarations) {
            for (int i = 0; i < declaration.getIdentifierList().getIdentifierList().size(); i++) {
                children.add(new VhdlStructureViewElement((NavigatablePsiElement) declaration, i));
            }
        }
        return children;
    }

    @NotNull
    private static Collection<TreeElement> getSignalTreeElements(List<VhdlSignalDeclaration> declarations) {
        List<TreeElement> children = new ArrayList<>(declarations.size());
        for (VhdlSignalDeclaration declaration : declarations) {
            for (int i = 0; i < declaration.getIdentifierList().getIdentifierList().size(); i++) {
                children.add(new VhdlStructureViewElement((NavigatablePsiElement) declaration, i));
            }
        }
        return children;
    }

    @NotNull
    private static Collection<TreeElement> getVariableTreeElements(List<VhdlVariableDeclaration> declarations) {
        List<TreeElement> children = new ArrayList<>(declarations.size());
        for (VhdlVariableDeclaration declaration : declarations) {
            for (int i = 0; i < declaration.getIdentifierList().getIdentifierList().size(); i++) {
                children.add(new VhdlStructureViewElement((NavigatablePsiElement) declaration, i));
            }
        }
        return children;
    }

    @NotNull
    private static Collection<TreeElement> getSubprogramSpecificationTreeElements(
            @NotNull List<VhdlSubprogramDeclaration> subprogramDeclarations,
            @NotNull List<VhdlSubprogramBody> subprogramBodies
    ) {
        List<TreeElement> children = new ArrayList<>(subprogramBodies.size() + subprogramDeclarations.size());
        for (VhdlSubprogramBody subprogram : subprogramBodies) {
            children.add(new VhdlStructureViewElement((NavigatablePsiElement) subprogram.getSubprogramSpecification()));
        }
        for (VhdlSubprogramDeclaration subprogram : subprogramDeclarations) {
            children.add(new VhdlStructureViewElement((NavigatablePsiElement) subprogram.getSubprogramSpecification()));
        }
        return children;
    }

    @NotNull
    private static <T> Collection<TreeElement> getTreeElements(List<T> ts) {
        List<TreeElement> children = new ArrayList<>(ts.size());
        for (T t : ts) {
            children.add(new VhdlStructureViewElement((NavigatablePsiElement) t));
        }
        return children;
    }

    @NotNull
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
