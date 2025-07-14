package com.epam.cora.vo;

import com.epam.cora.entity.pipeline.TaskStatus;

import java.util.Date;

public class RunStatusVO {

    private TaskStatus status;

    private Date endDate;

    public TaskStatus getStatus() {
        return status;
    }

    public void setStatus(TaskStatus status) {
        this.status = status;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }
}
