package com.epam.cora.entity.cluster.pool;

import com.epam.cora.entity.pipeline.RunInstance;
import lombok.Data;

import java.util.Set;

@Data
public class RunningInstance {
    private RunInstance instance;
    private NodePool pool;
    private Set<String> prePulledImages;
}
