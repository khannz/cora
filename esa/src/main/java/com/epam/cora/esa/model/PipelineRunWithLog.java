package com.epam.cora.esa.model;

import com.epam.cora.entity.pipeline.PipelineRun;
import com.epam.cora.entity.pipeline.RunLog;
import com.epam.cora.entity.user.PipelineUser;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class PipelineRunWithLog {

    private PipelineRun pipelineRun;
    private PipelineUser runOwner;
    private List<RunLog> runLogs;
}
