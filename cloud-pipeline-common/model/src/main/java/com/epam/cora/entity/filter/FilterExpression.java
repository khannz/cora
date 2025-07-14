package com.epam.cora.entity.filter;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class FilterExpression {

    private String field;
    private String value;
    private String operand;
    private String filterExpressionType;
}
