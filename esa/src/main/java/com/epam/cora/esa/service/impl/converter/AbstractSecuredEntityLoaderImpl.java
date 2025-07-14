package com.epam.cora.esa.service.impl.converter;

import com.epam.cora.entity.AbstractSecuredEntity;
import com.epam.cora.entity.security.acl.AclClass;
import com.epam.cora.esa.service.impl.CloudPipelineAPIClient;

public abstract class AbstractSecuredEntityLoaderImpl<T extends AbstractSecuredEntity> extends AbstractCloudPipelineEntityLoader<T> {

    public AbstractSecuredEntityLoaderImpl(final CloudPipelineAPIClient apiClient) {
        super(apiClient);
    }

    @Override
    protected String getOwner(final T entity) {
        return entity.getOwner();
    }

    @Override
    protected AclClass getAclClass(final T entity) {
        return entity.getAclClass();
    }
}
