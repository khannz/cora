package com.epam.cora.entity.datastorage;

import com.epam.cora.entity.utils.ProviderUtils;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class GSBucketStorage extends AbstractDataStorage {

    private Long regionId;

    public GSBucketStorage() {
        setType(DataStorageType.GS);
    }

    public GSBucketStorage(final Long id, final String name, final String path, final StoragePolicy policy, final String mountPoint) {
        super(id, name, ProviderUtils.normalizeBucketName(path), DataStorageType.GS, policy, mountPoint);
    }

    @Override
    public String getDelimiter() {
        return ProviderUtils.DELIMITER;
    }

    @Override
    public String getPathMask() {
        return String.format("gs://%s", getPath());
    }

    @Override
    public boolean isPolicySupported() {
        return true;
    }
}
