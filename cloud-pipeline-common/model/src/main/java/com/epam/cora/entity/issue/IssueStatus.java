package com.epam.cora.entity.issue;

import java.util.Arrays;
import java.util.function.Function;

public enum IssueStatus {
    OPEN(0), IN_PROGRESS(1), CLOSED(2);

    private long id;

    IssueStatus(long id) {
        this.id = id;
    }

    public static IssueStatus getById(Long id) {
        return selectByField(IssueStatus::getId, id);
    }

    public static IssueStatus getByName(String name) {
        return selectByField(IssueStatus::name, name);
    }

    private static <T> IssueStatus selectByField(Function<IssueStatus, T> getter, T value) {
        return Arrays.stream(values()).filter(status -> getter.apply(status).equals(value)).findFirst().orElse(null);
    }

    public Long getId() {
        return this.id;
    }
}
