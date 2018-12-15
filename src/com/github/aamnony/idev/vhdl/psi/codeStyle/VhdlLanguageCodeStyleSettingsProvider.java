package com.github.aamnony.idev.vhdl.psi.codeStyle;

import com.github.aamnony.idev.vhdl.lang.VhdlLanguage;
import com.intellij.application.options.IndentOptionsEditor;
import com.intellij.application.options.SmartIndentOptionsEditor;
import com.intellij.lang.Language;
import com.intellij.psi.codeStyle.CodeStyleSettingsCustomizable;
import com.intellij.psi.codeStyle.CommonCodeStyleSettings;
import com.intellij.psi.codeStyle.LanguageCodeStyleSettingsProvider;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class VhdlLanguageCodeStyleSettingsProvider extends LanguageCodeStyleSettingsProvider {
    @NotNull
    @Override
    public Language getLanguage() {
        return VhdlLanguage.INSTANCE;
    }

    @Override
    public void customizeSettings(@NotNull CodeStyleSettingsCustomizable consumer, @NotNull SettingsType settingsType) {
        switch (settingsType) {
            case BLANK_LINES_SETTINGS:
                consumer.showStandardOptions(
                        "KEEP_BLANK_LINES_IN_CODE", "KEEP_BLANK_LINES_IN_DECLARATIONS",
                        "BLANK_LINES_BEFORE_IMPORTS", "BLANK_LINES_AFTER_IMPORTS",
                        "BLANK_LINES_AROUND_CLASS"
                );
                consumer.renameStandardOption("BLANK_LINES_AROUND_CLASS", "After design unit (entity, package, ...)");
                break;
            case SPACING_SETTINGS:
                consumer.showStandardOptions(
                        "SPACE_BEFORE_SEMICOLON",
                        "SPACE_WITHIN_BRACKETS",
                        "SPACE_WITHIN_PARENTHESES",
                        "SPACE_BEFORE_COMMA", "SPACE_AFTER_COMMA",
                        "SPACE_BEFORE_COLON", "SPACE_AFTER_COLON",
                        "SPACE_AROUND_ADDITIVE_OPERATORS", "SPACE_AROUND_ASSIGNMENT_OPERATORS", "SPACE_AROUND_EQUALITY_OPERATORS",
                        "SPACE_AROUND_MULTIPLICATIVE_OPERATORS", "SPACE_AROUND_RELATIONAL_OPERATORS"
                );
                consumer.moveStandardOption("SPACE_BEFORE_COLON", "Other");
                consumer.moveStandardOption("SPACE_AFTER_COLON", "Other");
                consumer.renameStandardOption("SPACE_BEFORE_COLON", "Before colon");
                consumer.renameStandardOption("SPACE_AFTER_COLON", "After colon");
                consumer.renameStandardOption("SPACE_AROUND_ADDITIVE_OPERATORS", "Additive operators (+, -, &)");
                consumer.renameStandardOption("SPACE_AROUND_ASSIGNMENT_OPERATORS", "Assignxment operators (<=, :=, =>)");
                consumer.renameStandardOption("SPACE_AROUND_EQUALITY_OPERATORS", "Equality operators (=, /=, ...)");
                consumer.renameStandardOption("SPACE_AROUND_MULTIPLICATIVE_OPERATORS", "Multiplicative operators (*, /, **)");
                break;
            case WRAPPING_AND_BRACES_SETTINGS:
                consumer.showStandardOptions("ALIGN_GROUP_FIELD_DECLARATIONS", "ALIGN_CONSECUTIVE_VARIABLE_DECLARATIONS");
                consumer.renameStandardOption("ALIGN_GROUP_FIELD_DECLARATIONS", "Align ports & generics in columns");
                consumer.renameStandardOption("ALIGN_CONSECUTIVE_VARIABLE_DECLARATIONS", "Align objects in columns");
                break;
        }
    }

    @Nullable
    @Override
    public IndentOptionsEditor getIndentOptionsEditor() {
        return new SmartIndentOptionsEditor();
    }

    @Nullable
    @Override
    public CommonCodeStyleSettings getDefaultCommonSettings() {
        CommonCodeStyleSettings commonSettings = new CommonCodeStyleSettings(VhdlLanguage.INSTANCE);
        CommonCodeStyleSettings.IndentOptions indentOptions = commonSettings.initIndentOptions();
        commonSettings.BLANK_LINES_BEFORE_IMPORTS = 3;
        commonSettings.BLANK_LINES_AFTER_IMPORTS = 2;
        commonSettings.BLANK_LINES_AROUND_CLASS = 2;
        commonSettings.KEEP_BLANK_LINES_IN_CODE = 1;
        commonSettings.ALIGN_GROUP_FIELD_DECLARATIONS = true;
        commonSettings.ALIGN_CONSECUTIVE_VARIABLE_DECLARATIONS = true;
        return commonSettings;
    }

    @Override
    public String getCodeSample(@NotNull SettingsType settingsType) {
        return "library ieee;\n" +
                "    use ieee.std_logic_1164.all;\n" +
                "    use ieee.numeric_std.all;\n" +
                "\n" +
                "library work;\n" +
                "    use work.example_regbank_pkg.all;\n" +
                "\n" +
                "\n" +
                "-- Register bank which connects to a vAXI block via ERB interface on one side, and to user block(s) via discrete signals on the other side.\n" +
                "entity example_regbank is\n" +
                "    generic(\n" +
                "        RESET_POLARITY_G : std_logic := '1' -- Polarity of RST: '1' - active high, '0' - active low.\n" +
                "    );\n" +
                "    port(\n" +
                "        CLK           : in  std_logic;                                          -- Clock.\n" +
                "        RST           : in  std_logic;                                          -- Asynchronous reset, active high/low depending on RESET_POLARITY_G.\n" +
                "        -- ERB interface:\n" +
                "        ERB_WR        : in  std_logic;                                          -- SW write request, active high.\n" +
                "        ERB_RD        : in  std_logic;                                          -- SW read request, active high.\n" +
                "        ERB_ADDR      : in  std_logic_vector(erb_addr_width_c-1 downto 0);      -- (Word) Address of register to read from/write to.\n" +
                "        ERB_WR_BE     : in  std_logic_vector(erb_be_width_c-1 downto 0);        -- Byte enable for SW write requests, each bit is active high.\n" +
                "        ERB_DATA_OUT  : in  std_logic_vector(erb_data_width_c-1 downto 0);      -- Data from SW to write to a register. Left bits are truncated when register width is smaller than max width.\n" +
                "        ERB_DATA_IN   : out std_logic_vector(erb_data_width_c-1 downto 0);      -- Data from register to send to SW. Left bits are zeros when register width is smaller than max width.\n" +
                "        -- Discrete signals interface:\n" +
                "        HW_VERSION_RO : in  std_logic_vector(width_hw_version_ro_c-1 downto 0); -- Version. [User description]\n" +
                "        STATUS_RO     : in  std_logic_vector(width_status_ro_c-1 downto 0);     -- Status. [User description]\n" +
                "        STATUS_RO_WE  : in  std_logic;                                          -- Write enable for STATUS_RO register, active high.\n" +
                "        RESULT_RO     : in  std_logic_vector(width_result_ro_c-1 downto 0);     -- Result of the calculation. [User description]\n" +
                "        NUMBER1_RW    : out std_logic_vector(width_number1_rw_c-1 downto 0);    -- First number for the calculation. [User description]\n" +
                "        NUMBER2_RW    : out std_logic_vector(width_number2_rw_c-1 downto 0)     -- Second number for the calculation. [User description]\n" +
                "    );\n" +
                "end entity example_regbank;\n" +
                "\n" +
                "\n" +
                "architecture example_regbank_arch of example_regbank is\n" +
                "\n" +
                "    signal reg_hw_version_ro : std_logic_vector(width_hw_version_ro_c-1 downto 0);\n" +
                "    signal reg_result_ro     : std_logic_vector(width_result_ro_c-1 downto 0);\n" +
                "    signal reg_number1_rw    : std_logic_vector(width_number1_rw_c-1 downto 0);\n" +
                "    signal reg_number2_rw    : std_logic_vector(width_number2_rw_c-1 downto 0);\n" +
                "    signal reg_status_ro     : std_logic_vector(width_status_ro_c-1 downto 0);\n" +
                "\n" +
                "    signal erb_addr_int      : integer range 0 to (2**erb_addr_width_c) - 1;\n" +
                "\n" +
                "begin\n" +
                "\n" +
                "    convert_erb_addr_to_number :\n" +
                "        erb_addr_int <= to_integer(unsigned(ERB_ADDR));\n" +
                "\n" +
                "    -- Handles register read requests from SW by sending the value of the register with the correct address.\n" +
                "    -- Drives ERB_DATA_IN output with the value of the register.\n" +
                "    read_proc : process(CLK) is\n" +
                "    begin\n" +
                "        if rising_edge(CLK) then\n" +
                "            if ERB_RD = '1' then\n" +
                "                case erb_addr_int is\n" +
                "                    when addr_hw_version_ro_c =>\n" +
                "                        reg_read(ERB_DATA_IN, reg_hw_version_ro);\n" +
                "                    when addr_result_ro_c =>\n" +
                "                        reg_read(ERB_DATA_IN, reg_result_ro);\n" +
                "                    when addr_number1_rw_c =>\n" +
                "                        reg_read(ERB_DATA_IN, reg_number1_rw);\n" +
                "                    when addr_number2_rw_c =>\n" +
                "                        reg_read(ERB_DATA_IN, reg_number2_rw);\n" +
                "                    when addr_status_ro_c =>\n" +
                "                        reg_read(ERB_DATA_IN, reg_status_ro);\n" +
                "\n" +
                "                    when others =>\n" +
                "                        report example_regbank_arch'instance_name & \" Invalid read address: \" & integer'image(erb_addr_int)\n" +
                "                            severity error;\n" +
                "                end case;\n" +
                "            end if;\n" +
                "        end if;\n" +
                "    end process read_proc;\n" +
                "\n" +
                "    -- Handles register write requests from SW by writing the received data to the register with the correct address.\n" +
                "    write_proc : process(CLK, RST) is\n" +
                "    begin\n" +
                "        if RST = RESET_POLARITY_G then\n" +
                "            reg_number1_rw <= (others => '0');\n" +
                "            reg_number2_rw <= (others => '0');\n" +
                "        elsif rising_edge(CLK) then\n" +
                "            if ERB_WR = '1' then\n" +
                "                case erb_addr_int is\n" +
                "                    when addr_number1_rw_c =>\n" +
                "                        reg_write(ERB_DATA_OUT, ERB_WR_BE, reg_number1_rw);\n" +
                "                    when addr_number2_rw_c =>\n" +
                "                        reg_write(ERB_DATA_OUT, ERB_WR_BE, reg_number2_rw);\n" +
                "\n" +
                "                    when others =>\n" +
                "                        report example_regbank_arch'instance_name & \" Invalid write address: \" & integer'image(erb_addr_int)\n" +
                "                            severity error;\n" +
                "                end case;\n" +
                "            end if;\n" +
                "        end if;\n" +
                "    end process write_proc;\n" +
                "\n" +
                "    -- Samples discrete signals from the HW user side into registers.\n" +
                "    discrete_sample_proc : process(CLK) is\n" +
                "    begin\n" +
                "        if rising_edge(CLK) then\n" +
                "            reg_hw_version_ro <= HW_VERSION_RO;\n" +
                "            if STATUS_RO_WE = '1' then\n" +
                "                reg_status_ro <= STATUS_RO;\n" +
                "            end if;\n" +
                "        end if;\n" +
                "    end process discrete_sample_proc;\n" +
                "\n" +
                "    -- Assign non-sampled discrete signals from the HW user side into registers.\n" +
                "    non_sampled_discrete_assignments : block is\n" +
                "    begin\n" +
                "        reg_result_ro <= RESULT_RO;\n" +
                "    end block non_sampled_discrete_assignments;\n" +
                "\n" +
                "    -- Drive RW registers values back to HW user side.\n" +
                "    registers_discrete_output_assignments : block is\n" +
                "    begin\n" +
                "        NUMBER1_RW <= reg_number1_rw;\n" +
                "        NUMBER2_RW <= reg_number2_rw;\n" +
                "    end block registers_discrete_output_assignments;\n" +
                "\n" +
                "    assertions : block is\n" +
                "    begin\n" +
                "        assert not((ERB_WR = '1') and (ERB_RD = '1'))\n" +
                "            report example_regbank_arch'instance_name & \" Simultaneous read and write requests are not supported!\"\n" +
                "            severity error;\n" +
                "\n" +
                "        assert (RESET_POLARITY_G = '1') or (RESET_POLARITY_G = '0')\n" +
                "            report RESET_POLARITY_G'instance_name & \" must be either '1' (active high) or '0' (active low).\"\n" +
                "            severity failure;\n" +
                "\n" +
                "    end block assertions;\n" +
                "\n" +
                "end architecture example_regbank_arch;";
    }
}

