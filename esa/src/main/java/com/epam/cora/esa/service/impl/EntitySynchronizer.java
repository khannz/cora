package com.epam.cora.esa.service.impl;

import com.epam.cora.esa.dao.PipelineEventDao;
import com.epam.cora.esa.model.PipelineEvent;
import com.epam.cora.esa.service.ElasticsearchSynchronizer;
import com.epam.cora.esa.service.EventToRequestConverter;
import com.epam.cora.esa.utils.EventProcessorUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.elasticsearch.action.DocWriteRequest;

import java.time.LocalDateTime;
import java.util.List;

@RequiredArgsConstructor
@Slf4j
public class EntitySynchronizer implements ElasticsearchSynchronizer {

    private final PipelineEventDao pipelineEventDao;
    private final PipelineEvent.ObjectType objectType;
    private final String indexMappingFile;
    private final EventToRequestConverter converter;
    private final ElasticIndexService indexService;
    private final BulkRequestSender bulkRequestSender;
    private final int chunkSize;
    private final int sendRequestChunkSize;

    public EntitySynchronizer(final PipelineEventDao pipelineEventDao, final PipelineEvent.ObjectType objectType,
                              final String indexMappingFile, final EventToRequestConverter converter,
                              final ElasticIndexService indexService, final BulkRequestSender bulkRequestSender,
                              final int chunkSize) {
        this(pipelineEventDao, objectType, indexMappingFile, converter, indexService, bulkRequestSender,
                chunkSize, chunkSize);
    }

    @Override
    @SuppressWarnings("PMD.AvoidCatchingGenericException")
    public void synchronize(final LocalDateTime lastSyncTime, final LocalDateTime syncStart) {
        try {
            log.debug("Starting to synchronize {} entities", objectType);
            final List<PipelineEvent> pipelineEvents = pipelineEventDao
                    .loadPipelineEventsByObjectType(objectType, syncStart, chunkSize);
            log.debug("Loaded {} events for {}", pipelineEvents.size(), objectType);
            final List<PipelineEvent> mergeEvents = EventProcessorUtils.mergeEvents(pipelineEvents);
            if (mergeEvents.isEmpty()) {
                log.debug("{} entities for synchronization were not found.", objectType);
                return;
            }

            log.debug("Merged {} events for {}", mergeEvents.size(), objectType);

            final String indexName = converter.buildIndexName();
            indexService.createIndexIfNotExist(indexName, indexMappingFile);

            final List<DocWriteRequest> documentRequests = converter.convertEventsToRequest(mergeEvents, indexName);
            if (CollectionUtils.isEmpty(documentRequests)) {
                log.debug("No index requests created for {}", objectType);
                return;
            }
            log.debug("Creating {} requests for {} entity.", documentRequests.size(), objectType);
            bulkRequestSender.indexDocuments(indexName, objectType, documentRequests, syncStart, sendRequestChunkSize);
            log.debug("Successfully finished {} synchronization.", objectType);
        } catch (Exception e) {
            log.error("An error during {} synchronization: {}", objectType, e.getMessage());
            log.error(e.getMessage(), e);
        }
    }
}
