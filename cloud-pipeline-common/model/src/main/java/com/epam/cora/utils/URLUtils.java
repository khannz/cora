package com.epam.cora.utils;

import org.apache.commons.lang3.StringUtils;
import org.springframework.util.Assert;

public final class URLUtils {

    private URLUtils() {
        //no op
    }

    public static String normalizeUrl(String url) {
        Assert.state(StringUtils.isNotBlank(url), "URL shall be specified");
        return url.endsWith("/") ? url : url + "/";
    }

    public static String getUrlWithoutTrailingSlash(String url) {
        return url.endsWith("/") ? url.substring(0, url.length() - 1) : url;
    }
}
