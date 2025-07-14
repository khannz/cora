package com.epam.cora.entity.git;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
@AllArgsConstructor
public class GitCredentials {
    private String userName;
    private String token;
    private String email;
    private String url;
}
