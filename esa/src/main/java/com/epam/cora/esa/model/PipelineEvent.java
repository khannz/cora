package com.epam.cora.esa.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PipelineEvent {

    private EventType eventType;
    private LocalDateTime createdDate;
    private ObjectType objectType;
    private Long objectId;
    private String data;

    @RequiredArgsConstructor
    @Getter
    public enum ObjectType {
        PIPELINE("pipeline"),
        PIPELINE_CODE("pipeline_code"),
        RUN("run"),
        NFS("NFS"),
        S3("S3"),
        AZ("AZ"),
        GS("GS"),
        TOOL("tool"),
        FOLDER("folder"),
        TOOL_GROUP("tool_group"),
        DOCKER_REGISTRY("docker_registry"),
        ISSUE("issue"),
        METADATA_ENTITY("metadata_entity"),
        CONFIGURATION("configuration");

        private final String dbName;

    }
}
