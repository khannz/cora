package com.epam.cora.esa.service;

import org.apache.commons.lang3.math.NumberUtils;
import org.opensearch.action.bulk.BulkItemResponse;

public interface ResponseIdConverter {

    default Long getId(BulkItemResponse response) {
        final String id = response.getId();
        if (NumberUtils.isDigits(id)) {
            return Long.parseLong(id);
        }
        throw new IllegalArgumentException(
                String.format("Failed to read id from string: %s", id));
    }
}
