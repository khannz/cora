package com.epam.cora.esa.service.impl.converter.configuration;

import com.epam.cora.entity.configuration.RunConfiguration;
import com.epam.cora.entity.configuration.RunConfigurationEntry;
import com.epam.cora.entity.security.acl.AclClass;
import com.epam.cora.esa.model.RunConfigurationDoc;
import com.epam.cora.esa.service.impl.CloudPipelineAPIClient;
import com.epam.cora.esa.service.impl.converter.AbstractCloudPipelineEntityLoader;
import org.apache.commons.collections4.ListUtils;
import org.springframework.stereotype.Component;

import java.util.Objects;
import java.util.stream.Collectors;

@Component
public class RunConfigurationLoader extends AbstractCloudPipelineEntityLoader<RunConfigurationDoc> {

    public RunConfigurationLoader(final CloudPipelineAPIClient apiClient) {
        super(apiClient);
    }

    @Override
    protected RunConfigurationDoc fetchEntity(final Long id) {
        final RunConfiguration configuration = getApiClient().loadRunConfiguration(id);
        return RunConfigurationDoc.builder().configuration(configuration).pipelines(ListUtils.emptyIfNull(configuration.getEntries()).stream().map(entry -> {
            if (entry instanceof RunConfigurationEntry) {
                return ((RunConfigurationEntry) entry).getPipelineId();
            }
            return null;
        }).filter(Objects::nonNull).map(pipelineId -> getApiClient().loadPipeline(String.valueOf(pipelineId))).collect(Collectors.toList())).build();
    }

    @Override
    protected String getOwner(final RunConfigurationDoc entity) {
        return entity.getConfiguration().getOwner();
    }

    @Override
    protected AclClass getAclClass(final RunConfigurationDoc entity) {
        return entity.getConfiguration().getAclClass();
    }
}
