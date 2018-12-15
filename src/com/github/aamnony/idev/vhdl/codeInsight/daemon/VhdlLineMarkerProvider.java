package com.github.aamnony.idev.vhdl.codeInsight.daemon;


import com.github.aamnony.idev.vhdl.VhdlIcons;
import com.github.aamnony.idev.vhdl.VhdlIcons;
import com.github.aamnony.idev.vhdl.psi.VhdlArchitectureBody;
import com.github.aamnony.idev.vhdl.psi.VhdlEntityDeclaration;
import com.github.aamnony.idev.vhdl.psi.VhdlPackageBody;
import com.github.aamnony.idev.vhdl.psi.VhdlPackageDeclaration;
import com.github.aamnony.idev.vhdl.psi.VhdlSubprogramBody;
import com.github.aamnony.idev.vhdl.psi.VhdlSubprogramDeclaration;
import com.github.aamnony.idev.vhdl.psi.tree.VhdlPsiTreeUtil;
import com.intellij.codeInsight.daemon.RelatedItemLineMarkerInfo;
import com.intellij.codeInsight.daemon.RelatedItemLineMarkerProvider;
import com.intellij.codeInsight.navigation.NavigationGutterIconBuilder;
import com.intellij.psi.PsiElement;

import org.jetbrains.annotations.NotNull;

import java.util.Collection;
import java.util.List;

public class VhdlLineMarkerProvider extends RelatedItemLineMarkerProvider {
    @Override
    protected void collectNavigationMarkers(@NotNull PsiElement element,
                                            @NotNull Collection<? super RelatedItemLineMarkerInfo> result) {
        try {
            if (element instanceof VhdlEntityDeclaration) {
                collectNavigationMarkers((VhdlEntityDeclaration) element, result);
            } else if (element instanceof VhdlArchitectureBody) {
                collectNavigationMarkers((VhdlArchitectureBody) element, result);
            } else if (element instanceof VhdlPackageDeclaration) {
                collectNavigationMarkers((VhdlPackageDeclaration) element, result);
            } else if (element instanceof VhdlPackageBody) {
                collectNavigationMarkers((VhdlPackageBody) element, result);
            } else if (element instanceof VhdlSubprogramDeclaration) {
                collectNavigationMarkers((VhdlSubprogramDeclaration) element, result);
            } else if (element instanceof VhdlSubprogramBody) {
                collectNavigationMarkers((VhdlSubprogramBody) element, result);
            }
        } catch (UnsupportedOperationException e) {
            // TODO: Action not yet supported, ignore for now.
        }
    }

    private static void collectNavigationMarkers(@NotNull VhdlEntityDeclaration element,
                                                 @NotNull Collection<? super RelatedItemLineMarkerInfo> result) {
        List<VhdlArchitectureBody> architectures = VhdlPsiTreeUtil.getArchitectures(element);
        for (VhdlArchitectureBody architecture : architectures) {
            NavigationGutterIconBuilder<PsiElement> builder = NavigationGutterIconBuilder.create(VhdlIcons.IMPLEMENTED_ENTITY)
                    .setTargets(architecture.getIdentifierList().get(0))
                    .setTooltipText("Navigate to architecture");
            result.add(builder.createLineMarkerInfo(element));
        }
    }

    private static void collectNavigationMarkers(@NotNull VhdlArchitectureBody element,
                                                 @NotNull Collection<? super RelatedItemLineMarkerInfo> result) {
        VhdlEntityDeclaration entity = VhdlPsiTreeUtil.getEntity(element);
        NavigationGutterIconBuilder<PsiElement> builder = NavigationGutterIconBuilder.create(VhdlIcons.IMPLEMENTING_ENTITY)
                .setTargets(entity.getIdentifierList().get(0))
                .setTooltipText("Navigate to entity");
        result.add(builder.createLineMarkerInfo(element));
    }

    private static void collectNavigationMarkers(@NotNull VhdlPackageDeclaration element,
                                                 @NotNull Collection<? super RelatedItemLineMarkerInfo> result) {
        List<VhdlPackageBody> packageBodies = VhdlPsiTreeUtil.getPackageBodies(element);
        for (VhdlPackageBody packageBody : packageBodies) {
            NavigationGutterIconBuilder<PsiElement> builder = NavigationGutterIconBuilder.create(VhdlIcons.IMPLEMENTED_PACKAGE)
                    .setTargets(packageBody.getIdentifierList().get(0))
                    .setTooltipText("Navigate to package body");
            result.add(builder.createLineMarkerInfo(element));
        }
    }

    private static void collectNavigationMarkers(@NotNull VhdlPackageBody element,
                                                 @NotNull Collection<? super RelatedItemLineMarkerInfo> result) {
        VhdlPackageDeclaration pkg = VhdlPsiTreeUtil.getPackage(element);
        NavigationGutterIconBuilder<PsiElement> builder = NavigationGutterIconBuilder.create(VhdlIcons.IMPLEMENTING_PACKAGE)
                .setTargets(pkg.getIdentifierList().get(0))
                .setTooltipText("Navigate to package declaration");
        result.add(builder.createLineMarkerInfo(element));

    }

    private static void collectNavigationMarkers(@NotNull VhdlSubprogramDeclaration element,
                                                 @NotNull Collection<? super RelatedItemLineMarkerInfo> result) {
        List<VhdlSubprogramBody> subprogramBodies = VhdlPsiTreeUtil.getSubprogramBodies(element);
        for (VhdlSubprogramBody subprogramBody : subprogramBodies) {
            NavigationGutterIconBuilder<PsiElement> builder = NavigationGutterIconBuilder.create(VhdlIcons.IMPLEMENTED_SUBPROGRAM)
                    .setTargets(subprogramBody.getSubprogramSpecification().getDesignator())
                    .setTooltipText("Navigate to subprogram body");
            result.add(builder.createLineMarkerInfo(element));
        }
    }

    private static void collectNavigationMarkers(@NotNull VhdlSubprogramBody element,
                                                 @NotNull Collection<? super RelatedItemLineMarkerInfo> result) {
        PsiElement subprogram = VhdlPsiTreeUtil.getSubprogram(element);
        if (subprogram != null) {
            NavigationGutterIconBuilder<PsiElement> builder = NavigationGutterIconBuilder.create(VhdlIcons.IMPLEMENTING_SUBPROGRAM)
                    .setTargets(subprogram)
                    .setTooltipText("Navigate to subprogram declaration");
            result.add(builder.createLineMarkerInfo(element));
        }
    }
}

