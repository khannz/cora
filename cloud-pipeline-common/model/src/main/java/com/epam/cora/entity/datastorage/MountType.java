package com.epam.cora.entity.datastorage;

import lombok.Getter;

@Getter
public enum MountType {

    NFS("nfs"), SMB("cifs"), LUSTRE("lustre");;

    private String protocol;

    MountType(String protocol) {
        this.protocol = protocol;
    }
}
