package com.epam.cora.vo;

import com.epam.cora.entity.security.acl.AclClass;
import com.epam.cora.entity.security.acl.AclPermissionEntry;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.HashSet;
import java.util.Set;

@Data
@NoArgsConstructor
public class EntityPermissionVO {
    private Long entityId;
    private AclClass entityClass;
    private String owner;
    private Set<AclPermissionEntry> permissions = new HashSet<>();
}
