package com.epam.cora.esa.model;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.AbstractMap.SimpleEntry;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Getter
@RequiredArgsConstructor
public enum EventType {

    INSERT('I'), UPDATE('U'), DELETE('D');

    private final char code;

    private static final Map<Character, EventType> CODES = Stream.of(
                    new SimpleEntry<>(INSERT.code, INSERT),
                    new SimpleEntry<>(UPDATE.code, UPDATE),
                    new SimpleEntry<>(DELETE.code, DELETE))
            .collect(Collectors.toMap(SimpleEntry::getKey, SimpleEntry::getValue));

    public static EventType fromCode(char code) {
        EventType eventType = CODES.get(code);
        if (eventType == null) {
            throw new IllegalArgumentException("Unsupported event code.");
        }
        return eventType;
    }
}
