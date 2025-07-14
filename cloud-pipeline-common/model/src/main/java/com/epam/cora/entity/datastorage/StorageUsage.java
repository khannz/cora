package com.epam.cora.entity.datastorage;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Value;

import java.util.Map;

@AllArgsConstructor
@Builder
@Data
public class StorageUsage {
    private Long id;
    private String name;
    private DataStorageType type;
    private String path;
    private Long size;
    private Long count;
    private Long effectiveSize;
    private Long effectiveCount;
    private Long oldVersionsSize;
    private Long oldVersionsEffectiveSize;

    private Map<String, StorageUsageStats> usage;

    @Value
    @Builder
    @AllArgsConstructor
    public static class StorageUsageStats {
        String storageClass;
        Long size;
        Long count;
        Long effectiveSize;
        Long effectiveCount;
        Long oldVersionsSize;
        Long oldVersionsEffectiveSize;
    }
}
