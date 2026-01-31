package com.epam.cora.esa.service.impl.converter.metadata;

import com.epam.cora.entity.BaseEntity;
import com.epam.cora.entity.metadata.MetadataClass;
import com.epam.cora.entity.metadata.MetadataEntity;
import com.epam.cora.entity.metadata.PipeConfValue;
import com.epam.cora.entity.search.SearchDocumentType;
import com.epam.cora.esa.model.EntityContainer;
import com.epam.cora.esa.service.EntityMapper;
import org.apache.commons.lang3.StringUtils;
import org.opensearch.core.xcontent.XContentBuilder;
import org.opensearch.core.xcontent.XContentFactory;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.io.IOException;
import java.util.Collections;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import static com.epam.cora.esa.service.ElasticsearchSynchronizer.DOC_TYPE_FIELD;

@Component
public class MetadataEntityMapper implements EntityMapper<MetadataEntity> {

    @Override
    public XContentBuilder map(final EntityContainer<MetadataEntity> container) {
        MetadataEntity entity = container.getEntity();
        try (XContentBuilder jsonBuilder = XContentFactory.jsonBuilder()) {
            jsonBuilder.startObject();
            jsonBuilder.field(DOC_TYPE_FIELD, SearchDocumentType.METADATA_ENTITY.name()).field("id", entity.getId()).field("externalId", entity.getExternalId()).field("parentId", Optional.ofNullable(entity.getParent()).map(BaseEntity::getId).orElse(null)).field("name", StringUtils.defaultString(entity.getName(), entity.getExternalId()));

            buildEntityClass(entity.getClassEntity(), jsonBuilder);
            buildPermissions(container.getPermissions(), jsonBuilder);

            buildMap(prepareEntityMetadata(entity.getData()), jsonBuilder, "fields");
            jsonBuilder.endObject();
            return jsonBuilder;
        } catch (IOException e) {
            throw new IllegalArgumentException("Failed to create elasticsearch document for metadata: ", e);
        }
    }

    private void buildEntityClass(final MetadataClass metadataClass, final XContentBuilder jsonBuilder) throws IOException {
        if (metadataClass == null) {
            return;
        }
        jsonBuilder.field("className", metadataClass.getName()).field("classId", metadataClass.getId());
    }

    private Map<String, String> prepareEntityMetadata(final Map<String, PipeConfValue> data) {
        if (CollectionUtils.isEmpty(data)) {
            return Collections.emptyMap();
        }
        return data.entrySet().stream().collect(Collectors.toMap(Map.Entry::getKey, entry -> entry.getValue().getValue()));
    }
}
