package com.epam.cora.entity.datastorage;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TemporaryCredentials {

    @JsonProperty(value = "accessKey")
    private String accessKey;

    @JsonProperty(value = "keyID")
    private String keyId;

    @JsonProperty(value = "token")
    private String token;

    @JsonProperty(value = "expiration")
    private String expirationTime;

    private String region;
}
