package com.epam.cora.entity.security.acl;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.acls.domain.GrantedAuthoritySid;
import org.springframework.security.acls.domain.PrincipalSid;
import org.springframework.security.acls.model.Sid;

@Data
@NoArgsConstructor
public class AclSid {
    private String name;
    private boolean isPrincipal;

    public AclSid(String name, boolean isPrincipal) {
        this.name = name;
        this.isPrincipal = isPrincipal;
    }

    public AclSid(Sid sid) {
        if (sid instanceof PrincipalSid) {
            this.name = ((PrincipalSid) sid).getPrincipal();
            this.isPrincipal = true;
        } else if (sid instanceof GrantedAuthoritySid) {
            this.name = ((GrantedAuthoritySid) sid).getGrantedAuthority();
            this.isPrincipal = false;
        } else {
            throw new IllegalArgumentException("Unsupported sid " + sid.getClass());
        }
    }
}
