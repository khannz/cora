package com.epam.cora.entity.datastorage;

import com.epam.cora.entity.utils.ProviderUtils;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * An entity, that represents a Data Storage, backed by Amazon S3 bucket
 */
@Getter
@Setter
public class S3bucketDataStorage extends AbstractDataStorage {

    public S3bucketDataStorage() {
        setType(DataStorageType.S3);
    }

    /**
     * ID of AWS region in which bucket is created.
     * If null bucket is assumed to be created in default regions (for backward compatibility only).
     */
    private Long regionId;
    /**
     * A list of allowed CIDR strings, that define access control
     */
    private List<String> allowedCidrs;

    public S3bucketDataStorage(final Long id, final String name, final String path) {
        this(id, name, ProviderUtils.normalizeBucketName(path), DEFAULT_POLICY, "");
    }

    public S3bucketDataStorage(final Long id, final String name, final String path,
                               final StoragePolicy policy, String mountPoint) {
        super(id, name, ProviderUtils.normalizeBucketName(path), DataStorageType.S3, policy, mountPoint);
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
        return String.format("s3://%s", getPath());
    }

    @Override
    public boolean isPolicySupported() {
        return true;
    }

}
