package com.epam.cora.entity.datastorage.lifecycle.restore;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class StorageRestoreAction {
    private Long id;
    private Long datastorageId;
    private Long userActorId;
    private String path;
    private StorageRestorePathType type;
    private Boolean restoreVersions;
    private String restoreMode;
    private Long days;
    private LocalDateTime started;
    private LocalDateTime updated;
    private LocalDateTime restoredTill;
    private StorageRestoreStatus status;
}
