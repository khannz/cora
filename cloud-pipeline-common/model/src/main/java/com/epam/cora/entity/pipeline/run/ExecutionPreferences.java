package com.epam.cora.entity.pipeline.run;

import com.epam.cora.entity.configuration.ExecutionEnvironment;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

@JsonTypeInfo(
        use = JsonTypeInfo.Id.NAME,
        include = JsonTypeInfo.As.PROPERTY,
        property = "environment",
        defaultImpl = CloudPlatformPreferences.class)
@JsonSubTypes({
        @JsonSubTypes.Type(value = FirecloudPreferences.class, name = "FIRECLOUD"),
        @JsonSubTypes.Type(value = CloudPlatformPreferences.class, name = "CLOUD_PLATFORM"),
        @JsonSubTypes.Type(value = DtsExecutionPreferences.class, name = "DTS")})
public interface ExecutionPreferences {

    @JsonIgnore
    ExecutionEnvironment getEnvironment();

    static ExecutionPreferences getDefault() {
        return new CloudPlatformPreferences();
    }
}
