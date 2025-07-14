package com.epam.cora.esa.service.impl.converter.configuration;

import com.epam.cora.esa.service.ResponseIdConverter;
import org.elasticsearch.action.bulk.BulkItemResponse;

import static com.epam.cora.esa.service.impl.converter.configuration.RunConfigurationDocumentBuilder.ID_DELIMITER;

public class ConfigurationIdConverter implements ResponseIdConverter {

    @Override
    public Long getId(final BulkItemResponse response) {
        final String id = response.getId();
        String[] split = id.split(ID_DELIMITER);
        return Long.parseLong(split[0]);
    }
}
