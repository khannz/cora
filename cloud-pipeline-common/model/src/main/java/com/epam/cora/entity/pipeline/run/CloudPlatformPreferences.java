package com.epam.cora.entity.pipeline.run;

import com.epam.cora.entity.configuration.ExecutionEnvironment;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class CloudPlatformPreferences implements ExecutionPreferences {
    @Override
    public ExecutionEnvironment getEnvironment() {
        return ExecutionEnvironment.CLOUD_PLATFORM;
    }
}
