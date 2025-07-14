package com.epam.cora.entity.pipeline.run;

import com.epam.cora.entity.pipeline.TaskStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RunStatus {

    private Long runId;
    private TaskStatus status;
    private LocalDateTime timestamp;
}
