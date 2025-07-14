package com.epam.cora.entity.configuration;

/**
 * Class for common utility methods connected with {@link RunConfiguration}
 */
public final class RunConfigurationUtils {

    /**
     * Calculates node count from {@link PipelineConfiguration#nodeCount} gracefully: if this value is not specified
     * returns {@code basicValue}.
     *
     * @param nodeCount  current node count
     * @param basicValue initial node count
     * @return node count
     */
    public static Integer getNodeCount(Integer nodeCount, int basicValue) {
        return nodeCount == null ? basicValue : nodeCount + basicValue;
    }

    private RunConfigurationUtils() {
        //no-op
    }
}
