package com.epam.cora.utils;

import org.apache.commons.lang3.RandomStringUtils;

public final class PasswordGenerator {

    private static final int PASSWORD_LENGTH = 30;

    private PasswordGenerator() {
        //no op
    }

    public static String generatePassword() {
        return generateRandomString(PASSWORD_LENGTH);
    }

    public static String generateRandomString(int length) {
        return RandomStringUtils.secure().nextAlphanumeric(length);
    }
}
