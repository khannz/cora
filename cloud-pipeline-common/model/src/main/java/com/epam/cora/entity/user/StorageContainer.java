package com.epam.cora.entity.user;

/**
 * Marks that com.epam.pipeline.entity has some storage attached
 */
public interface StorageContainer {

    Long getId();
    /**
     Returns assigned to com.epam.pipeline.entity storage. Nullable.
     */
    Long getDefaultStorageId();
}
