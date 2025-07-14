package com.epam.cora.entity.log;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
public class LogFilter {
    private List<String> hostnames;
    private String message;
    private LocalDateTime messageTimestampFrom;
    private LocalDateTime messageTimestampTo;
    private List<String> serviceNames;
    private List<String> types;
    private List<String> users;
    private Boolean includeServiceAccountEvents;
    private LogPaginationRequest pagination;
    private String sortOrder;
}
