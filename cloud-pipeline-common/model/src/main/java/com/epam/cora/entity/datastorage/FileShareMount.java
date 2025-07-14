package com.epam.cora.entity.datastorage;

import lombok.Data;

@Data
public class FileShareMount {

    private Long id;
    private Long regionId;
    private String mountRoot;
    private MountType mountType;
    private String mountOptions;
}

