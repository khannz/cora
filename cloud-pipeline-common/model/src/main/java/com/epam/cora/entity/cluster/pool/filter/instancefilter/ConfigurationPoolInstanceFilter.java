package com.epam.cora.entity.cluster.pool.filter.instancefilter;

import lombok.Data;

@Data
public class ConfigurationPoolInstanceFilter implements LongInstanceFilter {

    private PoolInstanceFilterOperator operator;
    private Long value;
    private PoolInstanceFilterType type = PoolInstanceFilterType.CONFIGURATION_ID;
}
