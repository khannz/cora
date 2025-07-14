package com.epam.cora.esa.utils;

import com.epam.cora.esa.model.EventType;
import com.epam.cora.esa.model.PipelineEvent;
import org.apache.commons.lang3.StringUtils;

import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

public final class EventProcessorUtils {

    private static final String PATHS_DELIMITER = ";";

    private EventProcessorUtils() {}

    public static List<String> splitOnPaths(final String pipelineFileIndexPaths) {
        return Arrays.stream(pipelineFileIndexPaths.split(PATHS_DELIMITER))
                .filter(StringUtils::isNotBlank)
                .collect(Collectors.toList());
    }

    public static List<PipelineEvent> mergeEvents(final List<PipelineEvent> events) {
        Map<Long, List<PipelineEvent>> groupById = events.stream()
                .collect(Collectors.groupingBy(PipelineEvent::getObjectId));
        return groupById.entrySet()
                .stream()
                .map(entry -> mergeEventsByObject(entry.getValue()))
                .filter(Optional::isPresent)
                .map(Optional::get)
                .collect(Collectors.toList());
    }

    private static Optional<PipelineEvent> mergeEventsByObject(final List<PipelineEvent> events) {
        Optional<PipelineEvent> deleteEvent = events.stream()
                .filter(event -> event.getEventType() == EventType.DELETE)
                .findFirst();
        if (deleteEvent.isPresent()) {
            return deleteEvent;
        }
        return events.stream().filter(event -> event.getEventType() != EventType.DELETE)
                .max(Comparator.comparing(PipelineEvent::getCreatedDate));
    }

}
