package com.contractlens.service.analyzer.module.service.impl;

import com.contractlens.common.exception.JsonParseException;
import com.contractlens.service.analyzer.module.service.JsonParser;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.InputStream;

@Service
@AllArgsConstructor
public class JsonParserImpl implements JsonParser {

    private final ObjectMapper objectMapper;

    @Override
    public JsonNode parse(String content) {
        try {
            return objectMapper.readTree(content);
        } catch (IOException e) {
            throw new JsonParseException("Failed to parse JSON String.", e);
        }
    }

    @Override
    public JsonNode parse(byte[] content) {
        try {
            return objectMapper.readTree(content);
        } catch (IOException e) {
            throw new JsonParseException("Failed to parse JSON String.", e);
        }
    }

    @Override
    public JsonNode parse(InputStream inputStream) {
        try {
            return objectMapper.readTree(inputStream);
        } catch (IOException e) {
            throw new JsonParseException("Failed to parse JSON String.", e);
        }
    }
}
