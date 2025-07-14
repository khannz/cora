package com.epam.cora.esa.model;

import com.epam.cora.entity.user.PipelineUser;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Map;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class EntityContainer<T> {

    private T entity;
    private PipelineUser owner;
    private Map<String, String> metadata;
    private PermissionsContainer permissions;
}
