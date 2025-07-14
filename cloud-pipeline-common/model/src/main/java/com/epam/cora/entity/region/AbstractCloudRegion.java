package com.epam.cora.entity.region;

import com.epam.cora.entity.AbstractSecuredEntity;
import com.epam.cora.entity.datastorage.FileShareMount;
import com.epam.cora.entity.security.acl.AclClass;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor
@Getter
@Setter
@JsonTypeInfo(
        use = JsonTypeInfo.Id.NAME,
        include = JsonTypeInfo.As.PROPERTY,
        property = "provider",
        defaultImpl = AwsRegion.class)
@JsonSubTypes({
        @JsonSubTypes.Type(value = AwsRegion.class, name = "AWS"),
        @JsonSubTypes.Type(value = AzureRegion.class, name = "AZURE"),
        @JsonSubTypes.Type(value = GCPRegion.class, name = "GCP")})
public abstract class AbstractCloudRegion extends AbstractSecuredEntity {

    private final AclClass aclClass = AclClass.CLOUD_REGION;

    // There is no parent for cloud region
    private final AbstractSecuredEntity parent = null;

    /**
     * String code from cloud provider
     */
    @JsonProperty(value = "regionId")
    private String regionCode;

    @JsonProperty(value = "default")
    private boolean isDefault;

    private String globalDistributionUrl;
    private List<FileShareMount> fileShareMounts = new ArrayList<>();

    public abstract CloudProvider getProvider();
}
