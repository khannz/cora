package com.epam.cora.entity.cluster.pool.filter.instancefilter;

import lombok.Data;

@Data
public class DockerPoolInstanceFilter implements StringInstanceFilter {

    private PoolInstanceFilterOperator operator;
    private String value;
    private PoolInstanceFilterType type = PoolInstanceFilterType.DOCKER_IMAGE;
}
