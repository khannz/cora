package com.epam.cora.entity.pipeline;

import com.epam.cora.entity.region.CloudProvider;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.util.StringUtils;

import java.util.Objects;
import java.util.Set;

/**
 * Created by Mariia_Zueva on 5/29/2017.
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class RunInstance {

    private String nodeType;

    /**
     * Node size the was requested by user
     */
    private Integer nodeDisk;

    /**
     * Real requested node disk size (adjusted to docker size image).
     * In common case it will be nodeDisk + dockerSize * 3
     */
    private Integer effectiveNodeDisk;

    private String nodeIP;
    private String nodeId;
    private String nodeImage;
    private String nodeName;
    private Boolean spot;
    private Long cloudRegionId;
    private CloudProvider cloudProvider;
    /**
     * Docker images that shall be pre-pulled to the instance
     */
    private Set<String> prePulledDockerImages;
    private Long poolId;

    @JsonIgnore
    public boolean isEmpty() {
        return nodeType == null && (nodeDisk == null || nodeDisk <= 0) && nodeIP == null && nodeId == null && nodeImage == null && spot == null && cloudRegionId == null && !StringUtils.hasText(nodeName);
    }

    public boolean requirementsMatch(RunInstance other) {
        if (other == null) {
            return false;
        }
        if (!Objects.equals(this.nodeType, other.nodeType)) {
            return false;
        }
        if (!Objects.equals(this.effectiveNodeDisk, other.effectiveNodeDisk)) {
            return false;
        }
        if (!Objects.equals(this.nodeImage, other.nodeImage)) {
            return false;
        }
        if (!Objects.equals(this.spot, other.spot)) {
            return false;
        }
        return Objects.equals(this.cloudRegionId, other.cloudRegionId);
    }
}
