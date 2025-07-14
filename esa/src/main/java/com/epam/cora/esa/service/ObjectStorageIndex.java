package com.epam.cora.esa.service;

import com.epam.cora.entity.datastorage.AbstractDataStorage;
import com.epam.cora.entity.datastorage.DataStorageType;
import com.epam.cora.entity.search.SearchDocumentType;

/**
 * Provides common interface for indexing of files in some {@code AbstractDataStorage}
 */
public interface ObjectStorageIndex extends ElasticsearchSynchronizer {

    DataStorageType getStorageType();

    SearchDocumentType getDocumentType();

    void indexStorage(AbstractDataStorage dataStorage);
}
