package com.epam.cora.esa.service.impl.converter.metadata;

import com.epam.cora.entity.metadata.MetadataEntity;
import com.epam.cora.entity.security.acl.AclClass;
import com.epam.cora.esa.model.PermissionsContainer;
import com.epam.cora.esa.service.impl.CloudPipelineAPIClient;
import com.epam.cora.esa.service.impl.converter.AbstractCloudPipelineEntityLoader;
import org.springframework.stereotype.Component;

@Component
public class MetadataEntityLoader extends AbstractCloudPipelineEntityLoader<MetadataEntity> {

    public MetadataEntityLoader(CloudPipelineAPIClient apiClient) {
        super(apiClient);
    }

    @Override
    protected MetadataEntity fetchEntity(final Long id) {
        return getApiClient().loadMetadataEntity(id);
    }

    @Override
    protected String getOwner(final MetadataEntity entity) {
        return null;
    }

    @Override
    protected AclClass getAclClass(final MetadataEntity entity) {
        return entity.getAclClass();
    }

    @Override
    protected PermissionsContainer loadPermissions(final Long id, final AclClass entityClass) {
        MetadataEntity metadataEntity = fetchEntity(id);
        return super.loadPermissions(metadataEntity.getParent().getId(), metadataEntity.getParent().getAclClass());
    }
}
