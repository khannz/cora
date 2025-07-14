package com.epam.cora.esa.app;

import com.epam.cora.entity.search.SearchDocumentType;
import com.epam.cora.esa.dao.PipelineEventDao;
import com.epam.cora.esa.model.DataStorageDoc;
import com.epam.cora.esa.model.PipelineEvent;
import com.epam.cora.esa.service.ElasticsearchServiceClient;
import com.epam.cora.esa.service.impl.BulkRequestSender;
import com.epam.cora.esa.service.impl.CloudPipelineAPIClient;
import com.epam.cora.esa.service.impl.ElasticIndexService;
import com.epam.cora.esa.service.impl.EntitySynchronizer;
import com.epam.cora.esa.service.impl.converter.EventToRequestConverterImpl;
import com.epam.cora.esa.service.impl.converter.storage.DataStorageIndexCleaner;
import com.epam.cora.esa.service.impl.converter.storage.DataStorageLoader;
import com.epam.cora.esa.service.impl.converter.storage.DataStorageMapper;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Collections;

@Configuration
@ConditionalOnProperty(
        value = "sync.s3-storage.disable",
        matchIfMissing = true,
        havingValue = "false"
)
public class S3StorageSyncConfiguration {

    @Value("${sync.index.common.prefix}")
    private String commonIndexPrefix;

    @Bean
    public DataStorageMapper s3StorageMapper() {
        return new DataStorageMapper(SearchDocumentType.S3_STORAGE);
    }

    @Bean
    public DataStorageLoader s3StorageLoader(final CloudPipelineAPIClient apiClient) {
        return new DataStorageLoader(apiClient);
    }

    @Bean
    public DataStorageIndexCleaner s3EventProcessor(final @Value("${sync.s3-file.index.name}") String s3FileIndexName, final ElasticsearchServiceClient serviceClient) {
        return new DataStorageIndexCleaner(commonIndexPrefix, s3FileIndexName, serviceClient);
    }

    @Bean
    public EventToRequestConverterImpl<DataStorageDoc> s3EventConverter(final @Qualifier("s3StorageMapper") DataStorageMapper s3StorageMapper, final @Qualifier("s3StorageLoader") DataStorageLoader s3StorageLoader, final @Qualifier("s3EventProcessor") DataStorageIndexCleaner indexCleaner, final @Value("${sync.s3-storage.index.name}") String indexName) {
        return new EventToRequestConverterImpl<>(commonIndexPrefix, indexName, s3StorageLoader, s3StorageMapper, Collections.singletonList(indexCleaner));
    }

    @Bean
    public EntitySynchronizer dataStorageS3Synchronizer(final @Qualifier("s3EventConverter") EventToRequestConverterImpl<DataStorageDoc> s3EventConverter, final PipelineEventDao eventDao, final ElasticIndexService indexService, final BulkRequestSender requestSender, final @Value("${sync.s3-storage.index.mapping}") String s3StorageMapping, final @Value("${sync.load.common.entity.chunk.size:1000}") int chunkSize) {
        return new EntitySynchronizer(eventDao, PipelineEvent.ObjectType.S3, s3StorageMapping, s3EventConverter, indexService, requestSender, chunkSize);
    }
}
