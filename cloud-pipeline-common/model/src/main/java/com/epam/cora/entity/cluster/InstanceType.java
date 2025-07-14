package com.epam.cora.entity.cluster;

import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class InstanceType {
    private String sku;
    private String name;
    private String operatingSystem;
    private int vCPU;
    private float memory;
    private String memoryUnit;
    private String instanceFamily;
    private int gpu;
}
