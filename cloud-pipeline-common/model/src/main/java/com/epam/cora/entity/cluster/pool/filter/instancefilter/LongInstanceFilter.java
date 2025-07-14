package com.epam.cora.entity.cluster.pool.filter.instancefilter;

import com.epam.cora.entity.cluster.pool.filter.value.LongValueMatcher;
import com.epam.cora.entity.cluster.pool.filter.value.ValueMatcher;

public interface LongInstanceFilter extends PoolInstanceFilter<Long> {

    @Override
    default ValueMatcher<Long> getMatcher() {
        return new LongValueMatcher(getValue());
    }
}
