package com.epam.cora.config;

@SuppressWarnings("checkstyle:ConstantName")
public final class Constants {
    public static final String FMT_ISO_LOCAL_DATE = "yyyy-MM-dd HH:mm:ss.SSS";
    public static final String SECURITY_DATE_TIME_FORMAT = "yyyy-MM-dd'T'HH:mm:ss.SSS";
    public static final String SIMPLE_DATE_FORMAT = "yyyyMMdd";
    public static final String SIMPLE_TIME_FORMAT = "HHmmss";
    public static final Integer SECONDS_IN_MINUTE = 60;
    public static final String PATH_DELIMITER = "/";
    public static final String X509_BEGIN_CERTIFICATE = "-----BEGIN CERTIFICATE-----";
    public static final String X509_END_CERTIFICATE = "-----END CERTIFICATE-----";
    public static final String MODEL_PARAMETERS_FILE_NAME = "src/model_parameters.json";
    public static final String HTTP_AUTH_COOKIE = "HttpAuthorization";
    public static final double HUNDRED_PERCENTS = 100.0;

    public static final String FIRECLOUD_TOKEN_HEADER = "Firecloud-Token";

    private Constants() {
        //no op
    }
}
