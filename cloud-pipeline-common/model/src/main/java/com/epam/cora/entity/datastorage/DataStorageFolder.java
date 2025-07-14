package com.epam.cora.entity.datastorage;

import org.springframework.util.Assert;

import java.io.File;

public class DataStorageFolder extends AbstractDataStorageItem {

    public DataStorageFolder() {
        super();
        this.setType(DataStorageItemType.Folder);
    }

    public DataStorageFolder(String path, File directory) {
        super();

        Assert.isTrue(directory.isDirectory(), "Supplied file is not a directory");
        setName(directory.getName());
        setPath(path);
    }
}
