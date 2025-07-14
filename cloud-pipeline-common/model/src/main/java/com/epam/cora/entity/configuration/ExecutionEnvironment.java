package com.epam.cora.entity.configuration;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.HashMap;
import java.util.Map;

/**
 * Supported environments for running analysis
 */
@AllArgsConstructor
@Getter
public enum ExecutionEnvironment {

    CLOUD_PLATFORM(0, true), FIRECLOUD(1, true), DTS(2, false);
    private final long id;
    private final boolean monitored;

    private static final Map<Long, ExecutionEnvironment> ID_MAP = new HashMap<>();

    static {
        ID_MAP.put(CLOUD_PLATFORM.id, CLOUD_PLATFORM);
        ID_MAP.put(FIRECLOUD.id, FIRECLOUD);
        ID_MAP.put(DTS.id, DTS);
    }

    public static ExecutionEnvironment getById(Long id) {
        return ID_MAP.get(id);
    }
}
