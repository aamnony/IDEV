package com.github.amnonya.hdleditor.vhdl.options.colors;

import com.github.amnonya.hdleditor.vhdl.VhdlIcons;
import com.github.amnonya.hdleditor.vhdl.highlighting.VhdlSyntaxHighlighterFactory;
import com.github.amnonya.hdleditor.vhdl.lang.VhdlLanguage;
import com.google.common.collect.ImmutableMap;
import com.intellij.openapi.editor.colors.TextAttributesKey;
import com.intellij.openapi.fileTypes.SyntaxHighlighter;
import com.intellij.openapi.options.colors.AttributesDescriptor;
import com.intellij.openapi.options.colors.ColorDescriptor;
import com.intellij.openapi.options.colors.ColorSettingsPage;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Map;

import javax.swing.Icon;

import static com.github.amnonya.hdleditor.vhdl.highlighting.VhdlHighlightingColors.*;

public class VhdlColorSettingsPage implements ColorSettingsPage {
    private static final AttributesDescriptor[] DESCRIPTORS = new AttributesDescriptor[]{
            new AttributesDescriptor("Design Units//Primary Design Unit", PRIMARY_DESIGN_UNIT),
            new AttributesDescriptor("Design Units//Secondary Design Unit", SECONDARY_DESIGN_UNIT),
            new AttributesDescriptor("Keyword", KEYWORD),
            new AttributesDescriptor("Braces and Operators//Operator", OPERATOR),
            new AttributesDescriptor("Identifiers//Type", TYPE),
            new AttributesDescriptor("Braces and Operators//Semicolon", SEMICOLON),
            new AttributesDescriptor("Braces and Operators//Comma", COMMA),
            new AttributesDescriptor("Braces and Operators//Dot", DOT),
            new AttributesDescriptor("Braces and Operators//Parenthesis", PARENTHESIS),
            new AttributesDescriptor("Comment", COMMENT),
            new AttributesDescriptor("Character", CHARACTER),
            new AttributesDescriptor("String", STRING),
            new AttributesDescriptor("Number", NUMBER),
            new AttributesDescriptor("Identifiers//Generic", GENERIC),
            new AttributesDescriptor("Identifiers//Port", PORT),
            new AttributesDescriptor("Identifiers//Signal", SIGNAL),
            new AttributesDescriptor("Identifiers//Constant", CONSTANT),
            new AttributesDescriptor("Identifiers//Variable", VARIABLE),
            new AttributesDescriptor("Identifiers//Attribute", ATTRIBUTE),
            new AttributesDescriptor("Identifiers//Alias", ALIAS),
            new AttributesDescriptor("Identifiers//Subprogram Parameter", SUBPROGRAM_PARAMETER),
            new AttributesDescriptor("Identifiers//Subprogram Declaration", SUBPROGRAM_DECLARATION),
            new AttributesDescriptor("Identifiers//Subprogram Call", SUBPROGRAM_CALL),
            new AttributesDescriptor("Identifiers//Label", LABEL),
    };

    private static final Map<String, TextAttributesKey> ADDITIONAL_DESCRIPTORS = ImmutableMap.<String, TextAttributesKey>builder()
            .put("primary_design_unit", PRIMARY_DESIGN_UNIT)
            .put("secondary_design_unit", SECONDARY_DESIGN_UNIT)
            .put("type", TYPE)
            .put("generic", GENERIC)
            .put("port", PORT)
            .put("signal", SIGNAL)
            .put("constant", CONSTANT)
            .put("variable", VARIABLE)
            .put("attribute", ATTRIBUTE)
            .put("alias", ALIAS)
            .put("subprogram_parameter", SUBPROGRAM_PARAMETER)
            .put("subprogram_declaration", SUBPROGRAM_DECLARATION)
            .put("subprogram_call", SUBPROGRAM_CALL)
            .put("label", LABEL)
            .build();

    @Nullable
    @Override
    public Icon getIcon() {
        return VhdlIcons.FILE;
    }

    @NotNull
    @Override
    public SyntaxHighlighter getHighlighter() {
        return VhdlSyntaxHighlighterFactory.getSyntaxHighlighter(VhdlLanguage.INSTANCE, null, null);
    }

