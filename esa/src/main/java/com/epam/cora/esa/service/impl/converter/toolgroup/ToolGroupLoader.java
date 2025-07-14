package com.epam.cora.esa.service.impl.converter.toolgroup;

import com.epam.cora.entity.pipeline.ToolGroup;
import com.epam.cora.esa.service.impl.CloudPipelineAPIClient;
import com.epam.cora.esa.service.impl.converter.AbstractSecuredEntityLoaderImpl;
import org.springframework.stereotype.Component;

@Component
public class ToolGroupLoader extends AbstractSecuredEntityLoaderImpl<ToolGroup> {

    public ToolGroupLoader(final CloudPipelineAPIClient apiClient) {
        super(apiClient);
    }

    @Override
    protected ToolGroup fetchEntity(final Long id) {
        return getApiClient().loadToolGroup(String.valueOf(id));
    }
}
