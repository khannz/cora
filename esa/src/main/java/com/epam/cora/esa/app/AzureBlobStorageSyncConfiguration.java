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
        value = "sync.az-blob-storage.disable",
        matchIfMissing = true,
        havingValue = "false"
)
public class AzureBlobStorageSyncConfiguration {

    @Value("${sync.index.common.prefix}")
    private String commonIndexPrefix;

    @Bean
    public DataStorageMapper azStorageMapper() {
        return new DataStorageMapper(SearchDocumentType.AZ_BLOB_STORAGE);
    }

    @Bean
    public DataStorageLoader azStorageLoader(final CloudPipelineAPIClient apiClient) {
        return new DataStorageLoader(apiClient);
    }

    @Bean
    public DataStorageIndexCleaner azEventProcessor(
            final @Value("${sync.az-blob.index.name}") String azFileIndexName,
            final ElasticsearchServiceClient serviceClient) {
        return new DataStorageIndexCleaner(commonIndexPrefix, azFileIndexName, serviceClient);
    }

    @Bean
    public EventToRequestConverterImpl<DataStorageDoc> azEventConverter(
            final @Qualifier("azStorageMapper") DataStorageMapper azStorageMapper,
            final @Qualifier("azStorageLoader") DataStorageLoader azStorageLoader,
            final @Qualifier("azEventProcessor") DataStorageIndexCleaner indexCleaner,
            final @Value("${sync.az-blob-storage.index.name}") String indexName) {
        return new EventToRequestConverterImpl<>(
                commonIndexPrefix, indexName, azStorageLoader, azStorageMapper,
                Collections.singletonList(indexCleaner));
    }

    @Bean
    public EntitySynchronizer dataStorageAzSynchronizer(
            final @Qualifier("azEventConverter") EventToRequestConverterImpl<DataStorageDoc> azEventConverter,
            final PipelineEventDao eventDao,
            final ElasticIndexService indexService,
            final BulkRequestSender requestSender,
            final @Value("${sync.az-blob-storage.index.mapping}") String azStorageMapping,
            final @Value("${sync.load.common.entity.chunk.size:1000}") int chunkSize) {
        return new EntitySynchronizer(eventDao,
                PipelineEvent.ObjectType.AZ,
                azStorageMapping,
                azEventConverter,
                indexService,
                requestSender,
                chunkSize);
    }
}
