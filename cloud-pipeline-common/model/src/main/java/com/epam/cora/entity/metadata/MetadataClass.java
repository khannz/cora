package com.epam.cora.entity.metadata;

import lombok.*;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class MetadataClass {
    private Long id;
    private String name;
    private FireCloudClass fireCloudClassName;

    public MetadataClass(Long id, String name) {
        this.id = id;
        this.name = name;
    }
}
