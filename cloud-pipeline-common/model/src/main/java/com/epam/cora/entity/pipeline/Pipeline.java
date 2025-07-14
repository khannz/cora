package com.epam.cora.entity.pipeline;

import com.epam.cora.entity.AbstractSecuredEntity;
import com.epam.cora.entity.pipeline.run.RunVisibilityPolicy;
import com.epam.cora.entity.security.acl.AclClass;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class Pipeline extends AbstractSecuredEntity {

    private String description;
    private String repository;
    private Revision currentVersion;
    private Long parentFolderId;
    private String templateId;
    private Folder parent;
    private AclClass aclClass = AclClass.PIPELINE;
    @JsonIgnore
    private String repositoryToken;
    private RepositoryType repositoryType;
    private String repositoryError;
    private boolean hasMetadata;
    private String branch;
    private String configurationPath;
    private RunVisibilityPolicy visibility;
    private String codePath;
    private String docsPath;

    public Pipeline(Long id) {
        super(id);
    }

    @Override
    public void clearParent() {
        this.parent = null;
    }
}
