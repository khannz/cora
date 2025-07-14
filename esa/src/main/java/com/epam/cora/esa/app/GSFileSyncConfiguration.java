package com.epam.cora.esa.app;

import com.epam.cora.entity.datastorage.DataStorageType;
import com.epam.cora.entity.search.SearchDocumentType;
import com.epam.cora.esa.service.ElasticsearchServiceClient;
import com.epam.cora.esa.service.ObjectStorageFileManager;
import com.epam.cora.esa.service.ObjectStorageIndex;
import com.epam.cora.esa.service.impl.CloudPipelineAPIClient;
import com.epam.cora.esa.service.impl.ElasticIndexService;
import com.epam.cora.esa.service.impl.GsBucketFileManager;
import com.epam.cora.esa.service.impl.ObjectStorageIndexImpl;
import com.epam.cora.esa.service.lock.LockService;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class GSFileSyncConfiguration {

    @Value("${sync.index.common.prefix}")
    private String indexPrefix;

    @Value("${sync.gs-file.index.name}")
    private String indexName;

    @Value("${sync.gs-file.index.mapping}")
    private String indexSettingsPath;

    @Value("${sync.gs-file.bulk.insert.size:1000}")
    private Integer bulkInsertSize;

    @Value("${sync.gs-file.bulk.load.tags.size:100}")
    private Integer bulkLoadTagsSize;

    @Value("${sync.gs-file.tag.value.delimiter:;}")
    private String tagDelimiter;

    @Value("${sync.gs-file.storage.exclude.metadata.key:Billing status}")
    private String storageExcludeKey;

    @Value("${sync.gs-file.storage.exclude.metadata.value:Exclude}")
    private String storageExcludeValue;

    @Bean
    public ObjectStorageFileManager gsFileManager() {
        return new GsBucketFileManager();
    }

    @Bean
    @ConditionalOnProperty(
            value = "sync.gs-file.disable",
            matchIfMissing = true,
            havingValue = "false"
    )
    public ObjectStorageIndex gsFileSynchronizer(
            final CloudPipelineAPIClient apiClient,
            final ElasticsearchServiceClient esClient,
            final ElasticIndexService indexService,
            final @Qualifier("gsFileManager") ObjectStorageFileManager gsFileManager,
            final LockService lockService) {
        return new ObjectStorageIndexImpl(apiClient, esClient, indexService,
                gsFileManager, lockService, indexPrefix + indexName,
                indexSettingsPath, bulkInsertSize, bulkLoadTagsSize,
                DataStorageType.GS,
                SearchDocumentType.GS_FILE,
                tagDelimiter, false,
                storageExcludeKey, storageExcludeValue);
    }

}
