package com.epam.cora.entity.cluster.pool.filter.value;

public interface ValueMatcher<T> {

    T getValue();

    boolean matches(T anotherValue);

    default boolean notMatches(T anotherValue) {
        return !matches(anotherValue);
    }

    boolean empty(T anotherValue);

    default boolean notEmpty(T anotherValue) {
        return !empty(anotherValue);
    }
}
