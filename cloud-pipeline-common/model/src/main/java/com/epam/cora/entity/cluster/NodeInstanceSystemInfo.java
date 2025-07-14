package com.epam.cora.entity.cluster;

import io.fabric8.kubernetes.api.model.NodeSystemInfo;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class NodeInstanceSystemInfo {
    private String machineID;
    private String systemUUID;
    private String bootID;
    private String kernelVersion;
    private String osImage;
    private String operatingSystem;
    private String architecture;
    private String containerRuntimeVersion;
    private String kubeletVersion;
    private String kubeProxyVersion;

    public NodeInstanceSystemInfo() {
    }

    public NodeInstanceSystemInfo(NodeSystemInfo info) {
        this();
        this.setMachineID(info.getMachineID());
        this.setSystemUUID(info.getSystemUUID());
        this.setBootID(info.getBootID());
        this.setKernelVersion(info.getKernelVersion());
        this.setOsImage(info.getOsImage());
        this.setOperatingSystem(info.getOperatingSystem());
        this.setArchitecture(info.getArchitecture());
        this.setContainerRuntimeVersion(info.getContainerRuntimeVersion());
        this.setKubeletVersion(info.getKubeletVersion());
        this.setKubeProxyVersion(info.getKubeProxyVersion());
    }
}
