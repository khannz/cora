package com.epam.cora.entity.datastorage;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class LustreFS {

    private String id;
    private String status;
    private String mountPath;
    private String mountOptions;
    private Integer capacityGb;
    private Integer throughput;
    private String deploymentType;
}
