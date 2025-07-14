package com.epam.cora.entity.cluster.pool.filter.instancefilter;

import com.epam.cora.entity.cluster.pool.filter.value.StringCaseInsensitiveValueMatcher;
import com.epam.cora.entity.cluster.pool.filter.value.ValueMatcher;
import lombok.Data;

@Data
public class RunOwnerPoolInstanceFilter implements StringInstanceFilter {

    private PoolInstanceFilterOperator operator;
    private String value;
    private PoolInstanceFilterType type = PoolInstanceFilterType.RUN_OWNER;

    @Override
    public ValueMatcher<String> getMatcher() {
        return new StringCaseInsensitiveValueMatcher(value);
    }
}
