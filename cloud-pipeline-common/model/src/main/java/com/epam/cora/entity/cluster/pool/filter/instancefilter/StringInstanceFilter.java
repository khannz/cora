package com.epam.cora.entity.cluster.pool.filter.instancefilter;

import com.epam.cora.entity.cluster.pool.filter.value.StringValueMatcher;
import com.epam.cora.entity.cluster.pool.filter.value.ValueMatcher;

public interface StringInstanceFilter extends PoolInstanceFilter<String> {

    @Override
    default ValueMatcher<String> getMatcher() {
        return new StringValueMatcher(getValue());
    }
}
