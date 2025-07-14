package com.epam.cora.entity.datastorage;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DataStorageItemContent {
    private byte[] content;
    private String contentType;
    private boolean truncated = false;
    private boolean mayBeBinary = false;
}
