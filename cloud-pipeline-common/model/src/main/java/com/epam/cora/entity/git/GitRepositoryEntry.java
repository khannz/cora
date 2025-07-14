package com.epam.cora.entity.git;

import lombok.Getter;
import lombok.Setter;

/**
 * Represents Gitlab repository browsing request result
 */
@Getter
@Setter
public class GitRepositoryEntry {
    private String id;
    private String name;
    private String type;
    private String path;
    private String mode;
}
