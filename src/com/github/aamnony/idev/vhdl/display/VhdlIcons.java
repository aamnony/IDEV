package com.github.aamnony.idev.vhdl.display;

import com.intellij.openapi.application.ApplicationInfo;
import com.intellij.openapi.util.IconLoader;

import javax.swing.Icon;

public class VhdlIcons {

    /*
     * SVG icons are supported since 2018.2 (i.e. 182), so for prior builds we should use PNG icons.
     */
    private static final String FORMAT = ApplicationInfo.getInstance().getBuild().getComponents()[0] >= 182 ? "svg" : "png";

    public static final Icon FILE = IconLoader.getIcon("/icons/vhdl/vhd." + FORMAT);

    public static final Icon ENTITY = IconLoader.getIcon("/icons/vhdl/entity." + FORMAT);
    public static final Icon ARCHITECTURE = IconLoader.getIcon("/icons/vhdl/architecture." + FORMAT);
    public static final Icon PACKAGE = IconLoader.getIcon("/icons/vhdl/package." + FORMAT);
    public static final Icon PACKAGE_BODY = IconLoader.getIcon("/icons/vhdl/packageBody." + FORMAT);
    public static final Icon PROCESS = IconLoader.getIcon("/icons/vhdl/process." + FORMAT);
    public static final Icon BLOCK = IconLoader.getIcon("/icons/vhdl/block." + FORMAT);
    public static final Icon GENERATE = IconLoader.getIcon("/icons/vhdl/generate." + FORMAT);
    public static final Icon SUBPROGRAM = IconLoader.getIcon("/icons/vhdl/subprogram." + FORMAT);
    public static final Icon SUBPROGRAM_BODY = IconLoader.getIcon("/icons/vhdl/subprogramBody." + FORMAT);
    public static final Icon COMPONENT = IconLoader.getIcon("/icons/vhdl/component." + FORMAT);
    public static final Icon INSTANTIATION = IconLoader.getIcon("/icons/vhdl/instantiation." + FORMAT);

    public static final Icon PORT_IN = IconLoader.getIcon("/icons/vhdl/portIn." + FORMAT);
    public static final Icon PORT_INOUT = IconLoader.getIcon("/icons/vhdl/portInOut." + FORMAT);
    public static final Icon PORT_OUT = IconLoader.getIcon("/icons/vhdl/portOut." + FORMAT);
    public static final Icon PORT_BUFFER = IconLoader.getIcon("/icons/vhdl/portInOut." + FORMAT);
    public static final Icon GENERIC = IconLoader.getIcon("/icons/vhdl/generic." + FORMAT);
    public static final Icon CONSTANT = IconLoader.getIcon("/icons/vhdl/constant." + FORMAT);
    public static final Icon SIGNAL = IconLoader.getIcon("/icons/vhdl/signal." + FORMAT);
    public static final Icon VARIABLE = IconLoader.getIcon("/icons/vhdl/variable." + FORMAT);

    public static final Icon IMPLEMENTED_ENTITY = IconLoader.getIcon("/icons/vhdl/implementedEntity." + FORMAT);
    public static final Icon IMPLEMENTING_ENTITY = IconLoader.getIcon("/icons/vhdl/implementingEntity." + FORMAT);
    public static final Icon IMPLEMENTED_PACKAGE = IconLoader.getIcon("/icons/vhdl/implementedPackage." + FORMAT);
    public static final Icon IMPLEMENTING_PACKAGE = IconLoader.getIcon("/icons/vhdl/implementingPackage." + FORMAT);
    public static final Icon IMPLEMENTED_SUBPROGRAM = IconLoader.getIcon("/icons/vhdl/implementedSubprogram." + FORMAT);
    public static final Icon IMPLEMENTING_SUBPROGRAM = IconLoader.getIcon("/icons/vhdl/implementingSubprogram." + FORMAT);
}