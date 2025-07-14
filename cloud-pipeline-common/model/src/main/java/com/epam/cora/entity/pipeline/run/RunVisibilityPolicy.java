package com.epam.cora.entity.pipeline.run;

/**
 * Describe how permissions for {@code PipelineRun} shall be checked.
 */
public enum RunVisibilityPolicy {
    /**
     * PipelineRun permissions shall be inherited from parent Pipeline, if it is present.
     * Currently used as a default policy.
     */
    INHERIT,
    /**
     * Permissions on PipelineRun shall be granted only to owner and admin users.
     */
    OWNER
}
