package com.github.amnonya.hdleditor.utils;

import org.jetbrains.annotations.NotNull;

public class StringUtils {
    private StringUtils() {
    }

    /**
     * Replaces substrings in {@code text} which are inside parenthesis with an ellipsis.
     *
     * @param text The {@link String} to alter.
     * @return The altered {@link String}.
     */
    @NotNull
    public static String shrinkParenthesis(String text) {
        return text.replaceAll("\\(.*\\)", "(…)");
    }

    /**
     * Checks if {@code haystack} contains exactly {@code count} amount of {@code needle}s.
     *
     * @param haystack The {@link String} to search in.
     * @param needle   The {@link String} to search for.
     * @param count    The amount of times {@code needle} needs to occur in {@code haystack}.
     * @return Whether or not {@code haystack} contains exactly {@code count} amount of {@code needle}s.
     */
    public static boolean contains(String haystack, String needle, int count) {
        int lastIndex = 0;
        for (int i = 0; i < count; i++) {
            lastIndex = haystack.indexOf(needle, lastIndex);
            if (lastIndex < 0) {
                return false;
            }
        }
        return true;
    }
}
