package com.epam.cora.esa.service.impl.converter.pipeline;

import com.epam.cora.esa.service.ResponseIdConverter;
import com.epam.cora.esa.utils.Utils;
import lombok.RequiredArgsConstructor;
import org.elasticsearch.action.bulk.BulkItemResponse;

import java.util.Arrays;

@RequiredArgsConstructor
public class PipelineIdConverter implements ResponseIdConverter {

    private final String pipelineIndexName;
    private final String pipelineCodeIndexPrefix;

    @Override
    public Long getId(final BulkItemResponse response) {
        final String index = response.getIndex();
        if (pipelineIndexName.equals(index)) {
            return ResponseIdConverter.super.getId(response);
        }
        if (index.startsWith(pipelineCodeIndexPrefix)) {
            final String id = Utils.last(Arrays.asList(index.split("-")));
            return Long.parseLong(id);
        }
        throw new IllegalArgumentException(String.format("Cannot parse id for item {} index {}", response.getId(), index));
    }
}
