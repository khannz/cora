package com.epam.cora.entity.pipeline.run.parameter;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class PipelineRunParameter {
    private String name;
    /**
     * Represents original parameter value without environment variable substitution
     */
    private String value;

    private String type;
    /**
     * Represents parameter value with resolved environment variables, ${VAR} ans $VAR
     * syntax is supported
     */
    private String resolvedValue;
    private List<DataStorageLink> dataStorageLinks;

    public PipelineRunParameter(String parameter) {
        this(parameter, null);
    }

    public PipelineRunParameter(String name, String value) {
        this(name, value, null);
    }

    public PipelineRunParameter(String name, String value, String type) {
        this.name = name;
        this.value = value;
        this.type = type;
    }

    @Override
    public String toString() {
        if (value != null) {
            return "PipelineRunParameter{" + "name='" + name + '\'' + ", value='" + value + '\'' + '}';
        } else {
            return "PipelineRunParameter{" + "name='" + name + "\'}";
        }
    }
}
