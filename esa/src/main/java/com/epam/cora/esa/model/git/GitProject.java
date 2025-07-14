package com.epam.cora.esa.model.git;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class GitProject {

    @JsonProperty("http_url")
    private String repositoryUrl;
}
