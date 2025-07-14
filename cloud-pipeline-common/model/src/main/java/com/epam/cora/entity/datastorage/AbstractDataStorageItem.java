package com.epam.cora.entity.datastorage;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;

import java.util.Comparator;
import java.util.Map;

@Getter
@Setter
@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, property = "type", defaultImpl = DataStorageFile.class)
@JsonSubTypes({@JsonSubTypes.Type(value = DataStorageFile.class, name = "File"), @JsonSubTypes.Type(value = DataStorageFolder.class, name = "Folder")})
public abstract class AbstractDataStorageItem {

    public static final String DELIMITER = "/";

    private String name;
    private String path;
    private Map<String, String> labels;
    private Map<String, String> tags;
    @Setter(AccessLevel.PACKAGE)
    private DataStorageItemType type;

    @JsonIgnore
    public static Comparator<AbstractDataStorageItem> getStorageItemComparator() {
        return (o1, o2) -> {
            if (!o1.getPath().equals(o2.getPath())) {
                return o1.getPath().compareTo(o2.getPath());
            }
            if (o1.getType() != o2.getType()) {
                return o1.getType() == DataStorageItemType.File ? -1 : 1;
            }
            return 0;
        };
    }

    public String getAbsolutePath() {
        return this.getPath().startsWith(DELIMITER) ? this.getPath() : DELIMITER + this.getPath();
    }
}
