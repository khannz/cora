package com.epam.cora.entity.cluster;

import io.fabric8.kubernetes.api.model.ContainerState;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ContainerInstanceStatus {

    public static final String RUNNING = "Running";
    public static final String TERMINATED = "Terminated";
    public static final String WAITING = "Waiting";
    public static final String UNKNOWN = "Unknown";

    private String status;
    private String reason;
    private String message;
    private String timestamp;

    public ContainerInstanceStatus() {
    }

    public ContainerInstanceStatus(ContainerState state) {
        this();
        if (state.getRunning() != null) {
            this.status = RUNNING;
            this.timestamp = state.getRunning().getStartedAt();
        } else if (state.getTerminated() != null) {
            this.status = TERMINATED;
            this.timestamp = state.getTerminated().getFinishedAt();
            this.message = state.getTerminated().getMessage();
            this.reason = state.getTerminated().getReason();
        } else if (state.getWaiting() != null) {
            this.status = WAITING;
            this.message = state.getWaiting().getMessage();
            this.reason = state.getWaiting().getReason();
        } else {
            this.status = UNKNOWN;
        }
    }
}
