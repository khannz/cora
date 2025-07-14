package com.epam.cora.entity.pipeline;

import com.epam.cora.entity.AbstractHierarchicalEntity;
import com.epam.cora.entity.AbstractSecuredEntity;
import com.epam.cora.entity.security.acl.AclClass;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

@Getter
@Setter
public class DockerRegistry extends AbstractHierarchicalEntity {

    private String path;
    private String description;
    private String secretName;
    private String userName;
    private String password;
    private String caCert;
    private List<Tool> tools = new ArrayList<>();
    private List<ToolGroup> groups = new ArrayList<>();
    private AclClass aclClass = AclClass.DOCKER_REGISTRY;
    /**
     * Indicates that this registry is configured to use CloudPipeline as Authorization service
     */
    private boolean pipelineAuth = false;
    private boolean hasMetadata;
    private String externalUrl;
    /**
     * Indicates if a Security Scan should be done for this image
     */
    private boolean securityScanEnabled;
    /**
     * Indicates whether current user is allowed to create a private group in this registry
     */
    private Boolean privateGroupAllowed = true;

    public DockerRegistry() {
        // no op
    }

    public DockerRegistry(Long id) {
        super(id);
    }

    public DockerRegistry(long id, String path) {
        super(id, "");
        this.path = path;
    }

    /**
     * Docker registry does not support hierarchical structure
     *
     * @return
     */
    @Override
    @JsonIgnore
    public AbstractSecuredEntity getParent() {
        return null;
    }

    @Override
    public List<AbstractSecuredEntity> getLeaves() {
        return new ArrayList<>(tools);
    }

    @Override
    public List<AbstractHierarchicalEntity> getChildren() {
        return new ArrayList<>(groups);
    }

    @Override
    public void filterLeaves(Map<AclClass, Set<Long>> idToRemove) {
        tools = filterCollection(tools, AclClass.TOOL, idToRemove);
    }

    @Override
    public void filterChildren(Map<AclClass, Set<Long>> idToRemove) {
        groups = filterCollection(groups, AclClass.TOOL_GROUP, idToRemove);
    }

    @JsonIgnore
    public String getSecretName() {
        return secretName;
    }

    @JsonIgnore
    public String getUserName() {
        return userName;
    }

    @JsonIgnore
    public String getPassword() {
        return password;
    }

    @JsonIgnore
    public String getCaCert() {
        return caCert;
    }
}
