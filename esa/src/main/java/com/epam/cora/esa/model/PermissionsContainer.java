package com.epam.cora.esa.model;

import com.epam.cora.entity.security.acl.AclPermissionEntry;
import com.epam.cora.entity.security.acl.AclSid;
import com.epam.cora.vo.EntityPermissionVO;
import lombok.Getter;
import lombok.Setter;
import org.apache.commons.collections4.CollectionUtils;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
public class PermissionsContainer {
    private static final int READ_MASK = 1;
    private static final int NO_READ_MASK = 2;
    private static final String ROLE_ADMIN = "ROLE_ADMIN";

    private Set<String> allowedUsers = new HashSet<>();
    private Set<String> deniedUsers = new HashSet<>();

    private Set<String> allowedGroups = new HashSet<>();
    private Set<String> deniedGroups = new HashSet<>();

    public PermissionsContainer() {
        allowedGroups.add(ROLE_ADMIN);
    }

    public PermissionsContainer(final EntityPermissionVO permissions) {
        this();
        if (permissions != null) {
            add(permissions.getPermissions(), permissions.getOwner());
        }
    }

    public void add(final Collection<AclPermissionEntry> permissions, final String owner) {
        allowedUsers.add(owner);
        if (CollectionUtils.isEmpty(permissions)) {
            return;
        }
        permissions.forEach(aclPermissionEntry -> {
            final AclSid sid = aclPermissionEntry.getSid();
            final String sidName = sid.getName();
            final Set<String> allowed = sid.isPrincipal() ? allowedUsers : allowedGroups;
            final Set<String> denied = sid.isPrincipal() ? deniedUsers : deniedGroups;
            addPermissions(aclPermissionEntry.getMask(), sidName, owner, allowed, denied);
        });
    }

    private void addPermissions(final Integer mask,
                                final String sidName,
                                final String owner,
                                final Set<String> allowed,
                                final Set<String> denied) {
        if (readAllowed(mask)) {
            allowed.add(sidName);
        } else if (readDenied(mask)
                && !ROLE_ADMIN.equalsIgnoreCase(sidName)
                && !owner.equalsIgnoreCase(sidName)) {
            denied.add(sidName);
        }
    }

    private static boolean readAllowed(final int mask) {
        return hasPermissionMask(mask, READ_MASK);
    }

    private static boolean readDenied(final int mask) {
        return hasPermissionMask(mask, NO_READ_MASK);
    }

    private static boolean hasPermissionMask(final int mask, final int permissionMask) {
        return (mask & permissionMask) == permissionMask;
    }
}
