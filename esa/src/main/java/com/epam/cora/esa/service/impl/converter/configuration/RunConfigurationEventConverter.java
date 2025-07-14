package com.epam.cora.esa.service.impl.converter.configuration;

import com.epam.cora.entity.configuration.RunConfiguration;
import com.epam.cora.esa.exception.EntityNotFoundException;
import com.epam.cora.esa.model.EventType;
import com.epam.cora.esa.model.PipelineEvent;
import com.epam.cora.esa.service.ElasticsearchServiceClient;
import com.epam.cora.esa.service.EventToRequestConverter;
import com.epam.cora.esa.service.impl.ElasticIndexService;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.collections4.ListUtils;
import org.elasticsearch.action.DocWriteRequest;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

/**
 * {@link RunConfiguration} is indexed as a set od documents - one for each entry.
 * If any event is received for configuration, all related docs are deleted and then
 * inserted again, since we do not exactly know - which entries were removed, added or updated.
 */
@Slf4j
@Getter
@RequiredArgsConstructor
public class RunConfigurationEventConverter implements EventToRequestConverter {

    private static final String WILDCARD = "*";
    private final String indexPrefix;
    private final String indexName;
    private final RunConfigurationLoader configurationLoader;
    private final RunConfigurationDocumentBuilder docBuilder;
    private final ElasticsearchServiceClient elasticsearchClient;
    private final ElasticIndexService indexService;

    @Override
    public List<DocWriteRequest> convertEventsToRequest(final List<PipelineEvent> events, final String indexName) {
        return ListUtils.emptyIfNull(events).stream().map(event -> getDocWriteRequest(indexName, event)).filter(CollectionUtils::isNotEmpty).flatMap(Collection::stream).collect(Collectors.toList());
    }

    private List<DocWriteRequest> getDocWriteRequest(final String indexName, final PipelineEvent event) {
        final List<DocWriteRequest> deleteEntriesRequests = indexService.getDeleteRequests(String.valueOf(event.getObjectId()), indexName);
        if (event.getEventType() == EventType.DELETE) {
            return deleteEntriesRequests;
        }
        final List<DocWriteRequest> insertEntryRequests = getInsertEntryRequests(indexName, event);
        return ListUtils.union(deleteEntriesRequests, insertEntryRequests);
    }

    private List<DocWriteRequest> getInsertEntryRequests(final String indexName, final PipelineEvent event) {
        try {
            return configurationLoader.loadEntity(event.getObjectId()).map(configuration -> docBuilder.createDocsForConfiguration(indexName, configuration)).orElse(Collections.emptyList());
        } catch (EntityNotFoundException e) {
            log.error("Failed to load run configuration by ID {}. {}", event.getObjectId(), e.getMessage());
            return Collections.emptyList();
        }
    }
}
