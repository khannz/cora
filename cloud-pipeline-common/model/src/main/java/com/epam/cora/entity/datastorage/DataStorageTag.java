package com.epam.cora.entity.datastorage;

import lombok.AllArgsConstructor;
import lombok.Value;
import lombok.With;

import java.time.LocalDateTime;

@Value
@AllArgsConstructor
public class DataStorageTag {

    @With
    DataStorageObject object;

    @With
    String key;

    @With
    String value;

    @With
    LocalDateTime createdDate;

    public DataStorageTag(final DataStorageObject object, final String key, final String value) {
        this(object, key, value, null);
    }
}
