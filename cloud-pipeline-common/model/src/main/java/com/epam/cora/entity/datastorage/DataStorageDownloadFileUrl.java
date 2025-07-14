package com.epam.cora.entity.datastorage;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
public class DataStorageDownloadFileUrl {
    private String url;
    private Date expires;
    private String tagValue;
}
