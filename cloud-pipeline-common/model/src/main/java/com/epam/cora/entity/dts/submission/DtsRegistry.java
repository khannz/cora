package com.epam.cora.entity.dts.submission;

import com.epam.cora.entity.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;
import java.util.Map;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class DtsRegistry extends BaseEntity {
    private String url;
    private List<String> prefixes;
    private Map<String, String> preferences;
    private boolean schedulable = false;
}
