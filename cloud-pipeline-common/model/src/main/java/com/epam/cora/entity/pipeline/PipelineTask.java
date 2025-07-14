package com.epam.cora.entity.pipeline;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
public class PipelineTask {
    private static final String TASK_ID_FORMAT = "%s(%s)";

    private String name;
    private String parameters;
    private Long id;
    private TaskStatus status;
    private String instance;
    private Date created;
    private Date started;
    private Date finished;

    public PipelineTask() {
        // no op
    }

    public PipelineTask(String name) {
        String trimmedName = name.trim();
        int openIndex = trimmedName.indexOf('(');
        if (openIndex != -1 && trimmedName.endsWith(")")) {
            this.name = trimmedName.substring(0, openIndex);
            this.parameters = trimmedName.substring(openIndex + 1, trimmedName.length() - 1);
        } else {
            this.name = trimmedName;
        }
    }

    public static String buildTaskId(String taskName, String parameters) {
        if (parameters == null || parameters.isEmpty()) {
            return taskName.trim();
        } else {
            return String.format(TASK_ID_FORMAT, taskName.trim(), parameters.trim());
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        PipelineTask task = (PipelineTask) o;

        if (!name.equals(task.name)) {
            return false;
        }
        return parameters != null ? parameters.equals(task.parameters) : task.parameters == null;
    }

    @Override
    public int hashCode() {
        int result = name.hashCode();
        result = 31 * result + (parameters != null ? parameters.hashCode() : 0);
        return result;
    }
}
