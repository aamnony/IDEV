package com.github.amnonya.hdleditor.utils;

import org.jetbrains.annotations.NotNull;

public class StringUtils {

    private StringUtils() {
    }

    @NotNull
    public static String shrinkParenthesis(String text) {
        return text.replaceAll("\\(.*\\)", "(…)");
    }
}
