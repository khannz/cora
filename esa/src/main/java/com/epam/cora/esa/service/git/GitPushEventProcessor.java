package com.epam.cora.esa.service.git;

import com.epam.cora.esa.dao.PipelineEventDao;
import com.epam.cora.esa.model.EventType;
import com.epam.cora.esa.model.PipelineEvent;
import com.epam.cora.esa.model.git.GitCommit;
import com.epam.cora.esa.model.git.GitEvent;
import com.epam.cora.esa.model.git.GitEventData;
import com.epam.cora.esa.model.git.GitEventType;
import com.epam.cora.esa.service.impl.CloudPipelineAPIClient;
import com.epam.cora.esa.utils.EventProcessorUtils;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.collections4.ListUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Getter
@Service
@Slf4j
public class GitPushEventProcessor implements GitEventProcessor {

    private static final String DELIMITER = "/";
    private final PipelineEventDao eventDao;
    private final CloudPipelineAPIClient apiClient;
    private final ObjectMapper objectMapper;
    private final String defaultBranch;
    private final List<String> indexedPaths;

    public GitPushEventProcessor(final PipelineEventDao eventDao, final CloudPipelineAPIClient apiClient, final ObjectMapper objectMapper, @Value("${sync.pipeline-code.default-branch}") final String defaultBranch, @Value("${sync.pipeline-code.index.paths}") final String indexedPaths) {
        this.eventDao = eventDao;
        this.apiClient = apiClient;
        this.objectMapper = objectMapper;
        this.defaultBranch = defaultBranch.trim();
        this.indexedPaths = EventProcessorUtils.splitOnPaths(indexedPaths);
    }

    @Override
    public GitEventType supportedEventType() {
        return GitEventType.push;
    }

    @Override
    public void processEvent(final GitEvent event) {
        if (!validateEvent(event)) {
            return;
        }
        log.debug("Processing git event: {}", event);
        if (!defaultBranch.equals(event.getReference())) {
            log.debug("Skipping non default branch: {}", event.getReference());
            return;
        }
        fetchPipeline(event, apiClient).ifPresent(pipeline -> ListUtils.emptyIfNull(event.getCommits()).stream().map(commit -> {
            final List<PipelineEvent> events = new ArrayList<>();
            final List<String> updated = ListUtils.union(filterPaths(commit.getAdded()), filterPaths(commit.getModified()));
            buildEvent(commit, EventType.UPDATE, updated, pipeline.getId()).ifPresent(events::add);
            final List<String> deleted = filterPaths(commit.getRemoved());
            buildEvent(commit, EventType.DELETE, deleted, pipeline.getId()).ifPresent(events::add);
            log.debug("Processing events: {}", events);
            return events;
        }).filter(CollectionUtils::isNotEmpty).flatMap(Collection::stream).forEach(eventDao::createPipelineEvent));
    }

    private List<String> filterPaths(final List<String> paths) {
        return ListUtils.emptyIfNull(paths).stream().filter(this::matchIndexPaths).collect(Collectors.toList());
    }

    private boolean matchIndexPaths(final String path) {
        return indexedPaths.stream().anyMatch(indexedPath -> {
            if (indexedPath.endsWith(DELIMITER)) {
                return path.startsWith(indexedPath);
            } else {
                return path.equals(indexedPath);
            }
        });
    }

    private Optional<PipelineEvent> buildEvent(final GitCommit commit, final EventType eventType, final List<String> paths, final Long pipelineId) {
        if (CollectionUtils.isEmpty(paths)) {
            return Optional.empty();
        }
        final GitEventData data = GitEventData.builder().gitEventType(supportedEventType()).version(defaultBranch).paths(paths).build();
        final PipelineEvent pipelineEvent;
        try {
            pipelineEvent = PipelineEvent.builder().createdDate(commit.getTimestamp()).eventType(eventType).objectType(PipelineEvent.ObjectType.PIPELINE_CODE).data(objectMapper.writeValueAsString(data)).objectId(pipelineId).build();
            return Optional.of(pipelineEvent);
        } catch (JsonProcessingException e) {
            log.error(e.getMessage(), e);
            return Optional.empty();
        }
    }
}
