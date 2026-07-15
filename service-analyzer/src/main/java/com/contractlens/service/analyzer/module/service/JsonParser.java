package com.contractlens.service.analyzer.module.service;

import com.fasterxml.jackson.databind.JsonNode;

import java.io.InputStream;

public interface JsonParser {

    JsonNode parse(String content);

    JsonNode parse(byte[] content);

    JsonNode parse(InputStream inputStream);

}
