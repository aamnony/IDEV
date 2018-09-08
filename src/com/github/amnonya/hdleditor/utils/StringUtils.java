package com.github.amnonya.hdleditor.utils;

import org.jetbrains.annotations.NotNull;

public class StringUtils {
    @NotNull
    public static String shrinkParenthesis(String text) {
        return text.replaceAll("\\(.*\\)", "(…)");
    }
}
