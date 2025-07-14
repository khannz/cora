package com.epam.cora.entity.preference;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.apache.commons.lang3.StringUtils;

import java.util.Date;
import java.util.function.Function;

/**
 * Describes a system preference, that is stored in the database and controls some aspects of application runtime.
 */
@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode
public class Preference {
    private String name;
    private Date createdDate;
    private String value;
    private String preferenceGroup;
    private String description;

    /**
     * Defines if a preference should be visible to users on UI
     */
    private boolean visible = true;
    private PreferenceType type;

    public Preference(String name, String value, String group, String description, PreferenceType type, boolean visible) {
        this.name = name;
        this.value = value;
        this.description = description;
        this.preferenceGroup = group;
        this.type = type;
        this.visible = visible;
    }

    public Preference(String name, String value) {
        this.name = name;
        this.value = value;
    }

    /**
     * Casts a value of a preference to a required type with a specified cast function
     *
     * @param castFunction a function, that transforms string value of preference to a required type
     * @param <T>          required preference type
     * @return required preference value
     */
    public <T> T get(Function<String, T> castFunction) {
        if (StringUtils.isNotBlank(value)) {
            return castFunction.apply(value);
        }

        return null;
    }
}
