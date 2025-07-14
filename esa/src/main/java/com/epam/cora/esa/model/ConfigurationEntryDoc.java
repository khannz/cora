package com.epam.cora.esa.model;

import com.epam.cora.entity.configuration.AbstractRunConfigurationEntry;
import com.epam.cora.entity.configuration.RunConfiguration;
import com.epam.cora.entity.pipeline.Pipeline;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class ConfigurationEntryDoc {

    private String id;
    private RunConfiguration configuration;
    private AbstractRunConfigurationEntry entry;
    private Pipeline pipeline;
}
