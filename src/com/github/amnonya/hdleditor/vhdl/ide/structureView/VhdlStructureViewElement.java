package com.github.amnonya.hdleditor.vhdl.ide.structureView;

import com.github.amnonya.hdleditor.vhdl.psi.VhdlArchitectureBody;
import com.github.amnonya.hdleditor.vhdl.psi.VhdlArchitectureDeclarativePart;
import com.github.amnonya.hdleditor.vhdl.psi.VhdlArchitectureStatementPart;
import com.github.amnonya.hdleditor.vhdl.psi.VhdlBlockDeclarativePart;
import com.github.amnonya.hdleditor.vhdl.psi.VhdlBlockHeader;
import com.github.amnonya.hdleditor.vhdl.psi.VhdlBlockStatement;
import com.github.amnonya.hdleditor.vhdl.psi.VhdlBlockStatementPart;
import com.github.amnonya.hdleditor.vhdl.psi.VhdlComponentDeclaration;
import com.github.amnonya.hdleditor.vhdl.psi.VhdlConstantDeclaration;
import com.github.amnonya.hdleditor.vhdl.psi.VhdlEntityDeclaration;
import com.github.amnonya.hdleditor.vhdl.psi.VhdlEntityHeader;
import com.github.amnonya.hdleditor.vhdl.psi.VhdlFile;
import com.github.amnonya.hdleditor.vhdl.psi.VhdlGenerateStatement;
import com.github.amnonya.hdleditor.vhdl.psi.VhdlGenericClause;
import com.github.amnonya.hdleditor.vhdl.psi.VhdlInterfaceGenericDeclaration;
import com.github.amnonya.hdleditor.vhdl.psi.VhdlInterfacePortDeclaration;
import com.github.amnonya.hdleditor.vhdl.psi.VhdlPackageBody;
import com.github.amnonya.hdleditor.vhdl.psi.VhdlPackageBodyDeclarativePart;
import com.github.amnonya.hdleditor.vhdl.psi.VhdlPackageDeclaration;
import com.github.amnonya.hdleditor.vhdl.psi.VhdlPackageDeclarativePart;
import com.github.amnonya.hdleditor.vhdl.psi.VhdlPortClause;
import com.github.amnonya.hdleditor.vhdl.psi.VhdlProcessDeclarativePart;
import com.github.amnonya.hdleditor.vhdl.psi.VhdlProcessStatement;
import com.github.amnonya.hdleditor.vhdl.psi.VhdlSignalDeclaration;
import com.github.amnonya.hdleditor.vhdl.psi.VhdlSubprogramBody;
import com.github.amnonya.hdleditor.vhdl.psi.VhdlSubprogramDeclaration;
import com.github.amnonya.hdleditor.vhdl.psi.VhdlSubprogramDeclarativePart;
import com.github.amnonya.hdleditor.vhdl.psi.VhdlSubprogramSpecification;
import com.github.amnonya.hdleditor.vhdl.psi.VhdlVariableDeclaration;
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
        Collection<TreeElement> entities = getTreeElements(VhdlEntityDeclarationImpl.class, element);
        Collection<TreeElement> architectures = getTreeElements(VhdlArchitectureBodyImpl.class, element);
        Collection<TreeElement> packages = getTreeElements(VhdlPackageDeclarationImpl.class, element);
        Collection<TreeElement> packageBodies = getTreeElements(VhdlPackageBodyImpl.class, element);
        List<TreeElement> children = new ArrayList<>(2);
        children.addAll(entities);
        children.addAll(architectures);
        children.addAll(packages);
        children.addAll(packageBodies);
        return children.toArray(new TreeElement[0]);
    }

    @NotNull
    private static TreeElement[] getChildren(VhdlEntityDeclaration element) {
        VhdlEntityHeader entityHeader = element.getEntityHeader();
        Collection<TreeElement> generics = getGenericTreeElements(entityHeader.getGenericClause());
        Collection<TreeElement> ports = getPortTreeElements(entityHeader.getPortClause());
        List<TreeElement> children = new ArrayList<>(10);
        children.addAll(generics);
        children.addAll(ports);
        return children.toArray(new TreeElement[0]);
    }

    @NotNull
    private static TreeElement[] getChildren(VhdlArchitectureBody element) {
        VhdlArchitectureDeclarativePart declarations = element.getArchitectureDeclarativePart();
        Collection<TreeElement> constants = getConstantTreeElements(declarations.getConstantDeclarationList());
        Collection<TreeElement> signals = getSignalTreeElements(declarations.getSignalDeclarationList());
        Collection<TreeElement> subprograms = getSubprogramSpecificationTreeElements(
                declarations.getSubprogramDeclarationList(), declarations.getSubprogramBodyList()
        );
        Collection<TreeElement> components = getTreeElements(declarations.getComponentDeclarationList());

        VhdlArchitectureStatementPart statements = element.getArchitectureStatementPart();
        Collection<TreeElement> instantiations = getTreeElements(statements.getComponentInstantiationStatementList());
        Collection<TreeElement> processes = getTreeElements(statements.getProcessStatementList());
        Collection<TreeElement> blocks = getTreeElements(statements.getBlockStatementList());
        Collection<TreeElement> generates = getTreeElements(statements.getGenerateStatementList());

        List<TreeElement> children = new ArrayList<>(10);
        children.addAll(constants);
        children.addAll(signals);
        children.addAll(subprograms);
        children.addAll(components);
        children.addAll(instantiations);
        children.addAll(processes);
        children.addAll(blocks);
        children.addAll(generates);
        return children.toArray(new TreeElement[0]);
    }

    @NotNull
    private static TreeElement[] getChildren(VhdlPackageDeclaration element) {
        VhdlPackageDeclarativePart declarations = element.getPackageDeclarativePart();
        Collection<TreeElement> constants = getConstantTreeElements(declarations.getConstantDeclarationList());
        Collection<TreeElement> signals = getSignalTreeElements(declarations.getSignalDeclarationList());
        Collection<TreeElement> subprograms = getSubprogramSpecificationTreeElements(
                declarations.getSubprogramDeclarationList(), Collections.emptyList()
        );
        Collection<TreeElement> components = getTreeElements(declarations.getComponentDeclarationList());
        List<TreeElement> children = new ArrayList<>(10);
        children.addAll(constants);
        children.addAll(signals);
        children.addAll(subprograms);
        children.addAll(components);
        return children.toArray(new TreeElement[0]);
    }

    @NotNull
    private static TreeElement[] getChildren(VhdlPackageBody element) {
        VhdlPackageBodyDeclarativePart declarations = element.getPackageBodyDeclarativePart();
        Collection<TreeElement> constants = getConstantTreeElements(declarations.getConstantDeclarationList());
        Collection<TreeElement> subprograms = getSubprogramSpecificationTreeElements(
                declarations.getSubprogramDeclarationList(), declarations.getSubprogramBodyList()
        );
        List<TreeElement> children = new ArrayList<>(10);
        children.addAll(constants);
        children.addAll(subprograms);
        return children.toArray(new TreeElement[0]);
    }

    @NotNull
    private static TreeElement[] getChildren(VhdlBlockStatement element) {
        VhdlBlockHeader blockHeader = element.getBlockHeader();
        Collection<TreeElement> generics = getGenericTreeElements(blockHeader.getGenericClause());
        Collection<TreeElement> ports = getPortTreeElements(blockHeader.getPortClause());

        VhdlBlockDeclarativePart declarations = element.getBlockDeclarativePart();
        Collection<TreeElement> constants = getConstantTreeElements(declarations.getConstantDeclarationList());
        Collection<TreeElement> signals = getSignalTreeElements(declarations.getSignalDeclarationList());
        Collection<TreeElement> subprograms = getSubprogramSpecificationTreeElements(
                declarations.getSubprogramDeclarationList(), declarations.getSubprogramBodyList()
        );
        Collection<TreeElement> components = getTreeElements(declarations.getComponentDeclarationList());

        VhdlBlockStatementPart statements = element.getBlockStatementPart();
        Collection<TreeElement> instantiations = getTreeElements(statements.getComponentInstantiationStatementList());
        Collection<TreeElement> processes = getTreeElements(statements.getProcessStatementList());
        Collection<TreeElement> blocks = getTreeElements(statements.getBlockStatementList());
        Collection<TreeElement> generates = getTreeElements(statements.getGenerateStatementList());

        List<TreeElement> children = new ArrayList<>(10);
        children.addAll(generics);
        children.addAll(ports);
        children.addAll(constants);
        children.addAll(signals);
        children.addAll(subprograms);
        children.addAll(components);
        children.addAll(instantiations);
        children.addAll(processes);
        children.addAll(blocks);
        children.addAll(generates);
        return children.toArray(new TreeElement[0]);
    }

    @NotNull
    private static TreeElement[] getChildren(VhdlGenerateStatement element) {
        Collection<TreeElement> constants = getConstantTreeElements(element.getConstantDeclarationList());
        Collection<TreeElement> signals = getSignalTreeElements(element.getSignalDeclarationList());
        Collection<TreeElement> subprograms = getSubprogramSpecificationTreeElements(
                element.getSubprogramDeclarationList(), element.getSubprogramBodyList()
        );
        Collection<TreeElement> components = getTreeElements(element.getComponentDeclarationList());

        Collection<TreeElement> instantiations = getTreeElements(element.getComponentInstantiationStatementList());
        Collection<TreeElement> processes = getTreeElements(element.getProcessStatementList());
        Collection<TreeElement> blocks = getTreeElements(element.getBlockStatementList());
        Collection<TreeElement> generates = getTreeElements(element.getGenerateStatementList());

        List<TreeElement> children = new ArrayList<>(5);
        children.addAll(constants);
        children.addAll(signals);
        children.addAll(subprograms);
        children.addAll(components);
        children.addAll(instantiations);
        children.addAll(processes);
        children.addAll(blocks);
        children.addAll(generates);
        return children.toArray(new TreeElement[0]);
    }

    @NotNull
    private static TreeElement[] getChildren(VhdlProcessStatement element) {
        VhdlProcessDeclarativePart declarations = element.getProcessDeclarativePart();
        if (declarations != null) {
            Collection<TreeElement> constants = getConstantTreeElements(declarations.getConstantDeclarationList());
            Collection<TreeElement> variables = getVariableTreeElements(declarations.getVariableDeclarationList());
            Collection<TreeElement> subprograms = getSubprogramSpecificationTreeElements(
                    declarations.getSubprogramDeclarationList(), declarations.getSubprogramBodyList()
            );
            List<TreeElement> children = new ArrayList<>(10);
            children.addAll(constants);
            children.addAll(variables);
            children.addAll(subprograms);
            return children.toArray(new TreeElement[0]);
        } else {
            return EMPTY_ARRAY;
        }
    }

    @NotNull
    private static TreeElement[] getChildren(VhdlSubprogramBody element) {
        VhdlSubprogramDeclarativePart declarations = element.getSubprogramDeclarativePart();
        if (declarations != null) {
            Collection<TreeElement> constants = getConstantTreeElements(declarations.getConstantDeclarationList());
            Collection<TreeElement> variables = getVariableTreeElements(declarations.getVariableDeclarationList());
            Collection<TreeElement> subprograms = getSubprogramSpecificationTreeElements(
                    declarations.getSubprogramDeclarationList(), declarations.getSubprogramBodyList()
            );
            List<TreeElement> children = new ArrayList<>(3);
            children.addAll(constants);
            children.addAll(variables);
            children.addAll(subprograms);
            return children.toArray(new TreeElement[0]);
        } else {
            return EMPTY_ARRAY;
        }
    }

    @NotNull
    private static TreeElement[] getChildren(VhdlComponentDeclaration element) {
        VhdlEntityHeader entityHeader = element.getEntityHeader();
        Collection<TreeElement> generics = getGenericTreeElements(entityHeader.getGenericClause());
        Collection<TreeElement> ports = getPortTreeElements(entityHeader.getPortClause());
        List<TreeElement> children = new ArrayList<>(10);
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
