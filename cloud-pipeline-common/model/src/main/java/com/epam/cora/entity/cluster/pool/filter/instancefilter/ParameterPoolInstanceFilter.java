package com.epam.cora.entity.cluster.pool.filter.instancefilter;

import lombok.Data;

import java.util.Map;

@Data
public class ParameterPoolInstanceFilter implements MapInstanceFilter {

    private PoolInstanceFilterOperator operator;
    private Map<String, String> value;
    private PoolInstanceFilterType type = PoolInstanceFilterType.RUN_PARAMETER;
}
