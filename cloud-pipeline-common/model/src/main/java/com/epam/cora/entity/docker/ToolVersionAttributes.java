package com.epam.cora.entity.docker;

import com.epam.cora.entity.scan.ToolVersionScanResult;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ToolVersionAttributes {
    private String version;
    private ToolVersion attributes;
    private ToolVersionScanResult scanResult;
}
