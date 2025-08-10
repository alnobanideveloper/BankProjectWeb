package com.eastnets.util;

public class StringUtils {
    private StringUtils() {}

    public static boolean isBlankOrNull(String str) {
        return str == null || str.isBlank();
    }
}
