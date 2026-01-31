package com.epam.cora.esa.service.impl.converter.pipeline;

import com.epam.cora.entity.pipeline.Pipeline;
import com.epam.cora.entity.search.SearchDocumentType;
import com.epam.cora.esa.model.PermissionsContainer;
import com.epam.cora.utils.FileContentUtils;
import org.opensearch.core.xcontent.XContentBuilder;
import org.opensearch.core.xcontent.XContentFactory;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.nio.charset.Charset;

import static com.epam.cora.esa.service.ElasticsearchSynchronizer.DOC_TYPE_FIELD;

@Component
public class PipelineCodeMapper {

    public XContentBuilder pipelineCodeToDocument(final Pipeline pipeline, final String pipelineVersion, final String path, final byte[] fileContent, final PermissionsContainer permissions) {
        try (XContentBuilder jsonBuilder = XContentFactory.jsonBuilder()) {
            jsonBuilder.startObject().field(DOC_TYPE_FIELD, SearchDocumentType.PIPELINE_CODE.name()).field("pipelineId", pipeline.getId()).field("parentId", pipeline.getId()).field("pipelineName", pipeline.getName()).field("pipelineVersion", pipelineVersion).field("description", pipelineVersion).field("path", path).field("name", path).field("id", path).field("content", buildDocContent(fileContent));

            jsonBuilder.array("allowed_users", permissions.getAllowedUsers().toArray());
            jsonBuilder.array("denied_users", permissions.getDeniedUsers().toArray());
            jsonBuilder.array("allowed_groups", permissions.getAllowedGroups().toArray());
            jsonBuilder.array("denied_groups", permissions.getDeniedGroups().toArray());

            jsonBuilder.endObject();
            return jsonBuilder;
        } catch (IOException e) {
            throw new IllegalArgumentException("An error occurred while creating document: ", e);
        }
    }

    private String buildDocContent(final byte[] fileContent) {
        if (FileContentUtils.isBinaryContent(fileContent)) {
            return null;
        } else {
            return new String(fileContent, Charset.defaultCharset());
        }
    }
}
