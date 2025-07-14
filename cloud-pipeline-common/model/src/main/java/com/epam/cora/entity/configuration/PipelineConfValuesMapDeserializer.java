package com.epam.cora.entity.configuration;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.NullNode;

import java.io.IOException;
import java.util.*;

public class PipelineConfValuesMapDeserializer extends JsonDeserializer<Map<String, PipeConfValueVO>> {

    private static final String VALUE_FIELD = "value";
    private static final String TYPE_FIELD = "type";
    private static final String REQUIRED_FIELD = "required";
    private static final String ENUM_FIELD = "enum";
    private static final NullNode NULL_NODE = NullNode.getInstance();

    @Override
    public Map<String, PipeConfValueVO> deserialize(JsonParser p, DeserializationContext ctxt)
            throws IOException {
        Map<String, PipeConfValueVO> parameters = new LinkedHashMap<>();
        JsonNode treeNode = p.readValueAsTree();
        Iterator<String> childNames = treeNode.fieldNames();
        while (childNames.hasNext()) {
            String name = childNames.next();
            JsonNode child = treeNode.get(name);
            PipeConfValueVO parameter = new PipeConfValueVO();
            if (child.isValueNode()) {
                parameter.setValue(child.asText().trim());
            } else {
                JsonNode value = child.get(VALUE_FIELD);
                if (hasValue(value)) {
                    parameter.setValue(value.asText().trim());
                }
                JsonNode type = child.get(TYPE_FIELD);
                if (hasValue(type)) {
                    parameter.setType(type.asText());
                }
                JsonNode required = child.get(REQUIRED_FIELD);
                if (hasValue(required)) {
                    parameter.setRequired(required.asBoolean());
                }
                JsonNode availableValuesNode = child.get(ENUM_FIELD);
                if (hasValue(availableValuesNode) && availableValuesNode.isArray()) {
                    List<String> availableValues = new ArrayList<>();
                    availableValuesNode.forEach(arrayItem -> availableValues.add(arrayItem.asText()));
                    parameter.setAvailableValues(availableValues);
                }
            }
            parameters.put(name, parameter);
        }
        return parameters;
    }

    private boolean hasValue(final JsonNode required) {
        return required != null && !required.equals(NULL_NODE);
    }
}
