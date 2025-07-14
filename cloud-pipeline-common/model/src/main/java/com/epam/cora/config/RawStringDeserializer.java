package com.epam.cora.config;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;

import java.io.IOException;

public class RawStringDeserializer extends StdDeserializer<String> {

    public RawStringDeserializer() {
        super(String.class);
    }

    @Override
    public String deserialize(final JsonParser parser, final DeserializationContext context) throws IOException {
        return parser.getCodec().readTree(parser).toString();
    }
}
