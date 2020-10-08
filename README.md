[![GitHub issues](https://img.shields.io/github/issues/aamnony/idev.svg)](https://github.com/aamnony/IDEV/issues/)

[![VHDL project](https://img.shields.io/badge/VHDL-in%20progress-yellow.svg)](https://github.com/aamnony/IDEV/projects/1)
[![Verilog project](https://img.shields.io/badge/Verilog-not%20started-red.svg)](https://en.wikipedia.org/wiki/Verilog)

# IDEV - IDE for VHDL
VHDL IDE, implemented as an IntelliJ plugin.

## Creating a Project
<i>Needed for versions prior to 0.2.0.</i>
### Pycharm
1. Create a new project like a regular Python project
2. Navigate to: File &rarr; Settings &rarr; Project: <i>the-project-name</i> &rarr; Project Structure
3. Click: Add Content Root
4. Navigate to a folder or JAR containing IEEE and Standard VHDL libraries and click OK twice

### Intellij IDEA
1. Create a new Java project, without a template
   1. Feel free to rename (or even delete) the <i>src</i> directory
2. Navigate to: File &rarr; Project Structure &rarr; Project Settings &rarr; Modules &rarr; Sources
3. Click: Add Content Root
4. Navigate to a folder or JAR containing IEEE and Standard VHDL libraries and click OK twice

#
[![Help Wanted](https://img.shields.io/badge/-HELP%20WANTED-blue)](https://github.com/aamnony/IDEV/issues)

# Building
If you are new to JetBrains plugin development, be sure to follow the [prerequisites](https://jetbrains.org/intellij/sdk/docs/tutorials/custom_language_support/prerequisites.html) (no need to create a new project).

After cloning the repository, you need to generate the parser and lexer by:
* Right-click on ``/src/com/github/aamnony/idev/vhdl/lang/VHDL.bnf`` and click on ``Generate Parser Code``
* Right-click on ``/src/com/github/aamnony/idev/vhdl/lang/VHDL.flex`` and click on ``Run JFlex Generator``
