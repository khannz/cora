package com.epam.cora.entity.utils;

public final class ProviderUtils {

    public static final String DELIMITER = "/";
    public static final String FOLDER_TOKEN_FILE = ".DS_Store";

    private ProviderUtils() {
        //no op
    }

    public static String withTrailingDelimiter(final String path) {
        return !path.endsWith(DELIMITER) ? path + DELIMITER : path;
    }

    public static String normalizeBucketName(String name) {
        String bucketName = name.trim().toLowerCase();
        bucketName = bucketName.replaceAll("[^a-z0-9\\-]+", "-");
        return bucketName;
    }
}
