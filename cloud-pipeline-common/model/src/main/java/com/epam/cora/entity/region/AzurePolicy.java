package com.epam.cora.entity.region;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class AzurePolicy {

    /**
     * The minimum IP address of the range of IP addresses.
     */
    private String ipMin;

    /**
     * The maximum IP address of the range of IP addresses.
     */
    private String ipMax;
}
