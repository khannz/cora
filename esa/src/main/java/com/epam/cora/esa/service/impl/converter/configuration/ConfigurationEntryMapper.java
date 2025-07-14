package com.epam.cora.esa.service.impl.converter.configuration;

import com.epam.cora.entity.BaseEntity;
import com.epam.cora.entity.configuration.AbstractRunConfigurationEntry;
import com.epam.cora.entity.configuration.ExecutionEnvironment;
import com.epam.cora.entity.configuration.FirecloudRunConfigurationEntry;
import com.epam.cora.entity.configuration.RunConfiguration;
import com.epam.cora.entity.pipeline.Pipeline;
import com.epam.cora.entity.pipeline.run.PipelineStart;
import com.epam.cora.entity.search.SearchDocumentType;
import com.epam.cora.esa.model.ConfigurationEntryDoc;
import com.epam.cora.esa.model.EntityContainer;
import com.epam.cora.esa.service.EntityMapper;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.elasticsearch.common.xcontent.XContentBuilder;
import org.elasticsearch.common.xcontent.XContentFactory;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Optional;

import static com.epam.cora.esa.service.ElasticsearchSynchronizer.DOC_TYPE_FIELD;

@Component
@RequiredArgsConstructor
public class ConfigurationEntryMapper implements EntityMapper<ConfigurationEntryDoc> {

    @Override
    public XContentBuilder map(final EntityContainer<ConfigurationEntryDoc> container) {
        return getContentBuilder(container);
    }

    private XContentBuilder getContentBuilder(final EntityContainer<ConfigurationEntryDoc> container) {
        RunConfiguration configuration = container.getEntity().getConfiguration();
        try (XContentBuilder jsonBuilder = XContentFactory.jsonBuilder()) {
            jsonBuilder.startObject();
            AbstractRunConfigurationEntry entry = container.getEntity().getEntry();
            jsonBuilder.field(DOC_TYPE_FIELD, SearchDocumentType.CONFIGURATION.name()).field("id", container.getEntity().getId()).field("name", Optional.ofNullable(entry).map(AbstractRunConfigurationEntry::getName).orElse(configuration.getName())).field("description", configuration.getName() + " " + StringUtils.defaultIfBlank(configuration.getDescription(), StringUtils.EMPTY)).field("createdDate", parseDataToString(configuration.getCreatedDate())).field("parentId", Optional.ofNullable(configuration.getParent()).map(BaseEntity::getId).orElse(null));

            buildUserContent(container.getOwner(), jsonBuilder);
            buildMetadata(container.getMetadata(), jsonBuilder);
            buildPermissions(container.getPermissions(), jsonBuilder);

            if (entry != null) {
                buildConfigurationEntry(entry, container.getEntity().getPipeline(), jsonBuilder);
            }

            jsonBuilder.endObject();
            return jsonBuilder;
        } catch (IOException e) {
            throw new IllegalArgumentException("Failed to create elasticsearch document for run configuration: ", e);
        }
    }

    private void buildConfigurationEntry(final AbstractRunConfigurationEntry entry, final Pipeline pipeline, final XContentBuilder jsonBuilder) throws IOException {
        if (entry == null) {
            return;
        }

        jsonBuilder.field("environment", entry.getExecutionEnvironment().name()).field("entryName", entry.getName()).field("rootEntityId", entry.getRootEntityId()).field("configName", entry.getConfigName()).field("defaultConfiguration", entry.isDefaultConfiguration());

        buildExecutionEnvironmentConfiguration(entry, pipeline, jsonBuilder);
    }

    private void buildExecutionEnvironmentConfiguration(final AbstractRunConfigurationEntry entry, final Pipeline pipeline, final XContentBuilder jsonBuilder) throws IOException {
        if (entry.getExecutionEnvironment() == ExecutionEnvironment.CLOUD_PLATFORM || entry.getExecutionEnvironment() == ExecutionEnvironment.DTS) {
            PipelineStart pipelineStart = entry.toPipelineStart();
            jsonBuilder.field("pipelineId", pipelineStart.getPipelineId()).field("pipelineVersion", pipelineStart.getVersion()).field("dockerImage", pipelineStart.getDockerImage());

            if (pipeline != null) {
                jsonBuilder.field("pipelineName", pipeline.getName());
            }
        } else if (entry.getExecutionEnvironment() == ExecutionEnvironment.FIRECLOUD) {
            FirecloudRunConfigurationEntry firecloudEntry = (FirecloudRunConfigurationEntry) entry;
            jsonBuilder.field("methodName", firecloudEntry.getMethodName()).field("methodSnapshot", firecloudEntry.getMethodSnapshot()).field("methodConfigurationName", firecloudEntry.getMethodConfigurationName()).field("methodConfigurationSnapshot", firecloudEntry.getMethodConfigurationSnapshot());
        }
    }
}
