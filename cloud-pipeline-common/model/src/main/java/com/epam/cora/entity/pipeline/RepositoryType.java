package com.epam.cora.entity.pipeline;

import java.util.HashMap;
import java.util.Map;

public enum RepositoryType {
    GITLAB(0), GITHUB(1), BITBUCKET(2), BITBUCKET_CLOUD(3);

    private long id;
    private static Map<Long, RepositoryType> idMap = new HashMap<>();

    static {
        idMap.put(GITLAB.id, GITLAB);
        idMap.put(GITHUB.id, GITHUB);
        idMap.put(BITBUCKET.id, BITBUCKET);
        idMap.put(BITBUCKET_CLOUD.id, BITBUCKET_CLOUD);
    }

    private static Map<String, RepositoryType> namesMap = new HashMap<>();

    static {
        namesMap.put(GITLAB.name(), GITLAB);
        namesMap.put(GITHUB.name(), GITHUB);
        namesMap.put(BITBUCKET.name(), BITBUCKET);
        namesMap.put(BITBUCKET_CLOUD.name(), BITBUCKET_CLOUD);
    }

    RepositoryType(long id) {
        this.id = id;
    }

    public static RepositoryType getById(Long id) {
        if (id == null) {
            return null;
        }
        return idMap.get(id);
    }

    public static RepositoryType getByName(String name) {
        if (name == null) {
            return null;
        }
        return namesMap.get(name);
    }

    public Long getId() {
        return this.id;
    }
}
