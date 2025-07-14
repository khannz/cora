package com.epam.cora.entity.configuration;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Setter
@Getter
public class PipeConfValueVO {

    public static final String DEFAULT_TYPE = "string";
    public static final String DEFAULT_VALUE = "";
    public static final boolean DEFAULT_REQUIRED = false;
    protected static final List<String> DEFAULT_AVAIL_VALUES = new ArrayList<>();

    @JsonProperty(value = "value")
    private String value;

    @JsonProperty(value = "type")
    private String type;

    @JsonProperty(value = "required")
    private boolean required;

    @JsonProperty(value = "enum")
    private List<String> availableValues;

    PipeConfValueVO() {
        this(DEFAULT_VALUE, DEFAULT_TYPE, DEFAULT_REQUIRED, DEFAULT_AVAIL_VALUES);
    }

    public PipeConfValueVO(String value) {
        this(value, DEFAULT_TYPE, DEFAULT_REQUIRED, DEFAULT_AVAIL_VALUES);
    }

    public PipeConfValueVO(String value, String type) {
        this(value, type, DEFAULT_REQUIRED, DEFAULT_AVAIL_VALUES);
    }

    public PipeConfValueVO(String value, String type, boolean required) {
        this(value, type, required, DEFAULT_AVAIL_VALUES);
    }

    public PipeConfValueVO(String value, String type, boolean required, List<String> availableValues) {
        this.value = value;
        this.type = type;
        this.required = required;
        this.availableValues = Collections.unmodifiableList(availableValues);
    }

}
