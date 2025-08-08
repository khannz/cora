package com.epam.cora.esa.service.impl.converter.folder;

import com.epam.cora.entity.pipeline.Folder;
import com.epam.cora.entity.search.SearchDocumentType;
import com.epam.cora.esa.model.EntityContainer;
import com.epam.cora.esa.service.EntityMapper;
import org.opensearch.core.xcontent.XContentBuilder;
import org.opensearch.core.xcontent.XContentFactory;
import org.springframework.stereotype.Component;

import java.io.IOException;

import static com.epam.cora.esa.service.ElasticsearchSynchronizer.DOC_TYPE_FIELD;

@Component
public class FolderMapper implements EntityMapper<Folder> {

    @Override
    public XContentBuilder map(final EntityContainer<Folder> container) {
        Folder folder = container.getEntity();
        try (XContentBuilder jsonBuilder = XContentFactory.jsonBuilder()) {
            jsonBuilder.startObject().field(DOC_TYPE_FIELD, SearchDocumentType.FOLDER.name()).field("id", folder.getId()).field("name", folder.getName()).field("parentId", folder.getParentId()).field("createdDate", parseDataToString(folder.getCreatedDate()));

            buildUserContent(container.getOwner(), jsonBuilder);
            buildMetadata(container.getMetadata(), jsonBuilder);
            buildPermissions(container.getPermissions(), jsonBuilder);

            jsonBuilder.endObject();

            return jsonBuilder;
        } catch (IOException e) {
            throw new IllegalArgumentException("Failed to create elasticsearch document for pipeline folder: ", e);
        }
    }
}
