package com.epam.cora.esa.service.impl;

import com.epam.cora.esa.model.PipelineEvent;
import com.epam.cora.esa.service.BulkResponsePostProcessor;
import com.epam.cora.esa.service.ElasticsearchServiceClient;
import com.epam.cora.esa.service.ResponseIdConverter;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.ListUtils;
import org.elasticsearch.action.DocWriteRequest;
import org.elasticsearch.action.bulk.BulkResponse;
import org.springframework.util.ObjectUtils;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Slf4j
@RequiredArgsConstructor
@AllArgsConstructor
public class BulkRequestSender {

    private static final int DEFAULT_BULK_SIZE = 1000;
    private static final int MAX_PARTITION_SIZE = 200;
    private static final int MIN_PARTITION_SIZE = 10;
    private final ElasticsearchServiceClient elasticsearchClient;
    private final BulkResponsePostProcessor responsePostProcessor;

    private ResponseIdConverter idConverter = new ResponseIdConverter() {
    };
    private int currentBulkSize = DEFAULT_BULK_SIZE;

    public BulkRequestSender(final ElasticsearchServiceClient elasticsearchClient, final BulkResponsePostProcessor responsePostProcessor, final ResponseIdConverter idConverter) {
        this(elasticsearchClient, responsePostProcessor);
        this.idConverter = idConverter;
    }

    public void indexDocuments(final String indexName, final PipelineEvent.ObjectType objectType, final List<DocWriteRequest> documentRequests, final LocalDateTime syncStart) {
        indexDocuments(indexName, objectType, documentRequests, syncStart, currentBulkSize);

    }

    public void indexDocuments(final String indexName, final PipelineEvent.ObjectType objectType, final List<DocWriteRequest> documentRequests, final LocalDateTime syncStart, final int bulkSize) {
        indexDocuments(indexName, Collections.singletonList(objectType), documentRequests, syncStart, bulkSize);

    }

    public void indexDocuments(final String indexName, final List<PipelineEvent.ObjectType> objectTypes, final List<DocWriteRequest> documentRequests, final LocalDateTime syncStart) {
        indexDocuments(indexName, objectTypes, documentRequests, syncStart, currentBulkSize);

    }

    @SuppressWarnings("PMD.AvoidCatchingGenericException")
    public void indexDocuments(final String indexName, final List<PipelineEvent.ObjectType> objectTypes, final List<DocWriteRequest> documentRequests, final LocalDateTime syncStart, final int bulkSize) {
        final int partitionSize = Integer.min(MAX_PARTITION_SIZE, Integer.max(MIN_PARTITION_SIZE, bulkSize / 10));
        ListUtils.partition(documentRequests, partitionSize).forEach(chunk -> {
            try {
                indexChunk(indexName, chunk, objectTypes, syncStart);
            } catch (Exception e) {
                log.error("Partial error during {} index sync: {}.", indexName, e.getMessage());
            }
        });
    }

    private void indexChunk(final String indexName, final List<DocWriteRequest> documentRequests, final List<PipelineEvent.ObjectType> objectTypes, final LocalDateTime syncStart) {
        final List<DocWriteRequest> docs = ListUtils.emptyIfNull(documentRequests).stream().filter(Objects::nonNull).collect(Collectors.toList());
        if (docs.isEmpty()) {
            return;
        }
        log.debug("Inserting {} documents for {}", docs.size(), objectTypes);
        final BulkResponse response = elasticsearchClient.sendRequests(indexName, docs);

        if (ObjectUtils.isEmpty(response)) {
            log.error("Elasticsearch documents for {} were not created.", objectTypes);
            return;
        }
        Arrays.stream(response.getItems()).collect(Collectors.groupingBy(idConverter::getId)).forEach((id, items) -> responsePostProcessor.postProcessResponse(items, objectTypes, id, syncStart));
    }
}
