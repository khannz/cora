package com.epam.cora.entity.scan;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.HashMap;
import java.util.Map;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ToolDependency {

    private long toolId;
    private String toolVersion;
    private String name;
    private String version;
    private Ecosystem ecosystem;
    private String description;

    @Getter
    @AllArgsConstructor
    public enum Ecosystem {

        PYTHON_DIST("Python.Dist"),
        PYTHON_PKG("Python.Pkg"),
        R_PKG("R.Pkg"),
        SYSTEM("System"),
        JAVA("Java"),
        NMP("npm"),
        SWIFT("Swift.PM"),
        CMAKE("CMAKE"),
        RUBY("Ruby.Bundle"),
        OTHER("OTHER");

        private static Map<String, Ecosystem> map;

        static {
            map = new HashMap<>();
            map.put(PYTHON_DIST.value, PYTHON_DIST);
            map.put(R_PKG.value, R_PKG);
            map.put(SYSTEM.value, SYSTEM);
        }

        private String value;

        public static Ecosystem getByName(String name) {
            return map.get(name);
        }

        @JsonCreator
        public static Ecosystem forValue(String value) {
            Ecosystem ecosystem = map.get(value);
            return ecosystem != null ? ecosystem : OTHER;
        }

        @JsonValue
        @Override
        public String toString() {
            return value;
        }
    }
}
