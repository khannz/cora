package com.epam.cora.entity.cluster;

import io.fabric8.kubernetes.api.model.Quantity;

import java.util.HashMap;
import java.util.Map;

public final class QuantitiesConverter {

    private QuantitiesConverter() {
    }

    public static Map<String, String> convertQuantityMap(Map<String, Quantity> map) {
        if (map != null) {
            Map<String, String> result = new HashMap<>();
            map.forEach((key, quantity) -> result.put(key, quantity.getAmount()));
            return result;
        }
        return null;
    }
}
