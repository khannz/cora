package com.epam.cora.entity.pipeline.run;

import com.epam.cora.entity.configuration.ExecutionEnvironment;
import com.epam.cora.entity.configuration.PipeConfValueVO;
import com.epam.cora.entity.configuration.PipelineConfValuesMapDeserializer;
import com.epam.cora.entity.pipeline.run.parameter.RunSid;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Map;

@Data
@NoArgsConstructor
public class PipelineStart {
    private Long pipelineId;
    private String version;
    private Long timeout;
    private String instanceType;
    private Integer hddSize;
    private String dockerImage;
    private String cmdTemplate;
    private Long useRunId;
    private Long parentNodeId;
    private String configurationName;
    private Integer nodeCount;
    private String workerCmd;
    private Long parentRunId;
    private Boolean isSpot;
    private List<RunSid> runSids;
    private Long cloudRegionId;
    private boolean force;
    private ExecutionEnvironment executionEnvironment;
    private String prettyUrl;
    private boolean nonPause;

    @JsonDeserialize(using = PipelineConfValuesMapDeserializer.class)
    private Map<String, PipeConfValueVO> params;
}
