package com.epam.cora.esa.utils;

import java.text.DateFormat;
import java.text.SimpleDateFormat;

public final class ESConstants {

    public static final String DOC_MAPPING_TYPE = "_doc";
    public static final String HIDDEN_FILE_NAME = ".DS_Store";
    public static final String STORAGE_CLASS_LABEL = "StorageClass";
    public static final DateFormat FILE_DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
    public static final DateFormat GS_DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'");

    private ESConstants() {
        //
    }
}
