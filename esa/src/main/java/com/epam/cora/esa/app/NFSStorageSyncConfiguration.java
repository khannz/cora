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
        value = "sync.nfs-storage.disable",
        matchIfMissing = true,
        havingValue = "false"
)
public class NFSStorageSyncConfiguration {

    @Value("${sync.index.common.prefix}")
    private String commonIndexPrefix;

    @Bean
    public DataStorageMapper nfsStorageMapper() {
        return new DataStorageMapper(SearchDocumentType.NFS_STORAGE);
    }

    @Bean
    public DataStorageLoader nfsStorageLoader(final CloudPipelineAPIClient apiClient) {
        return new DataStorageLoader(apiClient);
    }

    @Bean
    public DataStorageIndexCleaner nfsStorageIndexCleaner(final @Value("${sync.nfs-file.index.name}") String nfsFileIndexName, final ElasticsearchServiceClient serviceClient) {
        return new DataStorageIndexCleaner(commonIndexPrefix, nfsFileIndexName, serviceClient);
    }

    @Bean
    public EventToRequestConverterImpl<DataStorageDoc> nfsEventConverter(final @Qualifier("nfsStorageMapper") DataStorageMapper nfsStorageMapper, final @Qualifier("nfsStorageLoader") DataStorageLoader nfsStorageLoader, final @Qualifier("nfsStorageIndexCleaner") DataStorageIndexCleaner nfsStorageIndexCleaner, final @Value("${sync.nfs-storage.index.name}") String indexName) {
        return new EventToRequestConverterImpl<>(commonIndexPrefix, indexName, nfsStorageLoader, nfsStorageMapper, Collections.singletonList(nfsStorageIndexCleaner));
    }

    @Bean
    public EntitySynchronizer dataStorageNfsSynchronizer(final @Qualifier("nfsEventConverter") EventToRequestConverterImpl<DataStorageDoc> nfsEventConverter, final PipelineEventDao eventDao, final ElasticIndexService indexService, final BulkRequestSender requestSender, final @Value("${sync.nfs-storage.index.mapping}") String nfsStorageMapping, final @Value("${sync.load.common.entity.chunk.size:1000}") int chunkSize) {
        return new EntitySynchronizer(eventDao, PipelineEvent.ObjectType.NFS, nfsStorageMapping, nfsEventConverter, indexService, requestSender, chunkSize);
    }
}
