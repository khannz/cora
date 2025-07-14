package com.epam.cora.esa.service.impl.converter.folder;

import com.epam.cora.entity.pipeline.Folder;
import com.epam.cora.esa.service.impl.CloudPipelineAPIClient;
import com.epam.cora.esa.service.impl.converter.AbstractSecuredEntityLoaderImpl;
import org.springframework.stereotype.Component;

@Component
public class FolderLoader extends AbstractSecuredEntityLoaderImpl<Folder> {

    public FolderLoader(final CloudPipelineAPIClient apiClient) {
        super(apiClient);
    }

    @Override
    protected Folder fetchEntity(final Long id) {
        return getApiClient().loadPipelineFolder(id);
    }
}
