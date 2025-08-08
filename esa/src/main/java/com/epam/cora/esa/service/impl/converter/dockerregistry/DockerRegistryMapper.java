package com.epam.cora.esa.service.impl.converter.dockerregistry;

import com.epam.cora.entity.pipeline.DockerRegistry;
import com.epam.cora.entity.search.SearchDocumentType;
import com.epam.cora.esa.model.EntityContainer;
import com.epam.cora.esa.service.EntityMapper;
import org.opensearch.core.xcontent.XContentBuilder;
import org.opensearch.core.xcontent.XContentFactory;
import org.springframework.stereotype.Component;

import java.io.IOException;

import static com.epam.cora.esa.service.ElasticsearchSynchronizer.DOC_TYPE_FIELD;

@Component
public class DockerRegistryMapper implements EntityMapper<DockerRegistry> {

    @Override
    public XContentBuilder map(final EntityContainer<DockerRegistry> container) {
        DockerRegistry dockerRegistry = container.getEntity();
        try (XContentBuilder jsonBuilder = XContentFactory.jsonBuilder()) {
            jsonBuilder.startObject().field(DOC_TYPE_FIELD, SearchDocumentType.DOCKER_REGISTRY.name()).field("id", dockerRegistry.getId()).field("name", dockerRegistry.getName()).field("path", dockerRegistry.getPath()).field("createdDate", parseDataToString(dockerRegistry.getCreatedDate())).field("description", dockerRegistry.getDescription()).field("userName", dockerRegistry.getUserName());

            buildUserContent(container.getOwner(), jsonBuilder);
            buildMetadata(container.getMetadata(), jsonBuilder);
            buildPermissions(container.getPermissions(), jsonBuilder);

            jsonBuilder.endObject();
            return jsonBuilder;
        } catch (IOException e) {
            throw new IllegalArgumentException("Failed to create elasticsearch document for docker registry: ", e);
        }
    }
}
