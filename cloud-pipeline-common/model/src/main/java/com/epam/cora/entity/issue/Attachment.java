package com.epam.cora.entity.issue;

import com.epam.cora.entity.BaseEntity;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Attachment extends BaseEntity {
    private String path;
    private String owner;
}
