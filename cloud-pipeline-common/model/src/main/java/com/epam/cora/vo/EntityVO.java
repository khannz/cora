package com.epam.cora.vo;

import com.epam.cora.entity.security.acl.AclClass;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
public class EntityVO {

    private Long entityId;
    private AclClass entityClass;
}
