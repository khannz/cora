package com.epam.cora.entity.region;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

/**
 * Google Cloud Platform region. Represents a zone inside of one of GCP Regions.
 * Holds settings and authorization options
 * related to GCP deployment.
 */
@NoArgsConstructor
@Getter
@Setter
public class GCPRegion extends AbstractCloudRegion {

    private CloudProvider provider = CloudProvider.GCP;
    /**
     * Optional path to service account secret json file, if
     * it is not specified, APPLICATION_DEFAULT credentials will
     * be used for authorization
     */
    private String authFile;
    private String sshPublicKeyPath;
    private String project;
    private String applicationName;
    @JsonProperty("tempCredentialsRole")
    private String impersonatedAccount;
    private List<GCPCustomInstanceType> customInstanceTypes;
    private String corsRules;
    private String policy;
    private Integer backupDuration;
    private boolean versioningEnabled;
}
