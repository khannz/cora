package com.epam.cora.entity.cluster;

import io.fabric8.kubernetes.api.model.*;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.function.Predicate;
import java.util.stream.Collectors;

@Getter
@Setter
public class PodInstance {

    private UUID uid;
    private String name;
    private String namespace;
    private String nodeName;
    private String phase;

    private List<ContainerInstance> containers;

    public PodInstance() {
        containers = new ArrayList<>();
    }

    public PodInstance(Pod pod) {
        this();
        ObjectMeta metadata = pod.getMetadata();
        if (metadata != null) {
            this.setUid(UUID.fromString(metadata.getUid()));
            this.setName(metadata.getName());
            this.setNamespace(metadata.getNamespace());
        }
        PodStatus podStatus = pod.getStatus();
        if (podStatus != null) {
            this.setPhase(podStatus.getPhase());
        }
        PodSpec podSpec = pod.getSpec();
        if (podSpec != null) {
            this.setNodeName(podSpec.getNodeName());
            if (podStatus != null) {
                this.containers = ContainerInstance.convertToInstances(
                        podSpec.getContainers(),
                        podStatus.getContainerStatuses()
                );
            } else {
                this.containers = ContainerInstance.convertToInstances(
                        podSpec.getContainers()
                );
            }
        }
    }

    public static List<PodInstance> convertToInstances(PodList podList) {
        return podList.getItems().stream().map(PodInstance::new).collect(Collectors.toList());
    }

    public static List<PodInstance> convertToInstances(PodList podList, Predicate<? super Pod> predicate) {
        return podList.getItems().stream().filter(predicate).map(PodInstance::new).collect(Collectors.toList());
    }
}
