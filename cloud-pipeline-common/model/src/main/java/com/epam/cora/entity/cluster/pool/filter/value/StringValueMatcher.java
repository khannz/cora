package com.epam.cora.entity.cluster.pool.filter.value;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;

@RequiredArgsConstructor
@Getter
public class StringValueMatcher implements ValueMatcher<String> {

    private final String value;

    @Override
    public boolean matches(final String anotherValue) {
        return StringUtils.equals(getValue(), anotherValue);
    }

    @Override
    public boolean empty(final String anotherValue) {
        return StringUtils.isBlank(anotherValue);
    }
}
