package com.epam.cora.entity.pipeline;

import java.util.Date;
import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RunLog {
    private Long runId;
    private Date date;
    private TaskStatus status;
    private String taskName;
    private String logText;
    private String instance;
    private PipelineTask task;
}
