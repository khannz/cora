package com.epam.cora.entity.user;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@EqualsAndHashCode
@AllArgsConstructor
public class Role implements StorageContainer {

    public static final String ROLE_PREFIX = "ROLE_";

    private Long id;
    private String name;
    private boolean predefined;
    private boolean userDefault;
    private Long defaultStorageId;

    public Role() {
        this.predefined = false;
        this.userDefault = false;
    }

    public Role(String name) {
        this();
        this.name = name;
    }

    public Role(Long id, String name) {
        this(name);
        this.id = id;
    }

    @Override
    public Long getDefaultStorageId() {
        return defaultStorageId;
    }
}
