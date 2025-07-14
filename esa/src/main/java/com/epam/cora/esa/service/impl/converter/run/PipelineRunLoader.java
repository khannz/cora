package com.epam.cora.esa.service.impl.converter.run;

import com.epam.cora.entity.pipeline.PipelineRun;
import com.epam.cora.entity.security.acl.AclClass;
import com.epam.cora.esa.model.PermissionsContainer;
import com.epam.cora.esa.model.PipelineRunWithLog;
import com.epam.cora.esa.service.impl.CloudPipelineAPIClient;
import com.epam.cora.esa.service.impl.converter.AbstractCloudPipelineEntityLoader;
import org.springframework.stereotype.Component;

import java.util.Collections;

@Component
public class PipelineRunLoader extends AbstractCloudPipelineEntityLoader<PipelineRunWithLog> {

    public PipelineRunLoader(final CloudPipelineAPIClient apiClient) {
        super(apiClient);
    }

    @Override
    protected PipelineRunWithLog fetchEntity(final Long id) {
        return getApiClient().loadPipelineRunWithLogs(id);
    }

    @Override
    protected String getOwner(final PipelineRunWithLog entity) {
        return entity.getPipelineRun().getOwner();
    }

    @Override
    protected AclClass getAclClass(final PipelineRunWithLog entity) {
        return null;
    }

    @Override
    protected PermissionsContainer loadPermissions(final Long id, final AclClass entityClass) {
        PipelineRun run = getApiClient().loadPipelineRun(id);
        Long pipelineId = run.getPipelineId();
        if (pipelineId == null) {
            PermissionsContainer permissionsContainer = new PermissionsContainer();
            permissionsContainer.add(Collections.emptyList(), run.getOwner());
            return permissionsContainer;
        }
        return super.loadPermissions(pipelineId, AclClass.PIPELINE);
    }
}
