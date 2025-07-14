package com.epam.cora.entity.datastorage;

import com.epam.cora.entity.utils.ProviderUtils;
import lombok.Getter;
import lombok.Setter;

/**
 * An entity, that represents a Data Storage, backed by Amazon S3 bucket
 */
@Getter
@Setter
public class AWSOmicsSeqStorage extends AbstractDataStorage {

    public AWSOmicsSeqStorage() {
        setType(DataStorageType.AWS_OMICS_SEQ);
    }

    /**
     * Id of AWS region in which omics store is created.
     */
    private Long regionId;

    public AWSOmicsSeqStorage(final Long id, final String name, final String path) {
        this(id, name, ProviderUtils.normalizeBucketName(path), DEFAULT_POLICY, "");
    }

    public AWSOmicsSeqStorage(final Long id, final String name, final String path, final StoragePolicy policy, String mountPoint) {
        super(id, name, ProviderUtils.normalizeBucketName(path), DataStorageType.AWS_OMICS_SEQ, policy, mountPoint);
    }

    @Override
    public String getMountOptions() {
        return "";
    }

    @Override
    public String getDelimiter() {
        return ProviderUtils.DELIMITER;
    }

    @Override
    public String getPathMask() {
        return String.format("omics://%s", getPath());
    }

    @Override
    public boolean isPolicySupported() {
        return false;
    }
}
