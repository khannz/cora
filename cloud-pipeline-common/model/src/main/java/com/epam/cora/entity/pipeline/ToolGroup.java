package com.epam.cora.entity.pipeline;

import com.epam.cora.entity.AbstractHierarchicalEntity;
import com.epam.cora.entity.AbstractSecuredEntity;
import com.epam.cora.entity.security.acl.AclClass;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Represents a group of tools. This com.epam.cora.entity is mainly used to simplify process of rights management
 * on set of tools.
 * A ToolGroup can belong to only one registry and can contain multiple tools.
 */
@Getter
@Setter
@NoArgsConstructor
public class ToolGroup extends AbstractHierarchicalEntity {
    private Long id;
    private Long registryId;
    private String name;
    private String description;

    private List<Tool> tools;

    /**
     * A flag, that determines if a ToolGroup belongs to the currently logged it user. Should be derived
     * from SecurityContext, not from the database
     */
    private boolean privateGroup;

    private final AclClass aclClass = AclClass.TOOL_GROUP;

    @JsonIgnore
    private DockerRegistry parent;

    public ToolGroup(Long id) {
        super(id);
    }

    @Override
    @JsonIgnore
    public List<? extends AbstractSecuredEntity> getLeaves() {
        return tools;
    }

    @Override
    @JsonIgnore
    public List<? extends AbstractHierarchicalEntity> getChildren() {
        return Collections.emptyList();
    }

    @Override
    public void filterLeaves(Map<AclClass, Set<Long>> idToRemove) {
        tools = filterCollection(tools, AclClass.TOOL, idToRemove);
    }

    @Override
    public void filterChildren(Map<AclClass, Set<Long>> idToRemove) {
        // no leaves, so no op
    }

    public DockerRegistry getParent() {
        if (parent != null) {
            return parent;
        }
        return registryId == null ? null : new DockerRegistry(registryId);
    }
}
