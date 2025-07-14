package com.epam.cora.entity.metadata;

import com.epam.cora.vo.EntityVO;
import lombok.*;

import java.util.Map;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class MetadataEntry {

    private EntityVO entity;
    private Map<String, PipeConfValue> data;
}
