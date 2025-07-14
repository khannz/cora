package com.epam.cora.entity.cluster.pool;

import com.epam.cora.entity.cluster.PriceType;
import com.epam.cora.entity.cluster.pool.filter.PoolFilter;
import com.epam.cora.entity.pipeline.RunInstance;
import com.epam.cora.vo.cluster.pool.PoolLabel;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

@Data
public class NodePool {

    private Long id;
    private String name;
    private LocalDateTime created;
    private Long regionId;
    private String instanceType;
    private int instanceDisk;
    private PriceType priceType;
    private Set<String> dockerImages;
    private String instanceImage;
    private int count;
    private NodeSchedule schedule;
    private PoolFilter filter;
    private boolean autoscaled;
    private Integer minSize;
    private Integer maxSize;
    private Double scaleUpThreshold;
    private Double scaleDownThreshold;
    private Integer scaleStep;
    private Map<String, PoolLabel> kubeLabels;

    public boolean isActive(final LocalDateTime timestamp) {
        if (count == 0) {
            return false;
        }
        return Optional.ofNullable(schedule).map(s -> s.isActive(timestamp)).orElse(false);
    }

    @Override
    public String toString() {
        return "NodePool{" + "id=" + id + ", name='" + name + '\'' + ", regionId=" + regionId + ", instanceType='" + instanceType + '\'' + ", instanceDisk=" + instanceDisk + ", priceType=" + priceType + ", dockerImages=" + dockerImages + ", instanceImage='" + instanceImage + '\'' + ", count=" + count + '}';
    }

    public RunningInstance toRunningInstance() {
        final RunningInstance runningInstance = new RunningInstance();
        runningInstance.setInstance(toRunInstance());
        runningInstance.setPrePulledImages(dockerImages);
        runningInstance.setPool(this);
        return runningInstance;
    }

    public RunInstance toRunInstance() {
        final RunInstance runInstance = new RunInstance();
        runInstance.setNodeType(instanceType);
        runInstance.setCloudRegionId(regionId);
        runInstance.setNodeDisk(instanceDisk);
        runInstance.setEffectiveNodeDisk(instanceDisk);
        runInstance.setSpot(PriceType.SPOT.equals(priceType));
        runInstance.setNodeImage(instanceImage);
        runInstance.setPrePulledDockerImages(dockerImages);
        return runInstance;
    }
}
