package com.contractlens.service.analyzer.module.service.impl;

import com.contractlens.service.analyzer.module.service.JsonParser;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.io.InputStream;

@Service
@AllArgsConstructor
public class JsonParserImpl implements JsonParser {

    private final ObjectMapper objectMapper;

    @Override
    public JsonNode parse(String content) {
        return null;
    }

    @Override
    public JsonNode parse(byte[] content) {
        return null;
    }

    @Override
    public JsonNode parse(InputStream inputStream) {
        return null;
    }
}
