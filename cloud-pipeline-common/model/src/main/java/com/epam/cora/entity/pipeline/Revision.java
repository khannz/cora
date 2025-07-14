package com.epam.cora.entity.pipeline;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
public class Revision {

    private Long id;
    private String name;
    private String message;
    private Date createdDate;
    private Boolean draft;
    private String commitId;

    public Revision() {
        this.id = 1L;
        this.draft = Boolean.FALSE;
    }

    public Revision(String name, String message, Date createdDate, String commitId) {
        this();
        this.name = name;
        this.message = message;
        this.createdDate = createdDate;
        this.commitId = commitId;
    }

    public Revision(String name, String message, Date createdDate, String commitId, Boolean draft) {
        this(name, message, createdDate, commitId);
        this.draft = draft;
    }
}
