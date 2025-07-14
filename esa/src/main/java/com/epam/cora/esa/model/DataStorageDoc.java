package com.epam.cora.esa.model;

import com.epam.cora.entity.datastorage.AbstractDataStorage;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class DataStorageDoc {
    private AbstractDataStorage storage;
    private String regionName;
}
