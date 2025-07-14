package com.epam.cora.vo.cluster.pool;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class NodePoolUsage {
    private Long id;
    private LocalDateTime logDate;
    private Long nodePoolId;
    private Integer totalNodesCount;
    private Integer occupiedNodesCount;
    private Long pendingRunsCount;
    private Long activeRunsCount;
}
