package com.epam.cora.entity.datastorage;

import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
public class StoragePolicy {

    private Boolean versioningEnabled = false;
    private Integer backupDuration;
    private Integer shortTermStorageDuration;
    private Integer longTermStorageDuration;

    public boolean isVersioningEnabled() {
        return versioningEnabled != null && versioningEnabled;
    }
}
