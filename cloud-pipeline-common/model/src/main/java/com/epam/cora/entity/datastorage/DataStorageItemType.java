package com.epam.cora.entity.datastorage;

import java.util.HashMap;
import java.util.Map;

public enum DataStorageItemType {
    Folder(0), File(1);

    private long id;
    private static Map<Long, DataStorageItemType> idMap = new HashMap<>();

    static {
        idMap.put(Folder.id, Folder);
        idMap.put(File.id, File);
    }

    private static Map<String, DataStorageItemType> namesMap = new HashMap<>();

    static {
        namesMap.put(Folder.name(), Folder);
        namesMap.put(File.name(), File);
    }

    DataStorageItemType(long id) {
        this.id = id;
    }

    public static DataStorageItemType getById(Long id) {
        if (id == null) {
            return null;
        }
        return idMap.get(id);
    }

    public static DataStorageItemType getByName(String name) {
        if (name == null) {
            return null;
        }
        return namesMap.get(name);
    }
}
