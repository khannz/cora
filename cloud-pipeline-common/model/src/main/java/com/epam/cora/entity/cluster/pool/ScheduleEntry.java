package com.epam.cora.entity.cluster.pool;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.temporal.TemporalAdjusters;

@Data
public class ScheduleEntry {
    private DayOfWeek from;
    private LocalTime fromTime;
    private DayOfWeek to;
    private LocalTime toTime;

    @JsonIgnore
    public boolean isActive(final LocalDateTime timestamp) {
        final LocalDateTime start = getFromDay(timestamp).with(fromTime);
        final LocalDateTime end = getToDay(timestamp, start).with(toTime);
        return timestamp.compareTo(start) >= 0 && timestamp.isBefore(end);
    }

    @JsonIgnore
    private LocalDateTime getFromDay(final LocalDateTime timestamp) {
        return timestamp.getDayOfWeek().equals(from) ? timestamp : timestamp.with(TemporalAdjusters.previous(from));
    }

    @JsonIgnore
    private LocalDateTime getToDay(final LocalDateTime timestamp, final LocalDateTime start) {
        if (timestamp.getDayOfWeek().equals(to)) {
            return timestamp;
        }
        if (from.equals(to)) {
            return start;
        }
        return start.with(TemporalAdjusters.next(to));
    }
}
