package com.epam.cora.entity.security.acl;

import lombok.Getter;

@Getter
public enum AclClass {
    PIPELINE,
    FOLDER,
    DATA_STORAGE,
    DOCKER_REGISTRY,
    TOOL,
    TOOL_GROUP,
    CONFIGURATION,
    METADATA_ENTITY,
    ATTACHMENT,
    CLOUD_REGION,
    PIPELINE_USER(false),
    ROLE(false),
    // added only for backward compatibility with old APi versions
    @Deprecated
    AWS_REGION;

    private final boolean supportsEntityManager;

    AclClass() {
        this(true);
    }

    AclClass(final boolean supportsEntityManager) {
        this.supportsEntityManager = supportsEntityManager;
    }
}
