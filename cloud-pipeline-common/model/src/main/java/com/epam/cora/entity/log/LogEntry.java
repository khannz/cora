package com.epam.cora.entity.log;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class LogEntry {
    private Long eventId;
    private String hostname;
    private String message;
    private LocalDateTime messageTimestamp;
    private String serviceName;
    private String type;
    private String user;
    private String severity;
    private Long storageId;
}
