package com.epam.cora.esa.service.git;

import com.epam.cora.esa.dao.PipelineEventDao;
import com.epam.cora.esa.model.EventType;
import com.epam.cora.esa.model.PipelineEvent;
import com.epam.cora.esa.model.git.GitEvent;
import com.epam.cora.esa.model.git.GitEventData;
import com.epam.cora.esa.model.git.GitEventType;
import com.epam.cora.esa.service.impl.CloudPipelineAPIClient;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
@Slf4j
public class GitTagEventProcessor implements GitEventProcessor {

    private static final String TAG_PREFIX = "refs/tags/";
    private final PipelineEventDao eventDao;
    private final CloudPipelineAPIClient apiClient;
    private final ObjectMapper objectMapper;

    @Override
    public GitEventType supportedEventType() {
        return GitEventType.tag_push;
    }

    @Override
    public void processEvent(final GitEvent event) {
        if (!validateEvent(event)) {
            return;
        }
        log.debug("Processing event: {}", event);

        fetchPipeline(event, apiClient).ifPresent(pipeline -> {
            final GitEventData eventData = GitEventData.builder().gitEventType(supportedEventType()).version(fetchVersion(event)).build();
            final PipelineEvent pipelineEvent;
            try {
                pipelineEvent = PipelineEvent.builder().objectType(PipelineEvent.ObjectType.PIPELINE_CODE).eventType(fetchPipelineEventType(event)).createdDate(LocalDateTime.now()).objectId(pipeline.getId()).data(objectMapper.writeValueAsString(eventData)).build();
                eventDao.createPipelineEvent(pipelineEvent);
            } catch (JsonProcessingException e) {
                log.error(e.getMessage(), e);
            }
        });
    }

    private EventType fetchPipelineEventType(final GitEvent event) {
        return CollectionUtils.isEmpty(event.getCommits()) ? EventType.DELETE : EventType.INSERT;
    }

    private String fetchVersion(final GitEvent event) {
        return event.getReference().replace(TAG_PREFIX, StringUtils.EMPTY);
    }
}
