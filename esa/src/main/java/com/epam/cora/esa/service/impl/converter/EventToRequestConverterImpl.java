package com.epam.cora.esa.service.impl.converter;

import com.epam.cora.esa.exception.EntityNotFoundException;
import com.epam.cora.esa.model.EntityContainer;
import com.epam.cora.esa.model.EventType;
import com.epam.cora.esa.model.PipelineEvent;
import com.epam.cora.esa.service.EventToRequestConverter;
import com.epam.cora.esa.service.EntityLoader;
import com.epam.cora.esa.service.EntityMapper;
import com.epam.cora.esa.service.EventProcessor;
import lombok.Data;
import org.apache.commons.collections4.ListUtils;
import org.opensearch.action.DocWriteRequest;
import org.opensearch.action.index.IndexRequest;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Data
public class EventToRequestConverterImpl<T> implements EventToRequestConverter {

    private final String indexPrefix;
    private final String indexName;
    private final EntityLoader<T> loader;
    private final EntityMapper<T> mapper;
    private final List<EventProcessor> additionalProcessors;

    public EventToRequestConverterImpl(final String indexPrefix,
                                       final String indexName,
                                       final EntityLoader<T> loader,
                                       final EntityMapper<T> mapper) {
        this(indexPrefix, indexName, loader, mapper, Collections.emptyList());
    }

    public EventToRequestConverterImpl(final String indexPrefix,
                                       final String indexName,
                                       final EntityLoader<T> loader,
                                       final EntityMapper<T> mapper,
                                       final List<EventProcessor> additionalProcessors) {
        this.indexPrefix = indexPrefix;
        this.indexName = indexName;
        this.loader = loader;
        this.mapper = mapper;
        this.additionalProcessors = Collections.unmodifiableList(additionalProcessors);
    }

    @Override
    public List<DocWriteRequest> convertEventsToRequest(final List<PipelineEvent> events,
                                                        final String indexName) {
        return ListUtils.emptyIfNull(events)
                .stream()
                .map(event -> getDocWriteRequest(indexName, event))
                .filter(Optional::isPresent)
                .map(Optional::get)
                .collect(Collectors.toList());
    }

    private Optional<DocWriteRequest> getDocWriteRequest(final String indexName,
                                                         final PipelineEvent event) {
        ListUtils.emptyIfNull(additionalProcessors).forEach(p -> p.process(event));
        if (event.getEventType() == EventType.DELETE) {
            return Optional.of(createDeleteRequest(event, indexName));
        }
        try {
            Optional<EntityContainer<T>> entity = loader.loadEntity(event.getObjectId());
            return entity
                    .map(ds -> new IndexRequest(indexName, INDEX_TYPE, String.valueOf(event.getObjectId()))
                            .source(mapper.map(ds)));
        } catch (EntityNotFoundException e) {
            return Optional.of(createDeleteRequest(event, indexName));
        }
    }

}
