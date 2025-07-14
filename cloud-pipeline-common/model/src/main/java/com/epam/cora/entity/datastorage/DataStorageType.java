package com.epam.cora.entity.datastorage;

import com.epam.cora.entity.region.CloudProvider;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.*;
import java.util.stream.Collectors;

@Getter
@RequiredArgsConstructor
public enum DataStorageType {
    S3(
            "S3",
            new HashSet<>(Arrays.asList(Constants.STANDARD_STORAGE_CLASS,
                    "GLACIER", "GLACIER_IR", "DEEP_ARCHIVE"))
    ),
    NFS("NFS", Collections.singleton(Constants.STANDARD_STORAGE_CLASS)),
    AZ("AZ", Collections.singleton(Constants.STANDARD_STORAGE_CLASS)),
    GS("GS", Collections.singleton(Constants.STANDARD_STORAGE_CLASS)),
    AWS_OMICS_REF("AWS_OMICS_REF", Collections.singleton(Constants.STANDARD_STORAGE_CLASS)),
    AWS_OMICS_SEQ("AWS_OMICS_SEQ", Collections.singleton(Constants.STANDARD_STORAGE_CLASS));

    private final String id;
    private final Set<String> storageClasses;
    private static final Map<String, DataStorageType> ID_MAP;
    private static final Map<String, DataStorageType> NAMES_MAP;

    static {
        ID_MAP = Arrays.stream(values()).collect(Collectors.toMap(v -> v.id, v -> v));
        NAMES_MAP = Arrays.stream(values()).collect(Collectors.toMap(Enum::name, v -> v));
    }

    public static DataStorageType fromServiceType(final CloudProvider provider,
                                                  final StorageServiceType serviceType) {
        return switch (serviceType) {
            case FILE_SHARE -> NFS;
            case OBJECT_STORAGE -> getObjectStorageType(provider);
            case AWS_OMICS_REF -> AWS_OMICS_REF;
            case AWS_OMICS_SEQ -> AWS_OMICS_SEQ;
            default -> throw new IllegalArgumentException("Unsupported service " + serviceType);
        };
    }

    public static DataStorageType getById(String id) {
        if (id == null) {
            return null;
        }
        return ID_MAP.get(id.toUpperCase());
    }

    public static DataStorageType getByName(String name) {
        if (name == null) {
            return null;
        }
        return NAMES_MAP.get(name);
    }
//
//    public String getId() {
//        return this.id;
//    }

    private static DataStorageType getObjectStorageType(final CloudProvider provider) {
        return switch (provider) {
            case AWS -> S3;
            case AZURE -> AZ;
            case GCP -> GS;
            default -> throw new IllegalArgumentException("Unsupported provider for object storage: " + provider);
        };
    }

    private static class Constants {
        public static final String STANDARD_STORAGE_CLASS = "STANDARD";
    }
}
