package com.epam.cora.esa.model.nfsobserver;

import lombok.AllArgsConstructor;
import lombok.Value;

@Value
@AllArgsConstructor
public class NFSObserverEvent {

    Long timestamp;
    NFSObserverEventType eventType;
    String storage;
    String filePath;
    String filePathTo;

    public NFSObserverEvent(final Long timestamp, final NFSObserverEventType eventType, final String storage, final String filePath) {
        this(timestamp, eventType, storage, filePath, null);
    }
}
