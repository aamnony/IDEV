package com.github.aamnony.idev.vhdl.project;


import com.intellij.icons.AllIcons;
import com.intellij.navigation.ItemPresentation;
import com.intellij.openapi.roots.SyntheticLibrary;
import com.intellij.openapi.util.Condition;
import com.intellij.openapi.vfs.VirtualFile;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Collection;
import java.util.Collections;
import java.util.Objects;
import java.util.Set;

import javax.swing.Icon;

public class VhdlSyntheticLibrary extends SyntheticLibrary implements ItemPresentation {

    private final String name;
    private final Collection<VirtualFile> binaryRoots;
    private final Collection<VirtualFile> sourceRoots;
    private final Set<VirtualFile> excludedRoots;
    private final Condition<VirtualFile> excludeCondition;

    public VhdlSyntheticLibrary(@Nullable String name, @NotNull Collection<VirtualFile> sourceRoots) {
        this.name = name;
        this.sourceRoots = Collections.unmodifiableCollection(sourceRoots);
        binaryRoots = Collections.emptySet();
        excludedRoots = Collections.emptySet();
        excludeCondition = null;
    }

    @NotNull
    @Override
    public Collection<VirtualFile> getSourceRoots() {
        return sourceRoots;
    }

    @NotNull
    @Override
    public Collection<VirtualFile> getBinaryRoots() {
        return binaryRoots;
    }

    @NotNull
    @Override
    public Set<VirtualFile> getExcludedRoots() {
        return excludedRoots;
    }

    @Nullable
    @Override
    public Condition<VirtualFile> getExcludeFileCondition() {
        return excludeCondition;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        VhdlSyntheticLibrary library = (VhdlSyntheticLibrary) o;
        if (!sourceRoots.equals(library.getSourceRoots())) return false;
        if (!binaryRoots.equals(library.getBinaryRoots())) return false;
        if (!excludedRoots.equals(library.getExcludedRoots())) return false;
        if (!Objects.equals(excludeCondition, library.getExcludeFileCondition())) return false;
        return true;
    }

    @Override
    public int hashCode() {
        return Objects.hash(sourceRoots, binaryRoots, excludedRoots, excludeCondition);
    }

    @Nullable
    @Override
    public String getPresentableText() {
        return name;
    }

    @Nullable
    @Override
    public String getLocationString() {
        return null;
    }

    @Nullable
    @Override
    public Icon getIcon(boolean unused) {
        return AllIcons.Nodes.PpLibFolder;
    }
}
