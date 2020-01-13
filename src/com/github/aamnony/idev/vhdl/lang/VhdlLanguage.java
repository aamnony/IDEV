package com.github.aamnony.idev.vhdl.lang;

import com.intellij.lang.Language;
import com.intellij.psi.tree.IElementType;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class VhdlLanguage extends Language {
    public static final VhdlLanguage INSTANCE = new VhdlLanguage();

    public static final String LEGEL_IDENTIFIER_PATTERN = "^"
            + "[a-zA-Z]"             // Starts with a letter.
            + "(?:_?[a-zA-Z0-9]+)*"  // Continues with groups of letters and digits, which are separated by a *single* underscore.
            + "$";                   // Ends with either a letter or a digit (not an underscore).

    private VhdlLanguage() {
        super("VHDL");
    }

    public static boolean isLegalIdentifier(String id) {
        if (id.length() > 0) {
            Pattern pattern = Pattern.compile(LEGEL_IDENTIFIER_PATTERN, Pattern.MULTILINE);
            Matcher matcher = pattern.matcher(id);
            boolean isLegalName = matcher.matches();
            if (isLegalName) {
                boolean isKeyword = false;
                for (IElementType keyword : VhdlElementTypes.KEYWORDS.getTypes()) {
                    if (keyword.toString().equalsIgnoreCase(id)) {
                        isKeyword = true;
                    }
                }
                return !isKeyword;
            }
        }
        return false;

    }
}