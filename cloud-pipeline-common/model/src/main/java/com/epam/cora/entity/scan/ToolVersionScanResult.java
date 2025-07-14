package com.epam.cora.entity.scan;

import com.epam.cora.entity.pipeline.ToolScanStatus;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Collections;
import java.util.Date;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class ToolVersionScanResult {

    private Long toolId;
    private String version;
    private ToolScanStatus status;
    private Date scanDate;
    private Date successScanDate;
    private List<Vulnerability> vulnerabilities;
    private List<ToolDependency> dependencies;
    private boolean isAllowedToExecute;

    @JsonIgnore
    private String lastLayerRef;

    public ToolVersionScanResult(ToolScanStatus status, Date scanDate, List<Vulnerability> vulnerabilities,
                                 List<ToolDependency> dependencies) {
        this.status = status;
        this.scanDate = scanDate;
        this.vulnerabilities = vulnerabilities;
        this.dependencies = dependencies;
        if (status == ToolScanStatus.COMPLETED) {
            this.successScanDate = scanDate;
        }
    }

    public ToolVersionScanResult(String version) {
        this.version = version;
        this.status = ToolScanStatus.NOT_SCANNED;
        this.vulnerabilities = Collections.emptyList();
        this.dependencies = Collections.emptyList();
    }

    public ToolVersionScanResult(String version, List<Vulnerability> vulnerabilities, List<ToolDependency> dependencies,
                                 ToolScanStatus status, String lastLayerRef) {
        this.version = version;
        this.vulnerabilities = vulnerabilities;
        this.lastLayerRef = lastLayerRef;
        this.status = status;
        this.dependencies = dependencies;
        this.scanDate = new Date();
        if (status == ToolScanStatus.COMPLETED) {
            this.successScanDate = scanDate;
        }
    }
}
