package com.epam.cora.esa.app;

import com.epam.cora.esa.service.ElasticsearchServiceClient;
import com.epam.cora.esa.service.ObjectStorageFileManager;
import com.epam.cora.esa.service.ObjectStorageIndex;
import com.epam.cora.esa.service.impl.CloudPipelineAPIClient;
import com.epam.cora.esa.service.impl.ElasticIndexService;
import com.epam.cora.esa.service.impl.ObjectStorageIndexImpl;
import com.epam.cora.esa.service.impl.S3FileManager;
import com.epam.cora.esa.service.lock.LockService;
import com.epam.cora.entity.datastorage.DataStorageType;
import com.epam.cora.entity.search.SearchDocumentType;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;

@Configuration
public class S3FileSyncConfiguration {

    @Value("${sync.index.common.prefix}")
    private String indexPrefix;
    @Value("${sync.s3-file.index.name}")
    private String indexName;
    @Value("${sync.s3-file.index.mapping}")
    private String indexSettingsPath;
    @Value("${sync.s3-file.index.include.versions:false}")
    private Boolean includeVersions;
    @Value("${sync.s3-file.bulk.insert.size:1000}")
    private Integer bulkInsertSize;
    @Value("${sync.s3-file.bulk.load.tags.size:100}")
    private Integer bulkLoadTagsSize;
    @Value("${sync.s3-file.tag.value.delimiter:;}")
    private String tagDelimiter;
    @Value("${sync.s3-file.storage.ids:}")
    private String storageIds;
    @Value("${sync.s3-file.storage.skip.ids:}")
    private String skipStorageIds;
    @Value("${sync.s3-file.storage.exclude.metadata.key:Billing status}")
    private String storageExcludeKey;
    @Value("${sync.s3-file.storage.exclude.metadata.value:Exclude}")
    private String storageExcludeValue;

    @Bean
    public ObjectStorageFileManager s3FileManager() {
        return new S3FileManager();
    }

    @Bean
    @ConditionalOnProperty(value = "sync.s3-file.disable", matchIfMissing = true, havingValue = "false")
    public ObjectStorageIndex s3FileSynchronizer(
            final CloudPipelineAPIClient apiClient,
            final ElasticsearchServiceClient esClient,
            final ElasticIndexService indexService,
            final @Qualifier("s3FileManager") ObjectStorageFileManager s3FileManager,
            final LockService lockService) {
        final ObjectStorageIndexImpl service = new ObjectStorageIndexImpl(apiClient, esClient, indexService,
                s3FileManager, lockService, indexPrefix + indexName,
                indexSettingsPath, bulkInsertSize, bulkLoadTagsSize,
                DataStorageType.S3,
                SearchDocumentType.S3_FILE,
                tagDelimiter,
                includeVersions,
                storageExcludeKey, storageExcludeValue);
        if (StringUtils.isNotBlank(storageIds)) {
            service.setStorageIds(parseIds(storageIds));
        }
        if (StringUtils.isNotBlank(skipStorageIds)) {
            service.setSkipStorageIds(parseIds(skipStorageIds));
        }
        return service;
    }

    private Set<Long> parseIds(final String storageIds) {
        return Arrays.stream(storageIds.split(","))
                .map(value -> Long.parseLong(value.trim()))
                .collect(Collectors.toSet());
    }

}
