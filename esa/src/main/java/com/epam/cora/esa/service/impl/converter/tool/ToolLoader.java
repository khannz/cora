package com.epam.cora.esa.service.impl.converter.tool;

import com.epam.cora.entity.docker.ToolDescription;
import com.epam.cora.entity.pipeline.Tool;
import com.epam.cora.entity.security.acl.AclClass;
import com.epam.cora.esa.model.ToolWithDescription;
import com.epam.cora.esa.service.impl.CloudPipelineAPIClient;
import com.epam.cora.esa.service.impl.converter.AbstractCloudPipelineEntityLoader;
import org.springframework.stereotype.Component;

@Component
public class ToolLoader extends AbstractCloudPipelineEntityLoader<ToolWithDescription> {

    public ToolLoader(final CloudPipelineAPIClient apiClient) {
        super(apiClient);
    }

    @Override
    protected ToolWithDescription fetchEntity(final Long id) {
        final Tool tool = getApiClient().loadTool(String.valueOf(id));
        final ToolDescription toolDescription = getApiClient().loadToolDescription(tool.getId());
        return new ToolWithDescription(tool, toolDescription);
    }

    @Override
    protected String getOwner(final ToolWithDescription entity) {
        return entity.getTool().getOwner();
    }

    @Override
    protected AclClass getAclClass(final ToolWithDescription entity) {
        return entity.getTool().getAclClass();
    }
}
