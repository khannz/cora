package com.epam.cora.entity.log;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class LogRequest {
    private LogFilter filter;
    private String groupBy;
}
