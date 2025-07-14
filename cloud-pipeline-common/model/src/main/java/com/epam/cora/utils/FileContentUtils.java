package com.epam.cora.utils;

import org.apache.commons.lang3.StringUtils;

import java.nio.charset.Charset;

public final class FileContentUtils {

    private static final int CONTENT_CHECK_LIMIT = 1024;
    private static final int ASCII_LIMIT = 128;
    private static final int SPACE = 31;
    private static final int BACKSPACE = 8;
    private static final int CARRIAGE_RETURN = 13;

    private FileContentUtils() {
    }

    public static boolean isBinaryContent(byte[] byteContent) {
        if (byteContent == null || byteContent.length == 0) {
            return false;
        }
        return !isAsciiContent(new String(byteContent, 0, Math.min(byteContent.length, CONTENT_CHECK_LIMIT), Charset.defaultCharset()));
    }

    /**
     * Checks if all symbols in the input string are in ASCII range fro letters [31,127] or
     * it is one of common special symbols: new line, tab, etc.
     *
     * @param data
     * @return
     */
    public static boolean isAsciiContent(String data) {
        if (StringUtils.isBlank(data)) {
            return false;
        }
        return data.chars().allMatch(c -> (c >= SPACE && c < ASCII_LIMIT) || (c >= BACKSPACE && c <= CARRIAGE_RETURN));
    }
}
