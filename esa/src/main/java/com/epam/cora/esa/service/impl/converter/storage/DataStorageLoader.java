package com.epam.cora.esa.service.impl.converter.storage;

import com.epam.cora.entity.datastorage.AbstractDataStorage;
import com.epam.cora.entity.datastorage.S3bucketDataStorage;
import com.epam.cora.entity.region.AbstractCloudRegion;
import com.epam.cora.entity.region.CloudProvider;
import com.epam.cora.entity.security.acl.AclClass;
import com.epam.cora.esa.model.DataStorageDoc;
import com.epam.cora.esa.service.impl.CloudPipelineAPIClient;
import com.epam.cora.esa.service.impl.converter.AbstractCloudPipelineEntityLoader;
import org.apache.commons.lang3.StringUtils;

import java.util.List;

public class DataStorageLoader extends AbstractCloudPipelineEntityLoader<DataStorageDoc> {

    public DataStorageLoader(final CloudPipelineAPIClient apiClient) {
        super(apiClient);
    }

    @Override
    protected DataStorageDoc fetchEntity(final Long id) {
        AbstractDataStorage dataStorage = getApiClient().loadDataStorage(id);
        List<? extends AbstractCloudRegion> cloudRegions = getApiClient().loadAllRegions();
        DataStorageDoc.DataStorageDocBuilder docBuilder = DataStorageDoc
                .builder()
                .storage(dataStorage);
        if (dataStorage instanceof S3bucketDataStorage) {
            docBuilder.regionName(
                    cloudRegions.stream()
                            .filter(region -> region.getProvider() == CloudProvider.AWS
                                    && region.getId().equals(((S3bucketDataStorage) dataStorage).getRegionId()))
                            .findFirst()
                            .map(AbstractCloudRegion::getRegionCode)
                            .orElse(StringUtils.EMPTY));
        }
        return docBuilder.build();
    }

    @Override
    protected String getOwner(final DataStorageDoc entity) {
        return entity.getStorage().getOwner();
    }

    @Override
    protected AclClass getAclClass(final DataStorageDoc entity) {
        return entity.getStorage().getAclClass();
    }
}
