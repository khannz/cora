package com.epam.cora.entity;

import com.epam.cora.entity.utils.DateUtils;
import java.util.Date;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

/**
 * {@link BaseEntity} represents a basic com.epam.pipeline.entity with a {@link Long} id and a minimal
 * set of fields.
 */
@Getter
@Setter
@AllArgsConstructor
@EqualsAndHashCode(of = {"id"})
public class BaseEntity {
    /**
     * {@code Long} represents an com.epam.pipeline.entity's identifier.
     */
    private Long id;

    /**
     * {@code String} represents an com.epam.pipeline.entity's name.
     */
    private String name;

    private Date createdDate;

    public BaseEntity() {
        this.createdDate = new DateUtils().now();
    }

    public BaseEntity(final Long id, final String name) {
        this();
        this.id = id;
        this.name = name;
    }

    @Override
    public String toString() {
        return String.format("%s id: %s name: %s", getClass().getSimpleName(), id, name);
    }
}
