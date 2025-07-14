package com.epam.cora.entity.metadata;

import com.epam.cora.entity.pipeline.run.parameter.DataStorageLink;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@EqualsAndHashCode
public class PipeConfValue {

    private String type;
    private String value;
    private List<DataStorageLink> dataStorageLinks;

    public PipeConfValue(String type, String value) {
        this.type = type;
        this.value = value;
    }
}
