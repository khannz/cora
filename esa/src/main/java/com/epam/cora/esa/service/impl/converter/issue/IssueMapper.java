package com.epam.cora.esa.service.impl.converter.issue;

import com.epam.cora.entity.issue.Attachment;
import com.epam.cora.entity.issue.Issue;
import com.epam.cora.entity.issue.IssueComment;
import com.epam.cora.entity.search.SearchDocumentType;
import com.epam.cora.esa.model.EntityContainer;
import com.epam.cora.esa.service.EntityMapper;
import com.epam.cora.vo.EntityVO;
import org.opensearch.core.xcontent.XContentBuilder;
import org.opensearch.core.xcontent.XContentFactory;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.io.IOException;
import java.util.List;

import static com.epam.cora.esa.service.ElasticsearchSynchronizer.DOC_TYPE_FIELD;

@Component
public class IssueMapper implements EntityMapper<Issue> {

    @Override
    public XContentBuilder map(final EntityContainer<Issue> container) {
        Issue issue = container.getEntity();
        try (XContentBuilder jsonBuilder = XContentFactory.jsonBuilder()) {
            jsonBuilder.startObject();
            jsonBuilder.field(DOC_TYPE_FIELD, SearchDocumentType.ISSUE.name()).field("id", issue.getId()).field("name", issue.getName()).field("text", issue.getText()).field("status", issue.getStatus()).field("createdDate", parseDataToString(issue.getCreatedDate())).field("updatedDate", parseDataToString(issue.getUpdatedDate()));

            buildLabels(issue.getLabels(), jsonBuilder);
            buildAttachments(issue.getAttachments(), jsonBuilder);
            buildEntityVO(issue.getEntity(), jsonBuilder);
            buildComments(issue.getComments(), jsonBuilder);
            buildPermissions(container.getPermissions(), jsonBuilder);
            buildUserContent(container.getOwner(), jsonBuilder);

            jsonBuilder.endObject();
            return jsonBuilder;
        } catch (IOException e) {
            throw new IllegalArgumentException("Failed to create elasticsearch document for issue: ", e);
        }
    }

    private void buildComments(final List<IssueComment> comments, final XContentBuilder jsonBuilder) throws IOException {
        if (!CollectionUtils.isEmpty(comments)) {
            jsonBuilder.array("comments", comments.stream().map(comment -> comment.getAuthor() + " : " + comment.getText()).toArray(String[]::new));
        }
    }

    private void buildAttachments(final List<Attachment> attachments, final XContentBuilder jsonBuilder) throws IOException {
        if (!CollectionUtils.isEmpty(attachments)) {
            jsonBuilder.array("attachments", attachments.stream().map(Attachment::getPath).toArray(String[]::new));
        }
    }

    private void buildLabels(final List<String> labels, final XContentBuilder jsonBuilder) throws IOException {
        if (!CollectionUtils.isEmpty(labels)) {
            jsonBuilder.array("labels", labels.toArray());
        }
    }

    private void buildEntityVO(final EntityVO entity, final XContentBuilder jsonBuilder) throws IOException {
        if (entity != null) {
            jsonBuilder.field("entityId", entity.getEntityId()).field("parentId", entity.getEntityId()).field("entityClass", entity.getEntityClass());
        }
    }
}
