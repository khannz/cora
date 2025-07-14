package com.epam.cora.entity.cluster;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * Instance price type.
 */
@RequiredArgsConstructor
@Getter
public enum PriceType {
    SPOT("spot"), ON_DEMAND("on_demand");

    /**
     * Price type string representation.
     */
    private final String literal;

    @Override
    public String toString() {
        return literal;
    }
}
