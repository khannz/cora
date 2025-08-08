package com.epam.cora.esa.service.impl.converter.storage;

import com.epam.cora.entity.datastorage.AbstractDataStorage;
import com.epam.cora.entity.datastorage.StoragePolicy;
import com.epam.cora.entity.search.SearchDocumentType;
import com.epam.cora.esa.model.DataStorageDoc;
import com.epam.cora.esa.model.EntityContainer;
import com.epam.cora.esa.service.EntityMapper;
import lombok.RequiredArgsConstructor;
import org.opensearch.core.xcontent.XContentBuilder;
import org.opensearch.core.xcontent.XContentFactory;

import java.io.IOException;

import static com.epam.cora.esa.service.ElasticsearchSynchronizer.DOC_TYPE_FIELD;

@RequiredArgsConstructor
public class DataStorageMapper implements EntityMapper<DataStorageDoc> {

    private final SearchDocumentType documentType;

    @Override
    public XContentBuilder map(final EntityContainer<DataStorageDoc> doc) {
        try (XContentBuilder jsonBuilder = XContentFactory.jsonBuilder()) {
            AbstractDataStorage storage = doc.getEntity().getStorage();
            jsonBuilder
                    .startObject()
                    .field(DOC_TYPE_FIELD, documentType.name())
                    .field("id", storage.getId())
                    .field("parentId", storage.getParentFolderId())
                    .field("name", storage.getName())
                    .field("path", storage.getPath())
                    .field("createdDate", parseDataToString(storage.getCreatedDate()))
                    .field("description", storage.getDescription())
                    .field("storageType", storage.getType())
                    .field("regionCode", doc.getEntity().getRegionName());

            StoragePolicy storagePolicy = storage.getStoragePolicy();
            if (storagePolicy != null) {
                jsonBuilder
                        .field("storagePolicyBackupDuration", storagePolicy.getBackupDuration())
                        .field("storagePolicyLongTermStorageDuration",
                                storagePolicy.getLongTermStorageDuration())
                        .field("storagePolicyShortTermStorageDuration",
                                storagePolicy.getShortTermStorageDuration())
                        .field("storagePolicyVersioningEnabled", storagePolicy.getVersioningEnabled());
            }

            buildUserContent(doc.getOwner(), jsonBuilder);
            buildMetadata(doc.getMetadata(), jsonBuilder);
            buildPermissions(doc.getPermissions(), jsonBuilder);

            jsonBuilder.endObject();
            return jsonBuilder;
        } catch (IOException e) {
            throw new IllegalArgumentException("An error occurred while creating document: ", e);
        }
    }
}
