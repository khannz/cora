package com.epam.cora.vo.data.storage;

import lombok.Value;

import java.util.List;

@Value
public class DataStorageTagUpsertBatchRequest {

    List<DataStorageTagUpsertRequest> requests;
}
