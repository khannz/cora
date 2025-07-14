package com.epam.cora.entity.cluster.pool;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import org.apache.commons.collections4.CollectionUtils;

import java.time.LocalDateTime;
import java.util.List;

@Data
public class NodeSchedule {
    private Long id;
    private String name;
    private LocalDateTime created;
    private List<ScheduleEntry> scheduleEntries;

    @JsonIgnore
    public boolean isActive(final LocalDateTime timestamp) {
        if (CollectionUtils.isEmpty(scheduleEntries)) {
            return true;
        }
        return scheduleEntries.stream().anyMatch(s -> s.isActive(timestamp));
    }
}
