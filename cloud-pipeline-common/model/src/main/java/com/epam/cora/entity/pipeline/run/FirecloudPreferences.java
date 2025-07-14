package com.epam.cora.entity.pipeline.run;

import com.epam.cora.entity.configuration.ExecutionEnvironment;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@JsonIgnoreProperties(ignoreUnknown = true)
public class FirecloudPreferences implements ExecutionPreferences {

    private String method;
    private String methodSnapshot;
    private String methodConfiguration;
    private String methodConfigurationSnapshot;

    @Override
    public ExecutionEnvironment getEnvironment() {
        return ExecutionEnvironment.FIRECLOUD;
    }


}
