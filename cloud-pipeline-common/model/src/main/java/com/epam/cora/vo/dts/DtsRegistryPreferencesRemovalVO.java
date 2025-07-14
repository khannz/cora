package com.epam.cora.vo.dts;

import lombok.Value;

import java.util.List;

@Value
public class DtsRegistryPreferencesRemovalVO {

    List<String> preferenceKeysToRemove;
}
