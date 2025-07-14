package com.epam.cora.entity.configuration;

import com.epam.cora.entity.pipeline.run.PipelineStart;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * Configuration entry for {@link ExecutionEnvironment#FIRECLOUD}.
 */
@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@JsonIgnoreProperties(ignoreUnknown = true)
public class FirecloudRunConfigurationEntry extends AbstractRunConfigurationEntry {
    public static final String PARAMETERS_PROPERTY = "parameters";

    private ExecutionEnvironment executionEnvironment = ExecutionEnvironment.FIRECLOUD;
    private String methodName;
    private String methodSnapshot;
    private String methodConfigurationName;
    private String methodConfigurationSnapshot;
    private List<InputsOutputs> methodInputs;
    private List<InputsOutputs> methodOutputs;

    //TODO: added only for backward compatibility with UI Client, remove later
    private Configuration configuration;

    @Override
    public boolean checkConfigComplete() {
        return StringUtils.hasText(methodName) &&
                StringUtils.hasText(methodSnapshot) &&
                (StringUtils.hasText(methodConfigurationName) ||
                        !CollectionUtils.isEmpty(methodInputs) ||
                        !CollectionUtils.isEmpty(methodOutputs));
    }

    @Override
    public PipelineStart toPipelineStart() {
        return null;
    }

    @Override
    public Integer getWorkerCount() {
        return null;
    }

    @JsonIgnore
    public Map<String, PipeConfValueVO> getParameters() {
        return configuration == null ? Collections.emptyMap() : configuration.getParameters();
    }

    @Data
    @JsonIgnoreProperties(ignoreUnknown = true)
    private static class Configuration {
        @JsonProperty(value = PARAMETERS_PROPERTY)
        @JsonDeserialize(using = PipelineConfValuesMapDeserializer.class)
        private Map<String, PipeConfValueVO> parameters = new LinkedHashMap<>();
    }

}
