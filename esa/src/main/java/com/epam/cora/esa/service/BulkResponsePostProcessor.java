package com.epam.cora.esa.service;

import com.epam.cora.esa.model.PipelineEvent;
import org.elasticsearch.action.bulk.BulkItemResponse;

import java.time.LocalDateTime;
import java.util.List;

public interface BulkResponsePostProcessor {

    void postProcessResponse(List<BulkItemResponse> items,
                             List<PipelineEvent.ObjectType> objectType,
                             Long entityId,
                             LocalDateTime syncStart);
}
