package com.epam.cora.entity.cluster;

import io.fabric8.kubernetes.api.model.Container;
import io.fabric8.kubernetes.api.model.ContainerStatus;
import io.fabric8.kubernetes.api.model.ResourceRequirements;
import lombok.Getter;
import lombok.Setter;

import java.util.*;
import java.util.function.Predicate;
import java.util.stream.Collectors;

@Getter
@Setter
public class ContainerInstance {

    private String name;
    private Map<String, String> requests;
    private Map<String, String> limits;
    private ContainerInstanceStatus status;

    public ContainerInstance() {
        this.requests = new HashMap<>();
        this.limits = new HashMap<>();
    }

    public ContainerInstance(Container container) {
        this();
        this.setName(container.getName());
        ResourceRequirements requirements = container.getResources();
        if (requirements != null) {
            this.setRequests(QuantitiesConverter.convertQuantityMap(requirements.getRequests()));
            this.setLimits(QuantitiesConverter.convertQuantityMap(requirements.getLimits()));
        }
    }

    public ContainerInstance(Container container, List<ContainerStatus> statuses) {
        this(container);
        if (statuses != null) {
            Predicate<? super ContainerStatus> filter = s -> s.getName() != null && s.getName().equals(this.name);
            Optional<ContainerStatus> optStatus = statuses.stream().filter(filter).findFirst();
            if (optStatus.isPresent()) {
                ContainerStatus status = optStatus.get();
                this.status = new ContainerInstanceStatus(status.getState());
            }
        }
    }

    public static List<ContainerInstance> convertToInstances(List<Container> containers) {
        if (containers != null) {
            return containers.stream().map(ContainerInstance::new).collect(Collectors.toList());
        }
        return new ArrayList<>();
    }

    public static List<ContainerInstance> convertToInstances(List<Container> containers, List<ContainerStatus> statuses) {
        if (containers != null) {
            return containers.stream().map(c -> new ContainerInstance(c, statuses)).collect(Collectors.toList());
        }
        return new ArrayList<>();
    }

    public boolean isRunning() {
        return Objects.nonNull(status) && ContainerInstanceStatus.RUNNING.equals(status.getStatus());
    }

    public boolean isPending() {
        return Objects.nonNull(status) && ContainerInstanceStatus.WAITING.equals(status.getStatus());
    }
}
