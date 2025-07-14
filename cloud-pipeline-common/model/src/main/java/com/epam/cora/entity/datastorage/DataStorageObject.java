package com.epam.cora.entity.datastorage;

import lombok.AllArgsConstructor;
import lombok.Value;
import lombok.With;

@Value
@AllArgsConstructor
public class DataStorageObject {

    @With
    String path;

    @With
    String version;

    public DataStorageObject(final String path) {
        this(path, null);
    }
}
