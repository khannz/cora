package com.epam.cora.entity.region;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@EqualsAndHashCode
public class GCPCustomInstanceType {
    private int cpu;
    private double ram;
    private int gpu;
    private String gpuType;

    public static GCPCustomInstanceType withCpu(final int cpu, final double ram) {
        return new GCPCustomInstanceType(cpu, ram, 0, null);
    }

    public static GCPCustomInstanceType withGpu(final int cpu, final double ram, final int gpu, final String gpuType) {
        return new GCPCustomInstanceType(cpu, ram, gpu, gpuType);
    }
}
