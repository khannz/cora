package com.epam.cora.entity.metadata;

import com.epam.cora.entity.AbstractSecuredEntity;
import com.epam.cora.entity.pipeline.Folder;
import com.epam.cora.entity.security.acl.AclClass;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Map;

@Getter
@Setter
@NoArgsConstructor
public class MetadataEntity extends AbstractSecuredEntity {

    private String externalId;
    private MetadataClass classEntity;
    private Folder parent;
    private Map<String, PipeConfValue> data;
    private AclClass aclClass = AclClass.METADATA_ENTITY;

    public MetadataEntity(final String externalId, final Folder parent) {
        this.externalId = externalId;
        this.parent = parent;
    }
}
