package com.epam.cora.esa.model.git;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@JsonIgnoreProperties(ignoreUnknown = true)
public class GitEventData {

    private static final String EVENT_NAME = "event_name";

    private GitEventType gitEventType;

    private String version;

    private List<String> paths;
}
