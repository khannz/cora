package com.epam.cora.entity.cluster;

import io.fabric8.kubernetes.api.model.NodeAddress;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@Setter
public class NodeInstanceAddress {

    private String address;
    private String type;

    public NodeInstanceAddress() {
    }

    public NodeInstanceAddress(NodeAddress address) {
        this();
        this.setAddress(address.getAddress());
        this.setType(address.getType());
    }

    public static List<NodeInstanceAddress> convertToInstances(List<NodeAddress> addresses) {
        if (addresses != null) {
            return addresses.stream().map(NodeInstanceAddress::new).collect(Collectors.toList());
        }
        return new ArrayList<>();
    }
}
