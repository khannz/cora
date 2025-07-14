package com.epam.cora.entity.region;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Amazon cloud region.
 * <p>
 * Some of the entity fields are perpetual and cannot be changed since the region is created in dao.
 * <p>
 * Amazon cloud region perpetual fields are: {@link AbstractCloudRegion#getRegionCode()}, {@link #profile}.
 */
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class AwsRegion extends AbstractCloudRegion {

    private CloudProvider provider = CloudProvider.AWS;
    private String corsRules;
    private String policy;
    private String kmsKeyId;
    private String kmsKeyArn;
    private String profile;
    //Not empty
    private String sshKeyName;
    //Not empty
    private String tempCredentialsRole;
    private Integer backupDuration;
    private boolean versioningEnabled;
    private String iamRole;
    private String omicsServiceRole;
    private String omicsEcrUrl;
    private String s3Endpoint;
}
