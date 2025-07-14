package com.epam.cora.entity.datastorage;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

/**
 * An entity, that represents a Data Storage and {@link FileShareMount} if it's exists for current data storage.
 */
@Getter
@Setter
@AllArgsConstructor
public class DataStorageWithShareMount {
    private AbstractDataStorage storage;
    private FileShareMount shareMount;
}

