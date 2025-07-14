package com.epam.cora.entity.pipeline.run.parameter;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DataStorageLink {
    private Long dataStorageId;
    private String path;
    private String absolutePath;
}
