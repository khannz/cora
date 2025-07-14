package com.epam.cora.entity.search;

import lombok.Data;

import java.util.Set;

@Data
public class StorageFileSearchMask {

    private final String storageName;
    private final Set<String> hiddenFilePathGlobs;
    private final Set<String> indexedContentPathGlobs;
}
