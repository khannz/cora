package com.epam.cora.esa.model.git;

import com.epam.cora.esa.model.EventType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
public class GitFileEvent {

    private String version;

    private String path;

    private LocalDateTime timestamp;

    private EventType eventType;
}
