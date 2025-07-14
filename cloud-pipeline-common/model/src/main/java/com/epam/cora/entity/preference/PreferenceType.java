package com.epam.cora.entity.preference;

import com.epam.cora.config.JsonMapper;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.math.NumberUtils;

import java.util.Arrays;
import java.util.function.Predicate;

@RequiredArgsConstructor
public enum PreferenceType {
    STRING(0, value -> true),
    INTEGER(1, NumberUtils::isDigits),
    FLOAT(2, NumberUtils::isNumber),
    BOOLEAN(3, value -> "true".equalsIgnoreCase(value) || "false".equalsIgnoreCase(value)),
    OBJECT(4, JsonMapper::isNullOrAnyValidJson),
    LONG(5, NumberUtils::isDigits);

    @Getter
    private final long id;
    private final Predicate<String> validator;

    public static PreferenceType getById(Long id) {
        return Arrays.stream(values())
                .filter(value -> value.id == id)
                .findFirst()
                .orElse(null);
    }

    public boolean validate(final String value) {
        return validator.test(value);
    }
}
