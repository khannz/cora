package com.epam.cora.esa.model.nfsobserver;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@AllArgsConstructor
@Getter
public enum NFSObserverEventType {
    CREATED("c"), MODIFIED("m"), FOLDER_MOVED("fm"), MOVED_FROM("mf"), MOVED_TO("mt"), DELETED("d"), REINDEX("r");

    private final String eventCode;

    private static final Map<String, NFSObserverEventType> CODES = Stream.of(NFSObserverEventType.values()).collect(Collectors.toMap(NFSObserverEventType::getEventCode, Function.identity()));

    public static NFSObserverEventType fromCode(final String code) {
        final NFSObserverEventType event = CODES.get(code);
        if (event == null) {
            throw new IllegalArgumentException("Unsupported event code.");
        }
        return event;
    }
}
