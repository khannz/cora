package com.epam.cora.entity.datastorage;

import lombok.Getter;
import lombok.Setter;
import org.apache.commons.collections4.MapUtils;

import java.io.File;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Getter
@Setter
public class DataStorageFile extends AbstractDataStorageItem {
    private Long size;
    private String changed;
    private String version;
    private Boolean deleteMarker;
    private Boolean isHidden = false;
    private boolean latest = true;
    private Map<String, AbstractDataStorageItem> versions;

    public DataStorageFile() {
        super();
        this.setType(DataStorageItemType.File);
    }

    public DataStorageFile copy(DataStorageFile other) {
        if (other == null) {
            return null;
        }
        DataStorageFile file = new DataStorageFile();
        file.setName(other.getName());
        file.setPath(other.getPath());
        file.setSize(other.getSize());
        file.setChanged(other.getChanged());
        file.setLabels(other.getLabels());
        file.setVersion(other.getVersion());
        file.setDeleteMarker(other.getDeleteMarker());
        file.setIsHidden(other.getIsHidden());
        file.setLatest(other.isLatest());
        return file;
    }

    public DataStorageFile copy() {
        DataStorageFile file = new DataStorageFile();
        file.setName(this.getName());
        file.setPath(this.getPath());
        file.setSize(this.getSize());
        file.setChanged(this.getChanged());
        if (MapUtils.isNotEmpty(getVersions())) {
            file.setVersions(new HashMap<>(this.getVersions()));
        }
        if (MapUtils.isNotEmpty(getLabels())) {
            file.setLabels(new HashMap<>(this.getLabels()));
        }
        file.setVersion(this.getVersion());
        file.setDeleteMarker(this.getDeleteMarker());
        file.setIsHidden(this.getIsHidden());
        file.setLatest(this.isLatest());
        return file;
    }

    public DataStorageFile(String path, File file) {
        setName(file.getName());
        setPath(path);
        setSize(file.length());
        setChanged(new Date(file.lastModified()).toString());
    }
}
