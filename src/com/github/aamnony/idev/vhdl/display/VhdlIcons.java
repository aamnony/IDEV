package com.github.aamnony.idev.vhdl.display;

import com.intellij.openapi.application.ApplicationInfo;
import com.intellij.openapi.util.IconLoader;

import javax.swing.Icon;

public class VhdlIcons {

    /*
     * SVG icons are supported since 2018.2 (i.e. 182), so for prior builds we should use PNG icons.
     */
    private static final String FORMAT = ApplicationInfo.getInstance().getBuild().getComponents()[0] >= 182 ? "svg" : "png";

    public static final Icon FILE = IconLoader.getIcon("/icons/vhd." + FORMAT);

    public static final Icon ENTITY = IconLoader.getIcon("/icons/entity." + FORMAT);
    public static final Icon ARCHITECTURE = IconLoader.getIcon("/icons/architecture." + FORMAT);
    public static final Icon PACKAGE = IconLoader.getIcon("/icons/package." + FORMAT);
    public static final Icon PACKAGE_BODY = IconLoader.getIcon("/icons/packageBody." + FORMAT);
    public static final Icon PROCESS = IconLoader.getIcon("/icons/process." + FORMAT);
    public static final Icon BLOCK = IconLoader.getIcon("/icons/block." + FORMAT);
    public static final Icon GENERATE = IconLoader.getIcon("/icons/generate." + FORMAT);
    public static final Icon SUBPROGRAM = IconLoader.getIcon("/icons/subprogram." + FORMAT);
    public static final Icon SUBPROGRAM_BODY = IconLoader.getIcon("/icons/subprogramBody." + FORMAT);
    public static final Icon COMPONENT = IconLoader.getIcon("/icons/component." + FORMAT);
    public static final Icon INSTANTIATION = IconLoader.getIcon("/icons/instantiation." + FORMAT);

    public static final Icon PORT_IN = IconLoader.getIcon("/icons/portIn." + FORMAT);
    public static final Icon PORT_INOUT = IconLoader.getIcon("/icons/portInOut." + FORMAT);
    public static final Icon PORT_OUT = IconLoader.getIcon("/icons/portOut." + FORMAT);
    public static final Icon PORT_BUFFER = IconLoader.getIcon("/icons/portInOut." + FORMAT);
    public static final Icon GENERIC = IconLoader.getIcon("/icons/generic." + FORMAT);
    public static final Icon CONSTANT = IconLoader.getIcon("/icons/constant." + FORMAT);
    public static final Icon SIGNAL = IconLoader.getIcon("/icons/signal." + FORMAT);
    public static final Icon VARIABLE = IconLoader.getIcon("/icons/variable." + FORMAT);

    public static final Icon IMPLEMENTED_ENTITY = IconLoader.getIcon("/icons/implementedEntity." + FORMAT);
    public static final Icon IMPLEMENTING_ENTITY = IconLoader.getIcon("/icons/implementingEntity." + FORMAT);
    public static final Icon IMPLEMENTED_PACKAGE = IconLoader.getIcon("/icons/implementedPackage." + FORMAT);
    public static final Icon IMPLEMENTING_PACKAGE = IconLoader.getIcon("/icons/implementingPackage." + FORMAT);
    public static final Icon IMPLEMENTED_SUBPROGRAM = IconLoader.getIcon("/icons/implementedSubprogram." + FORMAT);
    public static final Icon IMPLEMENTING_SUBPROGRAM = IconLoader.getIcon("/icons/implementingSubprogram." + FORMAT);
}