package com.epam.cora.entity.cluster.pool.filter.value;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.Objects;

@RequiredArgsConstructor
@Getter
public class LongValueMatcher implements ValueMatcher<Long> {

    private final Long value;

    @Override
    public boolean matches(final Long anotherValue) {
        return Objects.equals(getValue(), anotherValue);
    }

    @Override
    public boolean empty(final Long anotherValue) {
        return Objects.isNull(anotherValue);
    }
}
