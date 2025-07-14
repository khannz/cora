package com.epam.cora.esa.service.impl.converter.configuration;

import com.epam.cora.entity.configuration.AbstractRunConfigurationEntry;
import com.epam.cora.entity.configuration.RunConfiguration;
import com.epam.cora.entity.pipeline.Pipeline;
import com.epam.cora.esa.model.ConfigurationEntryDoc;
import com.epam.cora.esa.model.EntityContainer;
import com.epam.cora.esa.model.RunConfigurationDoc;
import lombok.RequiredArgsConstructor;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.elasticsearch.action.DocWriteRequest;
import org.elasticsearch.action.index.IndexRequest;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

import static com.epam.cora.esa.service.EventToRequestConverter.INDEX_TYPE;

@Component
@RequiredArgsConstructor
public class RunConfigurationDocumentBuilder {

    public static final String ID_DELIMITER = "-";
    private final ConfigurationEntryMapper entryMapper;

    public List<DocWriteRequest> createDocsForConfiguration(final String indexName, final EntityContainer<RunConfigurationDoc> configuration) {
        List<EntityContainer<ConfigurationEntryDoc>> entries = splitIntoEntryDocs(configuration);

        return entries.stream().map(entry -> new IndexRequest(indexName, INDEX_TYPE, entry.getEntity().getId()).source(entryMapper.map(entry))).collect(Collectors.toList());
    }

    private List<EntityContainer<ConfigurationEntryDoc>> splitIntoEntryDocs(final EntityContainer<RunConfigurationDoc> configurationDoc) {
        RunConfiguration configuration = configurationDoc.getEntity().getConfiguration();
        if (CollectionUtils.isEmpty(configuration.getEntries())) {
            return Collections.singletonList(convertEntryToDoc(configurationDoc, null));
        }
        return configuration.getEntries().stream().map(entry -> convertEntryToDoc(configurationDoc, entry)).collect(Collectors.toList());
    }

    private EntityContainer<ConfigurationEntryDoc> convertEntryToDoc(final EntityContainer<RunConfigurationDoc> configurationDoc, final AbstractRunConfigurationEntry entry) {
        ConfigurationEntryDoc doc = ConfigurationEntryDoc.builder().id(buildConfigEntryId(configurationDoc, entry)).configuration(configurationDoc.getEntity().getConfiguration()).entry(entry).pipeline(findPipeline(entry, configurationDoc.getEntity().getPipelines())).build();
        return buildContainer(configurationDoc, doc);
    }

    private Pipeline findPipeline(final AbstractRunConfigurationEntry entry, final List<Pipeline> pipelines) {
        if (CollectionUtils.isEmpty(pipelines)) {
            return null;
        }
        return Optional.ofNullable(entry).map(AbstractRunConfigurationEntry::toPipelineStart).flatMap(start -> Optional.ofNullable(start.getPipelineId())).flatMap(pipelineId -> pipelines.stream().filter(pipeline -> pipelineId.equals(pipeline.getId())).findFirst()).orElse(null);
    }

    private String buildConfigEntryId(final EntityContainer<RunConfigurationDoc> configurationDoc, final AbstractRunConfigurationEntry entry) {
        final String id = configurationDoc.getEntity().getConfiguration().getId() + ID_DELIMITER;
        if (Objects.isNull(entry) || StringUtils.isBlank(entry.getName())) {
            return id;
        }
        return id + entry.getName();
    }

    private EntityContainer<ConfigurationEntryDoc> buildContainer(final EntityContainer<RunConfigurationDoc> configurationDoc, final ConfigurationEntryDoc entryDoc) {
        return EntityContainer.<ConfigurationEntryDoc>builder().entity(entryDoc).owner(configurationDoc.getOwner()).metadata(configurationDoc.getMetadata()).permissions(configurationDoc.getPermissions()).build();
    }
}
