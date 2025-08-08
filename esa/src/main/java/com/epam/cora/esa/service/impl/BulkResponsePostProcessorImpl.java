package com.epam.cora.esa.service.impl;

import com.epam.cora.esa.dao.PipelineEventDao;
import com.epam.cora.esa.model.PipelineEvent;
import com.epam.cora.esa.service.BulkResponsePostProcessor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.opensearch.action.bulk.BulkItemResponse;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Component
@Slf4j
public class BulkResponsePostProcessorImpl implements BulkResponsePostProcessor {

    private final PipelineEventDao pipelineEventDao;

    @Override
    public void postProcessResponse(final List<BulkItemResponse> items, final List<PipelineEvent.ObjectType> objectTypes, final Long entityId, final LocalDateTime syncStart) {
        final List<BulkItemResponse> failedItems = items.stream().filter(BulkItemResponse::isFailed).collect(Collectors.toList());
        if (CollectionUtils.isNotEmpty(failedItems)) {
            failedItems.forEach(item -> log.error("Failed to index item {}: {}", item.getId(), item.getFailureMessage()));
        } else {
            objectTypes.forEach(type -> pipelineEventDao.deleteEventByObjectId(entityId, type, syncStart));
        }
    }
}
