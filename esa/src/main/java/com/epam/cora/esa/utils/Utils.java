package com.epam.cora.esa.utils;

import org.apache.commons.collections4.CollectionUtils;

import java.util.List;

public final class Utils {

    private Utils() {
        //
    }

    public static <T> T last(final List<T> collection) {
        if (CollectionUtils.isEmpty(collection)) {
            throw new IllegalArgumentException("Collection shall not by empty!");
        }

        return collection.getLast();
    }
}
