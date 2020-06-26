package com.github.aamnony.idev.vhdl.project;

import com.intellij.ide.highlighter.ArchiveFileType;
import com.intellij.ide.plugins.IdeaPluginDescriptor;
import com.intellij.ide.plugins.PluginManager;
import com.intellij.openapi.diff.impl.patch.formove.PathMerger;
import com.intellij.openapi.extensions.PluginId;
import com.intellij.openapi.fileTypes.FileType;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.roots.AdditionalLibraryRootsProvider;
import com.intellij.openapi.roots.SyntheticLibrary;
import com.intellij.openapi.vfs.JarFileSystem;
import com.intellij.openapi.vfs.LocalFileSystem;
import com.intellij.openapi.vfs.VirtualFile;

import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Objects;

public class VhdlLibraryRootsProvider extends AdditionalLibraryRootsProvider {

    @NotNull
    @Override
    public Collection<SyntheticLibrary> getAdditionalProjectLibraries(@NotNull Project project) {
        IdeaPluginDescriptor plugin = PluginManager.getPlugin(PluginId.getId("com.github.aamnony.idev"));
        if (plugin == null) {
            throw new NullPointerException("Plugin 'com.github.aamnony.idev' not found");
        }

        File pluginPath = plugin.getPath();
        VirtualFile pluginRoot = LocalFileSystem.getInstance().findFileByIoFile(pluginPath);
        VirtualFile vhdlLibsPath;
        FileType pluginRootFileType = Objects.requireNonNull(pluginRoot).getFileType();
        
        if (pluginRootFileType instanceof ArchiveFileType) {
            pluginRoot = JarFileSystem.getInstance().getJarRootForLocalFile(pluginRoot);
            vhdlLibsPath = PathMerger.getFile(pluginRoot, "lib/vhdl");
        } else if (pluginRoot.isDirectory()) {
            vhdlLibsPath = PathMerger.getFile(pluginRoot, "classes/lib/vhdl");
        } else {
            throw new UnsupportedOperationException("Root path of the plugin is of type: '" + pluginRootFileType + "' and is unsupported");
        }

        if (vhdlLibsPath == null) {
            throw new NullPointerException("Can't find VHDL libraries in plugin directory. " + pluginRoot);
        }

        VirtualFile[] vhdlLibs = vhdlLibsPath.getChildren();
        List<SyntheticLibrary> libraries = new ArrayList<>(vhdlLibs.length);
        for (VirtualFile vhdlLib : vhdlLibs) {
            libraries.add(new VhdlSyntheticLibrary(vhdlLib.getName(), Arrays.asList(vhdlLib.getChildren())));
        }

        return libraries;
    }
}
