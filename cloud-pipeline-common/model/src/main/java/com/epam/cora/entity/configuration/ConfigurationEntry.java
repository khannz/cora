package com.epam.cora.entity.configuration;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.util.StringUtils;

/**
 * Represents a set of settings for a {@link com.epam.cora.entity.pipeline.Pipeline}, usually is
 * stored in config.json file in pipeline's repository.
 */
@Getter
@Setter
@NoArgsConstructor
public class ConfigurationEntry {

    public static final String DEFAULT = "default";

    private String name = DEFAULT;
    private String description;
    @JsonProperty(value = DEFAULT)
    private Boolean defaultConfiguration = false;

    private PipelineConfiguration configuration;

    public ConfigurationEntry(PipelineConfiguration configuration) {
        this.configuration = configuration;
        this.defaultConfiguration = true;
    }

    public boolean checkConfigComplete() {
        if (!StringUtils.hasText(name)) {
            return false;
        }
        if (configuration == null || !StringUtils.hasText(configuration.getCmdTemplate())) {
            return false;
        }
        return true;
    }
}
