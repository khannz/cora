package com.epam.cora.entity.datastorage;

import com.epam.cora.entity.utils.ProviderUtils;
import lombok.Getter;
import lombok.Setter;

/**
 * Represents a Data Storage, backed by NFS file system
 */
@Getter
@Setter
public class NFSDataStorage extends AbstractDataStorage {

    public NFSDataStorage() {
        setType(DataStorageType.NFS);
    }

    public NFSDataStorage(Long id, String name, String path) {
        super(id, name, normalizeNfsPath(path), DataStorageType.NFS);
    }

    public NFSDataStorage(Long id, String name, String path, StoragePolicy policy, String mountPoint) {
        super(id, name, normalizeNfsPath(path), DataStorageType.NFS, policy, mountPoint);
    }

    @Override
    public String getDelimiter() {
        return ProviderUtils.DELIMITER;
    }

    @Override
    public String getPathMask() {
        return String.format("nfs://%s", getPath());
    }

    @Override
    public boolean isPolicySupported() {
        return false;
    }

    private static String normalizeNfsPath(String path) {
        return path.replaceAll(" ", "-");
    }
}
