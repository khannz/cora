package com.epam.cora.esa.service.impl.converter.dockerregistry;

import com.epam.cora.entity.pipeline.DockerRegistry;
import com.epam.cora.esa.service.impl.CloudPipelineAPIClient;
import com.epam.cora.esa.service.impl.converter.AbstractSecuredEntityLoaderImpl;
import org.springframework.stereotype.Component;

@Component
public class DockerRegistryLoader extends AbstractSecuredEntityLoaderImpl<DockerRegistry> {

    public DockerRegistryLoader(final CloudPipelineAPIClient apiClient) {
        super(apiClient);
    }

    @Override
    protected DockerRegistry fetchEntity(final Long id) {
        return getApiClient().loadDockerRegistry(id);
    }
}
