package com.epam.cora.entity.docker;

import com.epam.cora.entity.AbstractHierarchicalEntity;
import com.epam.cora.entity.AbstractSecuredEntity;
import com.epam.cora.entity.pipeline.DockerRegistry;
import com.epam.cora.entity.security.acl.AclClass;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.*;

/**
 * {@link DockerRegistryList} represents a pseudo root entity for returning list of
 * {@link DockerRegistry} entities in one tree structure
 */
@Getter
@Setter
@NoArgsConstructor
public class DockerRegistryList extends AbstractHierarchicalEntity {
    private List<DockerRegistry> registries;

    public DockerRegistryList(List<DockerRegistry> registries) {
        super();
        this.registries = registries;
    }

    @Override
    public List<AbstractSecuredEntity> getLeaves() {
        return Collections.emptyList();
    }

    @Override
    public List<AbstractHierarchicalEntity> getChildren() {
        return new ArrayList<>(registries);
    }

    @Override
    public void filterLeaves(Map<AclClass, Set<Long>> idToRemove) {
        //no op
    }

    @Override
    public void filterChildren(Map<AclClass, Set<Long>> idToRemove) {
        registries = filterCollection(registries, AclClass.DOCKER_REGISTRY, idToRemove);
    }

    @Override
    public AbstractSecuredEntity getParent() {
        return null;
    }

    @Override
    public AclClass getAclClass() {
        return AclClass.DOCKER_REGISTRY;
    }
}
