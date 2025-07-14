package com.epam.cora.esa.service.impl.converter.toolgroup;

import com.epam.cora.entity.pipeline.ToolGroup;
import com.epam.cora.entity.search.SearchDocumentType;
import com.epam.cora.esa.model.EntityContainer;
import com.epam.cora.esa.service.EntityMapper;
import org.elasticsearch.common.xcontent.XContentBuilder;
import org.elasticsearch.common.xcontent.XContentFactory;
import org.springframework.stereotype.Component;

import java.io.IOException;

import static com.epam.cora.esa.service.ElasticsearchSynchronizer.DOC_TYPE_FIELD;

@Component
public class ToolGroupMapper implements EntityMapper<ToolGroup> {

    @Override
    public XContentBuilder map(final EntityContainer<ToolGroup> container) {
        ToolGroup toolGroup = container.getEntity();
        try (XContentBuilder jsonBuilder = XContentFactory.jsonBuilder()) {
            jsonBuilder.startObject();

            jsonBuilder.field(DOC_TYPE_FIELD, SearchDocumentType.TOOL_GROUP.name()).field("id", toolGroup.getId()).field("name", toolGroup.getName()).field("registryId", toolGroup.getRegistryId()).field("parentId", toolGroup.getRegistryId()).field("createdDate", parseDataToString(toolGroup.getCreatedDate())).field("description", toolGroup.getDescription());

            buildUserContent(container.getOwner(), jsonBuilder);
            buildMetadata(container.getMetadata(), jsonBuilder);
            buildPermissions(container.getPermissions(), jsonBuilder);

            jsonBuilder.endObject();
            return jsonBuilder;
        } catch (IOException e) {
            throw new IllegalArgumentException("Failed to create elasticsearch document for tool group: ", e);
        }
    }
}
