package com.epam.cora.esa.service;

import com.epam.cora.esa.model.PipelineEvent;
import com.epam.cora.entity.user.PipelineUser;
import org.elasticsearch.action.DocWriteRequest;
import org.elasticsearch.action.delete.DeleteRequest;
import org.elasticsearch.common.xcontent.XContentBuilder;
import org.springframework.util.CollectionUtils;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

public interface EventToRequestConverter {

    String INDEX_TYPE = "_doc";
    String DATE_PATTERN = "yyyy-MM-dd HH:mm:ss.SSS";
    SimpleDateFormat SIMPLE_DATE_FORMAT = new SimpleDateFormat(DATE_PATTERN);
    String ATTRIBUTE_NAME = "Name";

    String getIndexPrefix();
    String getIndexName();

    default String buildIndexName() {
        return getIndexPrefix() + getIndexName();
    }

    List<DocWriteRequest> convertEventsToRequest(List<PipelineEvent> events,
                                                 String indexName);

    default DeleteRequest createDeleteRequest(PipelineEvent event, String indexName) {
        return new DeleteRequest(indexName, INDEX_TYPE, String.valueOf(event.getObjectId()));
    }

    default String parseDataToString(Date date) {
        if (date == null) {
            return null;
        }
        return SIMPLE_DATE_FORMAT.format(date);
    }

    default XContentBuilder buildUserContent(final PipelineUser user,
                                             final XContentBuilder jsonBuilder) throws IOException {
        if (user != null) {
            Map<String, String> attributes = user.getAttributes();
            jsonBuilder
                    .field("ownerUserId", user.getId())
                    .field("ownerUserName", user.getUserName())
                    .field("ownerFriendlyName",
                            CollectionUtils.isEmpty(attributes) ? null : attributes.get(ATTRIBUTE_NAME))
                    .field("ownerGroups", user.getGroups());
        }
        return jsonBuilder;
    }

}
