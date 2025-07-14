package com.epam.cora.esa.model.git;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@NoArgsConstructor
public class GitCommit {

    private LocalDateTime timestamp;
    private List<String> added;
    private List<String> modified;
    private List<String> removed;
}
