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
public class DtsExecutionPreferences implements ExecutionPreferences {

    private Long dtsId;
    private Integer coresNumber;

    @Override
    public ExecutionEnvironment getEnvironment() {
        return ExecutionEnvironment.DTS;
    }
}
