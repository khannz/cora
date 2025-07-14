package com.epam.cora.entity.cluster.pool.filter.value;

import org.apache.commons.lang3.StringUtils;

public class StringCaseInsensitiveValueMatcher extends StringValueMatcher {

    public StringCaseInsensitiveValueMatcher(final String value) {
        super(value);
    }

    @Override
    public boolean matches(final String anotherValue) {
        return StringUtils.equalsIgnoreCase(getValue(), anotherValue);
    }
}
