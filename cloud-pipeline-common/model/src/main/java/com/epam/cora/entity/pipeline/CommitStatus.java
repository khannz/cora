package com.epam.cora.entity.pipeline;

import java.util.HashMap;
import java.util.Map;

public enum CommitStatus {
    NOT_COMMITTED(0), COMMITTING(1), FAILURE(2), SUCCESS(3);

    private long id;
    private static Map<Long, CommitStatus> idMap = new HashMap<>();

    static {
        idMap.put(NOT_COMMITTED.id, NOT_COMMITTED);
        idMap.put(COMMITTING.id, COMMITTING);
        idMap.put(FAILURE.id, FAILURE);
        idMap.put(SUCCESS.id, SUCCESS);
    }

    private static Map<String, CommitStatus> namesMap = new HashMap<>();

    static {
        namesMap.put(NOT_COMMITTED.name(), NOT_COMMITTED);
        namesMap.put(COMMITTING.name(), COMMITTING);
        namesMap.put(FAILURE.name(), FAILURE);
        namesMap.put(SUCCESS.name(), SUCCESS);
    }

    CommitStatus(long id) {
        this.id = id;
    }

    public Long getId() {
        return this.id;
    }

    public static CommitStatus getById(Long id) {
        if (id == null) {
            return null;
        }
        return idMap.get(id);
    }

    public static CommitStatus getByName(String name) {
        if (name == null) {
            return null;
        }
        return namesMap.get(name);
    }
}
