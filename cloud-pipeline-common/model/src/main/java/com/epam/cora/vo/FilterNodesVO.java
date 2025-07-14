package com.epam.cora.vo;

import lombok.Getter;
import lombok.Setter;

import java.util.Map;

@Getter
@Setter
public class FilterNodesVO {
    private String runId;
    private String address;
    private Map<String, String> labels;
}
