package com.epam.cora.entity.configuration;

import com.epam.cora.entity.AbstractSecuredEntity;
import com.epam.cora.entity.pipeline.Folder;
import com.epam.cora.entity.security.acl.AclClass;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

import static com.epam.cora.entity.configuration.RunConfigurationUtils.getNodeCount;
import static com.epam.cora.entity.security.acl.AclClass.CONFIGURATION;

/**
 * Represents configuration for running analysis in  {@link ExecutionEnvironment#CLOUD_PLATFORM}.
 */
@Getter
@Setter
@NoArgsConstructor
public class RunConfiguration extends AbstractSecuredEntity {

    private String description;
    private Folder parent;
    private List<AbstractRunConfigurationEntry> entries = new ArrayList<>();

    @Override
    public Folder getParent() {
        return parent;
    }

    @Override
    public AclClass getAclClass() {
        return CONFIGURATION;
    }

    @JsonIgnore
    public int getTotalNodeCount() {
        return getEntries().stream()
                .map(AbstractRunConfigurationEntry::getWorkerCount)
                .mapToInt(nodeCount -> getNodeCount(nodeCount, 1))
                .sum();
    }
}