    @NotNull
    @Override
    public String getDemoText() {
        return "library ieee;\n" +
                "    use ieee.<primary_design_unit>std_logic_1164</primary_design_unit>.all;\n" +
                "    use ieee.<primary_design_unit>numeric_std</primary_design_unit>.all;\n" +
                "\n" +
                "\n" +
                "-- Block comment\n" +
                "-- extending\n" +
                "-- multiple lines\n" +
                "entity <primary_design_unit>some_entity</primary_design_unit> is\n" +
                "    generic(\n" +
                "        <generic>some_generic_g</generic>    : <type>std_logic</type> := '1';  -- This is a generic.\n" +
                "        <generic>another_generic_g</generic> : <type>integer</type>   := 50      -- This is another generic.\n" +
                "    );\n" +
                "    port(\n" +
                "        <port>CLOCK</port>       : in    <type>std_logic</type>;                             -- Clock.\n" +
                "        <port>RESET</port>       : in    <type>std_logic</type>;                             -- Active high asynchronous reset.\n" +
                "        <port>DATA_IN</port>     : in    <type>integer</type> range 0 to <generic>another_generic_g</generic>;  -- Input data..\n" +
                "        <port>DATA_OUT</port>    : out   <type>integer</type> range 0 to <generic>another_generic_g</generic>;  -- Output data.\n" +
                "        <port>CONTROL_BUS</port> : inout <type>std_logic_vector</type>(7 downto 0);          -- Bidirectional control bus.\n" +
                "    );\n" +
                "end entity <primary_design_unit>some_entity</primary_design_unit>;\n" +
                "\n" +
                "\n" +
                "architecture <secondary_design_unit>some_architecture</secondary_design_unit> of <primary_design_unit>some_entity</primary_design_unit> is\n" +
                "\n" +
                "    constant <constant>some_constant_c</constant> : <type>time</type> := 10 ns;\n" +
                "    signal   <signal>some_signal</signal>     : <type>boolean</type>;\n" +
                "    \n" +
                "    function <subprogram_declaration>some_function</subprogram_declaration>(<subprogram_parameter>some_parameter</subprogram_parameter> : <type>integer</type>) return <type>boolean</type> is\n" +
                "    begin\n" +
                "        return <subprogram_parameter>some_parameter</subprogram_parameter> > 17;\n" +
                "    end function <subprogram_declaration>some_function</subprogram_declaration>;\n" +
                "\n" +
                "begin\n" +
                "\n" +
                "    -- No reset flip-flop process.\n" +
                "    <label>no_reset_ff_proc</label> : process(<port>CLOCK</port>) is\n" +
                "        variable <variable>some_variable</variable> : <type>integer</type>;\n" +
                "    begin\n" +
                "        <variable>some_variable</variable> := another_generic_g * 3;\n" +
                "        <variable>some_variable</variable> := integer(<variable>some_variable</variable> / 1.7);\n" +
                "        if <subprogram_call>rising_edge</subprogram_call>(<port>CLOCK</port>) then\n" +
                "            if <subprogram_call>some_function</subprogram_call>(<variable>some_variable</variable>) then\n" +
                "                some_signal <= true;\n" +
                "                report some_entity'<attribute>instance_name</attribute> & \" this is a string concatenation\" severity note;\n" +
                "            else\n" +
                "                some_signal <= false;\n" +
                "            end if;\n" +
                "        end if;\n" +
                "    end process <label>no_reset_ff_proc</label>;\n" +
                "\n" +
                "    -- Flip flop process with reset.\n" +
                "    <label>reset_ff_proc</label> : process(<port>CLOCK</port>, <port>REEST</port>) is\n" +
                "    begin\n" +
                "        if <port>RESET</port> = '1' then\n" +
                "            <port>CONTROL_BUS</port> <= (others => 'Z');\n" +
                "        elsif <subprogram_call>rising_edge</subprogram_call>() then\n" +
                "            if <port>CONTROL_BUS</port>(0) = '1' then\n" +
                "                <port>DATA_OUT</port> <= <port>DATA_IN</port>;\n" +
                "            else\n" +
                "                <port>DATA_OUT</port> <= not <port>DATA_IN</port>;\n" +
                "            end if;\n" +
                "        end if;\n" +
                "    end process <label>reset_ff_proc</label>;\n" +
                "\n" +
                "end architecture <secondary_design_unit>some_architecture</secondary_design_unit>;";
    }

    @Nullable
    @Override
    public Map<String, TextAttributesKey> getAdditionalHighlightingTagToDescriptorMap() {
        return ADDITIONAL_DESCRIPTORS;
    }

    @NotNull
    @Override
    public AttributesDescriptor[] getAttributeDescriptors() {
        return DESCRIPTORS;
    }

    @NotNull
    @Override
    public ColorDescriptor[] getColorDescriptors() {
        return ColorDescriptor.EMPTY_ARRAY;
    }

    @NotNull
    @Override
    public String getDisplayName() {
        return VhdlLanguage.INSTANCE.getDisplayName();
    }
}
