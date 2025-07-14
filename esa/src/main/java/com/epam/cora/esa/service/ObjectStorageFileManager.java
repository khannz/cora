package com.epam.cora.esa.service;

import com.epam.cora.entity.datastorage.DataStorageFile;
import com.epam.cora.entity.datastorage.DataStorageType;
import com.epam.cora.entity.datastorage.TemporaryCredentials;

import java.io.InputStream;
import java.util.function.Supplier;
import java.util.stream.Stream;

/**
 * Lists all files in specified {@code AbstractDataStorage}
 */
public interface ObjectStorageFileManager {

    DataStorageType getType();

    Stream<DataStorageFile> files(String storage, String path, Supplier<TemporaryCredentials> credentialsSupplier);

    Stream<DataStorageFile> versions(String storage, String path, Supplier<TemporaryCredentials> credentialsSupplier, boolean showDeleted);

    Stream<DataStorageFile> versionsWithNativeTags(String storage, String path, Supplier<TemporaryCredentials> credentialsSupplier);

    default InputStream readFileContent(String storage, String path, Supplier<TemporaryCredentials> credentialsSupplier) {
        throw new UnsupportedOperationException();
    }

    default void deleteFile(String storage, String path, Supplier<TemporaryCredentials> credentialsSupplier) {
        throw new UnsupportedOperationException();
    }
}
