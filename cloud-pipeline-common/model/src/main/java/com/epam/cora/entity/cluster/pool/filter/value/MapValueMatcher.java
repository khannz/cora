package com.epam.cora.entity.cluster.pool.filter.value;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.apache.commons.collections4.MapUtils;
import org.apache.commons.lang3.StringUtils;

import java.util.Map;
import java.util.Objects;

@RequiredArgsConstructor
@Getter
public class MapValueMatcher implements ValueMatcher<Map<String, String>> {

    private final Map<String, String> value;

    @Override
    public boolean matches(final Map<String, String> anotherValue) {
        final Map<String, String> currentValue = getValue();
        if (Objects.isNull(currentValue) || Objects.isNull(anotherValue)) {
            return false;
        }
        return currentValue.entrySet().stream().allMatch(entry -> {
            final String value = anotherValue.get(entry.getKey());
            if (Objects.isNull(value)) {
                return false;
            }
            return Objects.equals(value, entry.getValue());
        });
    }

    /**
     * @param anotherValue
     * @return {@code true} if current map does not contain any value for
     * any of {@param anotherValue} keys or does not contain key at all
     */
    @Override
    public boolean empty(final Map<String, String> anotherValue) {
        final Map<String, String> currentValue = getValue();
        if (Objects.isNull(currentValue)) {
            return Objects.nonNull(anotherValue);
        }
        return MapUtils.emptyIfNull(currentValue).keySet().stream().allMatch(key -> {
            final String value = anotherValue.get(key);
            return StringUtils.isBlank(value);
        });
    }
}
