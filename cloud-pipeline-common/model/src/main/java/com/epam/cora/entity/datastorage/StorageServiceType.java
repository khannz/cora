package com.epam.cora.entity.datastorage;

/**
 * Basic types of storage services provided by cloud providers
 */
public enum StorageServiceType {
    /**
     * EFS for AWS, FS for Azure
     */
    FILE_SHARE,
    /**
     * S3 for AWS, Blob Storage for Azure
     */
    OBJECT_STORAGE,
    /**
     * AWS Omics stores
     */
    AWS_OMICS_REF,
    AWS_OMICS_SEQ;
}
