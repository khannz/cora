package com.epam.cora.esa.model.git;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
public class GitEvent {

    @JsonProperty("event_name")
    private GitEventType eventType;

    @JsonProperty("ref")
    private String reference;

    private GitProject project;

    private List<GitCommit> commits;
}
