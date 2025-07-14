package com.epam.cora.entity.configuration;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Represents FireCloud inputs and outputs for workspace method configuration.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class InputsOutputs {
    private String name;
    private String type;
    private String value;
    @Builder.Default
    private boolean optional = false;
}
