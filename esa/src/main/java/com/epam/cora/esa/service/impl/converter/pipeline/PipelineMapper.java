package com.epam.cora.esa.service.impl.converter.pipeline;

import com.epam.cora.entity.pipeline.Revision;
import com.epam.cora.entity.search.SearchDocumentType;
import com.epam.cora.esa.model.EntityContainer;
import com.epam.cora.esa.model.PipelineDoc;
import com.epam.cora.esa.service.EntityMapper;
import org.opensearch.core.xcontent.XContentBuilder;
import org.opensearch.core.xcontent.XContentFactory;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

import static com.epam.cora.esa.service.ElasticsearchSynchronizer.DOC_TYPE_FIELD;

@Component
public class PipelineMapper implements EntityMapper<PipelineDoc> {

    @Override
    public XContentBuilder map(final EntityContainer<PipelineDoc> container) {

        PipelineDoc pipelineDoc = container.getEntity();

        try (XContentBuilder jsonBuilder = XContentFactory.jsonBuilder()) {
            List<String> revisions = pipelineDoc.getRevisions().stream().map(Revision::getName).collect(Collectors.toList());
            jsonBuilder.startObject().field("id", pipelineDoc.getPipeline().getId()).field(DOC_TYPE_FIELD, SearchDocumentType.PIPELINE.name()).field("name", pipelineDoc.getPipeline().getName()).field("description", pipelineDoc.getPipeline().getDescription()).field("createdDate", parseDataToString(pipelineDoc.getPipeline().getCreatedDate())).field("parentId", pipelineDoc.getPipeline().getParentFolderId()).field("repository", pipelineDoc.getPipeline().getRepository()).field("versions", revisions).field("templateId", pipelineDoc.getPipeline().getTemplateId());

            buildUserContent(container.getOwner(), jsonBuilder);
            buildMetadata(container.getMetadata(), jsonBuilder);
            buildPermissions(container.getPermissions(), jsonBuilder);

            jsonBuilder.endObject();

            return jsonBuilder;
        } catch (IOException e) {
            throw new IllegalArgumentException("Failed to create elasticsearch document for pipeline: ", e);
        }
    }
}
