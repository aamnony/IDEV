package com.github.aamnony.idev.vhdl.lang;

import com.intellij.openapi.project.Project;
import com.intellij.psi.PsiFileFactory;
import com.intellij.psi.codeStyle.CodeStyleManager;
import com.intellij.psi.impl.source.tree.Factory;
import com.intellij.psi.impl.source.tree.LeafElement;

import java.util.List;
import java.util.Objects;

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

    public static String[] createInstantiation(Project project, VhdlEntityDeclaration entity, boolean useExplicitInstantiation) {
        // First, create the raw, non-formatted instantiation
        String entityName = entity.getIdentifierList().get(0).getName();

        VhdlEntityHeader entityHeader = entity.getEntityHeader();


        StringBuilder sb = new StringBuilder();
        sb.append(entityName).append("_inst").append(':').append(useExplicitInstantiation ? "entity work." : "").append(entityName).append('\n');

        VhdlGenericClause genericClause = Objects.requireNonNull(entityHeader).getGenericClause();
        if (genericClause != null) {
            List<VhdlInterfaceGenericDeclaration> genericDeclarations = genericClause.getGenericInterfaceList().getInterfaceGenericDeclarationList();
            int genericCount = getGenericCount(genericDeclarations);
            int currentGeneric = 0;
            if (genericCount > 0) {
                sb.append("generic map(\n");
                for (VhdlInterfaceGenericDeclaration genericDeclaration : genericDeclarations) {
                    for (VhdlIdentifier generic : genericDeclaration.getIdentifierList().getIdentifierList()) {
                        String genericName = generic.getName();
                        sb.append(genericName).append("=>").append(genericName).append(currentGeneric < genericCount - 1 ? ",\n" : '\n');
                        currentGeneric++;
                    }
                }
                sb.append(")\n");
            }
        }
        VhdlPortClause portClause = entityHeader.getPortClause();
        if (portClause != null) {
            List<VhdlInterfacePortDeclaration> portDeclarations = portClause.getPortInterfaceList().getInterfacePortDeclarationList();
            int portCount = getPortCount(portDeclarations);
            int currentPort = 0;
            if (portCount > 0) {
                sb.append("port map(\n");
                for (VhdlInterfacePortDeclaration portDeclaration : portDeclarations) {
                    for (VhdlIdentifier port : portDeclaration.getIdentifierList().getIdentifierList()) {
                        String portName = port.getName();
                        sb.append(portName).append("=>").append(portName).append(currentPort < portCount - 1 ? ",\n" : '\n');
                        currentPort++;
                    }
                }
                sb.append(");\n");
            }
        }
        // Second, wrap the raw instantiation with an empty architecture, so we can format it to match the project code syle
        sb.insert(0, "architecture arch of temp is\nbegin\n").append("\nend;");
        CodeStyleManager codeStyleManager = CodeStyleManager.getInstance(project);
        VhdlFile tempVhdlFile = (VhdlFile) codeStyleManager.reformat(VhdlElementFactory.createFile(project, sb.toString()));

        // Third, unwrap the formatted instantiation and return the it
        VhdlArchitectureBody tempArch = (VhdlArchitectureBody) tempVhdlFile.getLastChild();
        VhdlComponentInstantiationStatement inst = Objects.requireNonNull(tempArch.getArchitectureStatementPart()).getComponentInstantiationStatementList().get(0);

        if (useExplicitInstantiation) {
            return new String[]{inst.getText()};
        } else {
            // Implicit instantiation requires a component declaration, lets create one and return it too.
            String component = entity.getText().replace("entity", "component");
            return new String[]{inst.getText(), component};
        }
    }

    private static int getGenericCount(List<VhdlInterfaceGenericDeclaration> genericDeclarations) {
        int count = 0;
        for (VhdlInterfaceGenericDeclaration genericDeclaration : genericDeclarations) {
            count += genericDeclaration.getIdentifierList().getIdentifierList().size();
        }
        return count;
    }

    private static int getPortCount(List<VhdlInterfacePortDeclaration> portDeclarations) {
        int count = 0;
        for (VhdlInterfacePortDeclaration portDeclaration : portDeclarations) {
            count += portDeclaration.getIdentifierList().getIdentifierList().size();
        }
        return count;
    }
}
