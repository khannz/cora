package com.epam.cora.entity.docker;

import com.epam.cora.entity.configuration.ConfigurationEntry;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ToolVersion {
    private Long id;
    private Long toolId;
    private String version;
    private String digest;
    private Long size;
    private Date modificationDate;
    private List<ConfigurationEntry> settings;
}
