package com.epam.cora.entity.pipeline.run;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
public class RestartRun {

    private Long parentRunId;
    private Long restartedRunId;
    private Date date;
}
