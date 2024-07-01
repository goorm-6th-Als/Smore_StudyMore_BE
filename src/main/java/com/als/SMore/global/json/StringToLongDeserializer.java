package com.als.SMore.global.json;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;

import java.io.IOException;

public class StringToLongDeserializer extends JsonDeserializer<Long> {

    @Override
    public Long deserialize(JsonParser p, DeserializationContext ctxt) throws IOException {
        JsonNode node = p.getCodec().readTree(p);
        String value = node.asText();
        try {
            return value != null ? Long.valueOf(value) : null;
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("PK 형변환이 불가능합니다");
        }
    }
}