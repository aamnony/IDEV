package com.github.aamnony.idev.utils;

import com.intellij.openapi.application.ApplicationManager;
import com.intellij.openapi.module.Module;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.roots.ModuleRootModificationUtil;
import com.intellij.openapi.roots.OrderRootType;
import com.intellij.openapi.roots.libraries.Library;
import com.intellij.openapi.roots.libraries.LibraryTable;
import com.intellij.openapi.roots.libraries.LibraryTablesRegistrar;
import com.intellij.openapi.vfs.LocalFileSystem;
import com.intellij.openapi.vfs.VirtualFile;
import com.intellij.openapi.vfs.VirtualFileManager;

public class ProjectUtils {
    public static void createLibraryDependency(final Module module, Project project, String libraryName, String libraryPath) {
        LibraryTable projectLibraryTable = LibraryTablesRegistrar.getInstance().getLibraryTable(project);
        final LibraryTable.ModifiableModel projectLibraryModel = projectLibraryTable.getModifiableModel();

        final Library library = projectLibraryModel.createLibrary(libraryName);
        final Library.ModifiableModel libraryModel = library.getModifiableModel();
        String pathUrl = VirtualFileManager.constructUrl(LocalFileSystem.PROTOCOL, libraryPath);
        VirtualFile file = VirtualFileManager.getInstance().findFileByUrl(pathUrl);

        if (file != null) {
            libraryModel.addRoot(file, OrderRootType.CLASSES);
            ApplicationManager.getApplication().runWriteAction(() -> {
                libraryModel.commit();
                projectLibraryModel.commit();
                ModuleRootModificationUtil.addDependency(module, library);
            });
        }
    }
}
