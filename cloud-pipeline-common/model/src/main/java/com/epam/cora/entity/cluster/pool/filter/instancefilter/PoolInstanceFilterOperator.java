package com.epam.cora.entity.cluster.pool.filter.instancefilter;

import com.epam.cora.entity.cluster.pool.filter.value.ValueMatcher;
import org.apache.commons.collections4.CollectionUtils;

import java.util.Collection;
import java.util.Collections;
import java.util.stream.Stream;

public enum PoolInstanceFilterOperator {
    EQUAL, NOT_EQUAL, EMPTY, NOT_EMPTY;

    public <T> boolean evaluate(final ValueMatcher<T> matcher, final T value) {
        return evaluate(matcher, Collections.singletonList(value));
    }

    public <T> boolean evaluate(final ValueMatcher<T> matcher, final Collection<T> values) {
        switch (this) {
            case EQUAL:
                return stream(values).anyMatch(matcher::matches);
            case NOT_EQUAL:
                return stream(values).allMatch(matcher::notMatches);
            case EMPTY:
                return stream(values).anyMatch(matcher::empty);
            case NOT_EMPTY:
                return stream(values).allMatch(matcher::notEmpty);
            default:
                throw new IllegalArgumentException("Unsupported operator type " + this);
        }
    }

    private <T> Stream<T> stream(final Collection<T> values) {
        if (CollectionUtils.isEmpty(values)) {
            return Stream.empty();
        }
        return values.stream();
    }
}
