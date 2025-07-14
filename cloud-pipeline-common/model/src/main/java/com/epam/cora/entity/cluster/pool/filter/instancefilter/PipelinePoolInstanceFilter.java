package com.epam.cora.entity.cluster.pool.filter.instancefilter;

import lombok.Data;

@Data
public class PipelinePoolInstanceFilter implements LongInstanceFilter {

    private PoolInstanceFilterOperator operator;
    private Long value;
    private PoolInstanceFilterType type = PoolInstanceFilterType.PIPELINE_ID;
}
