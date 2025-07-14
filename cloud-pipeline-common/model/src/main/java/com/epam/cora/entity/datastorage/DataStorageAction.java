package com.epam.cora.entity.datastorage;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DataStorageAction {

    private Long id;
    private boolean list;
    private boolean listVersion;
    private boolean read;
    private boolean readVersion = false;
    private boolean write;
    private boolean writeVersion = false;

    @JsonIgnore
    private String bucketName;

}
