package com.epam.cora.vo;

import com.epam.cora.entity.filter.AclSecuredFilter;
import com.epam.cora.entity.filter.FilterExpression;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class PagingRunFilterExpressionVO implements AclSecuredFilter {

    private int page;
    private int pageSize;
    private int timezoneOffsetInMinutes;
    private FilterExpression filterExpression;

    @JsonIgnore
    private List<Long> allowedPipelines;

    @JsonIgnore
    private String ownershipFilter;
}
