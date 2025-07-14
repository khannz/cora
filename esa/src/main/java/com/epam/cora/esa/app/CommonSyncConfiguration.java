package com.epam.cora.esa.app;

import com.epam.cora.esa.dao.PipelineEventDao;
import com.epam.cora.esa.model.PipelineEvent;
import com.epam.cora.esa.service.BulkResponsePostProcessor;
import com.epam.cora.esa.service.ElasticsearchServiceClient;
import com.epam.cora.esa.service.ResponseIdConverter;
import com.epam.cora.esa.service.impl.BulkRequestSender;
import com.epam.cora.esa.service.impl.ElasticIndexService;
import com.epam.cora.esa.service.impl.EntitySynchronizer;
import com.epam.cora.esa.service.impl.converter.EventToRequestConverterImpl;
import com.epam.cora.esa.service.impl.converter.configuration.ConfigurationIdConverter;
import com.epam.cora.esa.service.impl.converter.configuration.RunConfigurationDocumentBuilder;
import com.epam.cora.esa.service.impl.converter.configuration.RunConfigurationEventConverter;
import com.epam.cora.esa.service.impl.converter.configuration.RunConfigurationLoader;
import com.epam.cora.esa.service.impl.converter.dockerregistry.DockerRegistryLoader;
import com.epam.cora.esa.service.impl.converter.dockerregistry.DockerRegistryMapper;
import com.epam.cora.esa.service.impl.converter.folder.FolderLoader;
import com.epam.cora.esa.service.impl.converter.folder.FolderMapper;
import com.epam.cora.esa.service.impl.converter.issue.IssueLoader;
import com.epam.cora.esa.service.impl.converter.issue.IssueMapper;
import com.epam.cora.esa.service.impl.converter.metadata.MetadataEntityLoader;
import com.epam.cora.esa.service.impl.converter.metadata.MetadataEntityMapper;
import com.epam.cora.esa.service.impl.converter.run.PipelineRunLoader;
import com.epam.cora.esa.service.impl.converter.run.PipelineRunMapper;
import com.epam.cora.esa.service.impl.converter.tool.ToolLoader;
import com.epam.cora.esa.service.impl.converter.tool.ToolMapper;
import com.epam.cora.esa.service.impl.converter.toolgroup.ToolGroupLoader;
import com.epam.cora.esa.service.impl.converter.toolgroup.ToolGroupMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class CommonSyncConfiguration {

    private static final String FALSE = "false";
    @Value("${sync.index.common.prefix}")
    private String commonIndexPrefix;
    @Value("${sync.load.common.entity.chunk.size:1000}")
    private int syncChunkSize;

    @Bean
    public BulkRequestSender bulkRequestSender(final ElasticsearchServiceClient elasticsearchClient, final BulkResponsePostProcessor responsePostProcessor) {
        return new BulkRequestSender(elasticsearchClient, responsePostProcessor);
    }

    @Bean
    @ConditionalOnProperty(
            value = "sync.run.disable",
            matchIfMissing = true,
            havingValue = FALSE
    )
    public EntitySynchronizer pipelineRunSynchronizer(final PipelineRunMapper mapper, final PipelineRunLoader loader, final PipelineEventDao eventDao, final ElasticIndexService indexService, final ElasticsearchServiceClient elasticsearchClient, final BulkResponsePostProcessor responsePostProcessor, final @Value("${sync.run.index.name}") String indexName, final @Value("${sync.run.index.mapping}") String runMapping, final @Value("${sync.run.bulk.insert.size:100}") int bulkSize) {
        final BulkRequestSender requestSender = new BulkRequestSender(elasticsearchClient, responsePostProcessor, new ResponseIdConverter() {
        }, bulkSize);
        return new EntitySynchronizer(eventDao, PipelineEvent.ObjectType.RUN, runMapping, new EventToRequestConverterImpl<>(commonIndexPrefix, indexName, loader, mapper), indexService, requestSender, syncChunkSize, bulkSize);
    }

    @Bean
    @ConditionalOnProperty(
            value = "sync.tool.disable",
            matchIfMissing = true,
            havingValue = FALSE
    )
    public EntitySynchronizer toolSynchronizer(final ToolMapper mapper, final ToolLoader loader, final PipelineEventDao eventDao, final ElasticIndexService indexService, final ElasticsearchServiceClient elasticsearchClient, final BulkRequestSender requestSender, final @Value("${sync.tool.index.name}") String indexName, final @Value("${sync.tool.index.mapping}") String toolMapping) {
        return new EntitySynchronizer(eventDao, PipelineEvent.ObjectType.TOOL, toolMapping, new EventToRequestConverterImpl<>(commonIndexPrefix, indexName, loader, mapper), indexService, requestSender, syncChunkSize);
    }

    @Bean
    @ConditionalOnProperty(
            value = "sync.folder.disable",
            matchIfMissing = true,
            havingValue = FALSE
    )
    public EntitySynchronizer pipelineFolderSynchronizer(final FolderMapper mapper, final FolderLoader loader, final PipelineEventDao eventDao, final ElasticIndexService indexService, final BulkRequestSender requestSender, final @Value("${sync.folder.index.name}") String indexName, final @Value("${sync.folder.index.mapping}") String folderMapping) {
        return new EntitySynchronizer(eventDao, PipelineEvent.ObjectType.FOLDER, folderMapping, new EventToRequestConverterImpl<>(commonIndexPrefix, indexName, loader, mapper), indexService, requestSender, syncChunkSize);
    }

    @Bean
    @ConditionalOnProperty(
            value = "sync.tool-group.disable",
            matchIfMissing = true,
            havingValue = FALSE
    )
    public EntitySynchronizer toolGroupSynchronizer(final ToolGroupMapper mapper, final ToolGroupLoader loader, final PipelineEventDao eventDao, final ElasticIndexService indexService, final BulkRequestSender requestSender, final @Value("${sync.tool-group.index.name}") String indexName, final @Value("${sync.tool-group.index.mapping}") String mapping) {
        return new EntitySynchronizer(eventDao, PipelineEvent.ObjectType.TOOL_GROUP, mapping, new EventToRequestConverterImpl<>(commonIndexPrefix, indexName, loader, mapper), indexService, requestSender, syncChunkSize);
    }

    @Bean
    @ConditionalOnProperty(
            value = "sync.docker-registry.disable",
            matchIfMissing = true,
            havingValue = FALSE
    )
    public EntitySynchronizer dockerRegistrySynchronizer(final DockerRegistryMapper mapper, final DockerRegistryLoader loader, final PipelineEventDao eventDao, final ElasticIndexService indexService, final BulkRequestSender requestSender, final @Value("${sync.docker-registry.index.name}") String indexName, final @Value("${sync.docker-registry.index.mapping}") String mapping) {
        return new EntitySynchronizer(eventDao, PipelineEvent.ObjectType.DOCKER_REGISTRY, mapping, new EventToRequestConverterImpl<>(commonIndexPrefix, indexName, loader, mapper), indexService, requestSender, syncChunkSize);
    }

    @Bean
    @ConditionalOnProperty(
            value = "sync.issue.disable",
            matchIfMissing = true,
            havingValue = FALSE
    )
    public EntitySynchronizer issueSynchronizer(final IssueMapper mapper, final IssueLoader loader, final PipelineEventDao eventDao, final ElasticIndexService indexService, final BulkRequestSender requestSender, final @Value("${sync.issue.index.name}") String indexName, final @Value("${sync.issue.index.mapping}") String mapping) {
        return new EntitySynchronizer(eventDao, PipelineEvent.ObjectType.ISSUE, mapping, new EventToRequestConverterImpl<>(commonIndexPrefix, indexName, loader, mapper), indexService, requestSender, syncChunkSize);
    }

    @Bean
    @ConditionalOnProperty(
            value = "sync.metadata-entity.disable",
            matchIfMissing = true,
            havingValue = FALSE
    )
    public EntitySynchronizer metadataEntitySynchronizer(final MetadataEntityMapper mapper, final MetadataEntityLoader loader, final PipelineEventDao eventDao, final ElasticIndexService indexService, final BulkRequestSender requestSender, final @Value("${sync.metadata-entity.index.name}") String indexName, final @Value("${sync.metadata-entity.index.mapping}") String mapping) {
        return new EntitySynchronizer(eventDao, PipelineEvent.ObjectType.METADATA_ENTITY, mapping, new EventToRequestConverterImpl<>(commonIndexPrefix, indexName, loader, mapper), indexService, requestSender, syncChunkSize);
    }

    @Bean
    @ConditionalOnProperty(
            value = "sync.run-configuration.disable",
            matchIfMissing = true,
            havingValue = FALSE
    )
    public EntitySynchronizer runConfigEntitySynchronizer(final RunConfigurationDocumentBuilder documentBuilder, final RunConfigurationLoader loader, final PipelineEventDao eventDao, final ElasticIndexService indexService, final ElasticsearchServiceClient elasticsearchClient, final BulkResponsePostProcessor responsePostProcessor, final @Value("${sync.run-configuration.index.name}") String indexName, final @Value("${sync.run-configuration.index.mapping}") String mapping) {

        final BulkRequestSender requestSender = new BulkRequestSender(elasticsearchClient, responsePostProcessor, new ConfigurationIdConverter());

        return new EntitySynchronizer(eventDao, PipelineEvent.ObjectType.CONFIGURATION, mapping, new RunConfigurationEventConverter(commonIndexPrefix, indexName, loader, documentBuilder, elasticsearchClient, indexService), indexService, requestSender, syncChunkSize);
    }
}
