package com.epam.cora.entity.cluster;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Value;

import java.util.List;

@Value
public class AllowedInstanceAndPriceTypes {

    @JsonProperty("cluster.allowed.instance.types")
    List<InstanceType> allowedInstanceTypes;

    @JsonProperty("cluster.allowed.instance.types.docker")
    List<InstanceType> allowedInstanceDockerTypes;

    @JsonProperty("cluster.allowed.price.types")
    List<String> allowedPriceTypes;
}
