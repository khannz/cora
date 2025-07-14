package com.epam.cora.esa.model;

import com.epam.cora.entity.docker.ToolDescription;
import com.epam.cora.entity.pipeline.Tool;
import lombok.Value;

@Value
public class ToolWithDescription {

    private Tool tool;
    private ToolDescription toolDescription;
}
