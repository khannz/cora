package com.epam.cora.esa.service.impl.converter.pipeline;

import com.epam.cora.entity.pipeline.Pipeline;
import com.epam.cora.entity.pipeline.Revision;
import com.epam.cora.entity.security.acl.AclClass;
import com.epam.cora.esa.model.PipelineDoc;
import com.epam.cora.esa.service.impl.CloudPipelineAPIClient;
import com.epam.cora.esa.service.impl.converter.AbstractCloudPipelineEntityLoader;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class PipelineLoader extends AbstractCloudPipelineEntityLoader<PipelineDoc> {

    private static final String DRAFT_VERSION = "draft-";

    public PipelineLoader(final CloudPipelineAPIClient apiClient) {
        super(apiClient);
    }

    @Override
    protected PipelineDoc fetchEntity(final Long id) {
        Pipeline pipeline = getApiClient().loadPipeline(String.valueOf(id));
        List<Revision> revisions = getApiClient().loadPipelineVersions(pipeline.getId()).stream().filter(revision -> !revision.getName().startsWith(DRAFT_VERSION)).collect(Collectors.toList());
        PipelineDoc.PipelineDocBuilder pipelineDocBuilder = PipelineDoc.builder().pipeline(pipeline).revisions(revisions);
        return pipelineDocBuilder.build();
    }

    @Override
    protected String getOwner(PipelineDoc entity) {
        return entity.getPipeline().getOwner();
    }

    @Override
    protected AclClass getAclClass(PipelineDoc entity) {
        return entity.getPipeline().getAclClass();
    }

    @Override
    protected String buildNotFoundErrorMessage(final Long id) {
        return String.format("Pipeline with requested id: '%d' was not found", id);
    }
}
