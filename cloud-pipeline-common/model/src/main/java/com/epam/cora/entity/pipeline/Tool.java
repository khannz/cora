package com.epam.cora.entity.pipeline;

import com.epam.cora.entity.AbstractSecuredEntity;
import com.epam.cora.entity.security.acl.AclClass;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class Tool extends AbstractSecuredEntity {

    private Long id;
    private String image;
    private String cpu;
    private String ram;
    private String instanceType;
    private Integer disk;

    private Long registryId;
    private String registry;
    private Long toolGroupId;
    private String toolGroup;

    @JsonIgnore
    private String secretName;
    private String description;
    private String shortDescription;
    private List<String> labels;
    private List<String> endpoints;
    private String defaultCommand;
    private boolean hasIcon;
    private Long iconId;

    public void setIconId(Long iconId) {
        this.iconId = iconId;
        hasIcon = iconId != null;
    }

    private ToolGroup parent;
    private AclClass aclClass = AclClass.TOOL;

    public Tool(String image) {
        this.image = image;
    }

    public ToolGroup getParent() {
        if (parent != null) {
            return parent;
        }
        return toolGroupId == null ? null : new ToolGroup(toolGroupId);
    }
}
