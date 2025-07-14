package com.epam.cora.entity.configuration;

import com.epam.cora.entity.pipeline.run.PipelineStart;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.springframework.util.StringUtils;

import java.util.LinkedHashMap;
import java.util.Map;

@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@JsonIgnoreProperties(ignoreUnknown = true)
public class DtsRunConfigurationEntry extends AbstractRunConfigurationEntry {

    private ExecutionEnvironment executionEnvironment = ExecutionEnvironment.DTS;
    private Long dtsId;
    private Integer coresNumber;

    private Long pipelineId;
    private String pipelineVersion;

    @JsonProperty("cmd_template")
    private String cmdTemplate;
    @JsonProperty("docker_image")
    private String dockerImage;

    @JsonDeserialize(using = PipelineConfValuesMapDeserializer.class)
    private Map<String, PipeConfValueVO> parameters = new LinkedHashMap<>();

    @Override
    public boolean checkConfigComplete() {
        if (dtsId == null) {
            return false;
        }
        if (coresNumber != null && coresNumber < 0) {
            return false;
        }
        if (pipelineId == null && StringUtils.hasText(dockerImage) &&
                StringUtils.hasText(cmdTemplate)) {
            return true;
        }
        return pipelineId != null && StringUtils.hasText(pipelineVersion);
    }

    @Override
    public PipelineStart toPipelineStart() {
        PipelineStart pipelineStart = new PipelineStart();
        pipelineStart.setPipelineId(pipelineId);
        pipelineStart.setVersion(pipelineVersion);
        pipelineStart.setCmdTemplate(cmdTemplate);
        pipelineStart.setDockerImage(dockerImage);
        pipelineStart.setParams(parameters);
        return pipelineStart;
    }

    @Override
    public Integer getWorkerCount() {
        return null;
    }

}
