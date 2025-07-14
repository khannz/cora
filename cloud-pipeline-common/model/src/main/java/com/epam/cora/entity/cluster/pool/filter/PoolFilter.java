package com.epam.cora.entity.cluster.pool.filter;

import com.epam.cora.entity.cluster.pool.filter.instancefilter.PoolInstanceFilter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import org.apache.commons.collections4.CollectionUtils;

import java.util.List;

@Data
public class PoolFilter {
    private final PoolFilterOperator operator;
    private final List<PoolInstanceFilter> filters;

    @JsonIgnore
    public boolean isEmpty() {
        return CollectionUtils.isEmpty(filters);
    }
}
