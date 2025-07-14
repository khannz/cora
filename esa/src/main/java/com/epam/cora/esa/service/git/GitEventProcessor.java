package com.epam.cora.esa.service.git;

import com.epam.cora.entity.pipeline.Pipeline;
import com.epam.cora.esa.model.git.GitEvent;
import com.epam.cora.esa.model.git.GitEventType;
import com.epam.cora.esa.service.impl.CloudPipelineAPIClient;
import com.epam.cora.exception.PipelineResponseException;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Optional;

public interface GitEventProcessor {

    Logger LOG = LoggerFactory.getLogger(GitEventProcessor.class);

    GitEventType supportedEventType();

    void processEvent(GitEvent event);

    default boolean validateEvent(GitEvent event) {
        if (supportedEventType() != event.getEventType()) {
            LOG.error("Not supported event type: {}", event.getEventType());
            return false;
        }
        if (event.getProject() == null || StringUtils.isBlank(event.getProject().getRepositoryUrl())) {
            LOG.error("Project is not specified for event");
            return false;
        }
        return true;
    }

    default Optional<Pipeline> fetchPipeline(final GitEvent event, final CloudPipelineAPIClient apiClient) {
        try {
            return Optional.of(apiClient.loadPipelineByRepositoryUrl(event.getProject().getRepositoryUrl()));
        } catch (PipelineResponseException e) {
            LOG.error("An error during pipeline fetch: {}", e.getMessage());
            return Optional.empty();
        }
    }
}
