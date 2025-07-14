package com.epam.cora.esa.model;

import com.epam.cora.entity.configuration.RunConfiguration;
import com.epam.cora.entity.pipeline.Pipeline;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
public class RunConfigurationDoc {

    private RunConfiguration configuration;
    private List<Pipeline> pipelines;
}
