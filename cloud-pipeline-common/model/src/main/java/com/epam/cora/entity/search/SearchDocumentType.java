package com.epam.cora.entity.search;

public enum SearchDocumentType {

    PIPELINE_RUN,
    S3_FILE,
    AZ_BLOB_FILE,
    NFS_FILE,
    GS_FILE,
    S3_STORAGE,
    NFS_STORAGE,
    AZ_BLOB_STORAGE,
    GS_STORAGE,
    TOOL,
    DOCKER_REGISTRY,
    TOOL_GROUP,
    FOLDER,
    CONFIGURATION,
    PIPELINE,
    ISSUE,
    METADATA_ENTITY,
    PIPELINE_CODE
}
