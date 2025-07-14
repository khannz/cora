package com.epam.cora.esa.app;

import com.epam.cora.entity.datastorage.DataStorageType;
import com.epam.cora.entity.search.SearchDocumentType;
import com.epam.cora.esa.service.ElasticsearchServiceClient;
import com.epam.cora.esa.service.ObjectStorageFileManager;
import com.epam.cora.esa.service.ObjectStorageIndex;
import com.epam.cora.esa.service.impl.AzureBlobManager;
import com.epam.cora.esa.service.impl.CloudPipelineAPIClient;
import com.epam.cora.esa.service.impl.ElasticIndexService;
import com.epam.cora.esa.service.impl.ObjectStorageIndexImpl;
import com.epam.cora.esa.service.lock.LockService;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AzureFileSyncConfiguration {

    @Value("${sync.index.common.prefix}")
    private String indexPrefix;

    @Value("${sync.az-blob.index.mapping}")
    private String indexSettingsPath;

    @Value("${sync.az-blob.bulk.insert.size:1000}")
    private Integer bulkInsertSize;

    @Value("${sync.az-blob.bulk.load.tags.size:100}")
    private Integer bulkLoadTagsSize;

    @Value("${sync.az-blob.index.name}")
    private String indexName;

    @Value("${sync.az-file.tag.value.delimiter:;}")
    private String tagDelimiter;

    @Value("${sync.az-file.storage.exclude.metadata.key:Billing status}")
    private String storageExcludeKey;

    @Value("${sync.az-file.storage.exclude.metadata.value:Exclude}")
    private String storageExcludeValue;

    @Bean
    public ObjectStorageFileManager azFileManager() {
        return new AzureBlobManager();
    }

    @Bean
    @ConditionalOnProperty(
            value = "sync.az-blob.disable",
            matchIfMissing = true,
            havingValue = "false"
    )
    public ObjectStorageIndex azFileSynchronizer(
            final CloudPipelineAPIClient apiClient,
            final ElasticsearchServiceClient esClient,
            final ElasticIndexService indexService,
            final @Qualifier("azFileManager") ObjectStorageFileManager azFileManager,
            final LockService lockService) {
        return new ObjectStorageIndexImpl(apiClient, esClient, indexService,
                azFileManager, lockService, indexPrefix + indexName,
                indexSettingsPath, bulkInsertSize, bulkLoadTagsSize,
                DataStorageType.AZ,
                SearchDocumentType.AZ_BLOB_FILE,
                tagDelimiter, false,
                storageExcludeKey, storageExcludeValue);
    }
}
