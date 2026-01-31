package com.epam.cora.esa.service.impl.converter.storage;

import com.epam.cora.esa.model.EventType;
import com.epam.cora.esa.model.PipelineEvent;
import com.epam.cora.esa.service.ElasticsearchServiceClient;
import com.epam.cora.esa.service.EventProcessor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.opensearch.ElasticsearchException;
import org.springframework.util.StringUtils;

@RequiredArgsConstructor
@Slf4j
public class DataStorageIndexCleaner implements EventProcessor {

    private final String indexPrefix;
    private final String storageFileIndexName;
    private final ElasticsearchServiceClient elasticsearchClient;

    @Override
    public void process(final PipelineEvent event) {
        if (event.getEventType() != EventType.DELETE) {
            return;
        }
        deleteCorrespondingStorageIndex(event.getObjectId());
    }

    private void deleteCorrespondingStorageIndex(final Long storageId) {
        String indexAlias = getIndexAlias(storageId);
        if (!StringUtils.hasText(indexAlias)) {
            return;
        }
        try {
            String indexNameByAlias = elasticsearchClient.getIndexNameByAlias(indexAlias);
            if (StringUtils.hasText(indexNameByAlias)) {
                elasticsearchClient.deleteIndex(indexNameByAlias);
            }
        } catch (ElasticsearchException e) {
            log.warn("Failed to delete index for storage {}", storageId);
        }
    }

    private String getIndexAlias(final Long storageId) {
        return indexPrefix + storageFileIndexName + String.format("-%d", storageId);
    }
}
