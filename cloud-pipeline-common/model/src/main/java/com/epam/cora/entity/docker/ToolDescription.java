package com.epam.cora.entity.docker;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
public class ToolDescription {
    private Long toolId;
    private List<ToolVersionAttributes> versions;
}
