package com.epam.cora.entity.region;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Azure cloud region.
 *
 * Some of the entity fields are perpetual and cannot be changed since the region is created in dao.
 *
 * Azure cloud region perpetual fields are: {@link AbstractCloudRegion#getRegionCode()}, {@link #resourceGroup},
 * {@link #storageAccount}, {@link #subscription}.
 */
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class AzureRegion extends AbstractCloudRegion {

    private CloudProvider provider = CloudProvider.AZURE;
    private String resourceGroup;
    private String storageAccount;
    private String storageAccountKey;
    private AzurePolicy azurePolicy;
    private String corsRules;
    private String subscription;
    private String authFile;

    // Not empty
    private String sshPublicKeyPath;
    private String meterRegionName;
    private String azureApiUrl;
    private String priceOfferId;
    private Boolean enterpriseAgreements;

    public CloudProvider getProvider() {
        return CloudProvider.AZURE;
    }
}
