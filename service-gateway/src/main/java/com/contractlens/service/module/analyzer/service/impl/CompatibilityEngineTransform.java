package com.contractlens.service.module.analyzer.service.impl;

import com.contractlens.common.dto.GatewayRequest;
import com.contractlens.common.enums.DataType;
import com.contractlens.common.enums.TransformationType;
import com.contractlens.common.util.DigestUtility;
import com.contractlens.service.db.redis.dao.CompatibilityPlan;
import com.contractlens.service.db.redis.service.CompatibilityPlanService;
import com.contractlens.service.module.analyzer.service.CompatibilityEngine;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.*;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Objects;
import java.util.UUID;

@Service("engineTransform")
public class CompatibilityEngineTransform implements CompatibilityEngine {

    @Value("${external.analyze-api.url:}")
    private String analyzeApiUrl;

    private final CompatibilityPlanService compatibilityPlanService;
    private final RestTemplate restTemplate;
    private final ObjectMapper objectMapper;

    public CompatibilityEngineTransform(CompatibilityPlanService compatibilityPlanService, RestTemplate restTemplate, ObjectMapper objectMapper) {
        this.compatibilityPlanService = compatibilityPlanService;
        this.restTemplate = restTemplate;
        this.objectMapper = objectMapper;
    }

    @SneakyThrows
    @Override
    public byte[] transform(
            CompatibilityPlan plan,
            byte[] responseBody
    ) {
        if (plan == null
                || plan.getTransformations() == null
                || plan.getTransformations().isEmpty()
                || responseBody == null
                || responseBody.length == 0) {
            return responseBody;
        }

        JsonNode root = objectMapper.readTree(responseBody);

        for (CompatibilityPlan.Transformation transformation
                : plan.getTransformations()) {

            if (TransformationType.TYPE_CONVERSION
                    .equals(transformation.getType())) {

                transformType(
                        root,
                        transformation
                );
            }
        }

        return objectMapper.writeValueAsBytes(root);
    }

    @Override
    public CompatibilityPlan callPlan(UUID tokenId, String urlPath, String method, GatewayRequest request) {
        CompatibilityPlan compatibilityPlan = compatibilityPlanService.read(
                DigestUtility.compabilityPlanId(
                        tokenId.toString(),
                        method,
                        urlPath

                )
        );

        if(Objects.isNull(compatibilityPlan)){
            String targetUrl = analyzeApiUrl+"/compability-inquiry/"+tokenId+"/";
            String url = targetUrl +
                    urlPath;


            HttpEntity<byte[]> entity;
            if(request.body().length>0){
                entity = new HttpEntity<>(
                        request.body(),
                        new HttpHeaders()
                );
            }else {
                entity = new HttpEntity<>(new HttpHeaders());
            }


            ResponseEntity<CompatibilityPlan> response = restTemplate.exchange(
                    url,
                HttpMethod.valueOf(method),
                entity,
                CompatibilityPlan.class
            );

            return response.getBody();
        }

        return compatibilityPlan;
    }

    private String toJsonPointer(String fieldPath) {
        if (fieldPath == null || fieldPath.isBlank() || "$".equals(fieldPath)) {
            return "";
        }

        return fieldPath
                .substring(1)
                .replace(".", "/");
    }

    private void transformType(
            JsonNode root,
            CompatibilityPlan.Transformation transformation
    ) {
        JsonNode value = root.at(toJsonPointer(transformation.getFieldPath()));

        if (value.isMissingNode() || value.isNull()) {
            return;
        }

        DataType sourceType = transformation.getSourceType();
        DataType targetType = transformation.getTargetType();

        JsonNode transformedValue =
                convert(value, sourceType, targetType);

        replaceValue(
                root,
                transformation.getFieldPath(),
                transformedValue
        );
    }

    private JsonNode convert(
            JsonNode value,
            DataType sourceType,
            DataType targetType
    ) {
        if (sourceType == targetType) {
            return value;
        }

        return switch (targetType) {

            case STRING ->
                    TextNode.valueOf(value.asText());

            case INTEGER ->
                    IntNode.valueOf(
                            parseInteger(value, sourceType)
                    );

            case LONG ->
                    LongNode.valueOf(
                            parseLong(value, sourceType)
                    );

            case DOUBLE ->
                    DoubleNode.valueOf(
                            parseDouble(value, sourceType)
                    );

            case BOOLEAN ->
                    BooleanNode.valueOf(
                            parseBoolean(value, sourceType)
                    );

            default ->
                    throw new IllegalArgumentException(
                            "Unsupported target type: " + targetType
                    );
        };
    }

    private int parseInteger(
            JsonNode value,
            DataType sourceType
    ) {
        return switch (sourceType) {
            case INTEGER ->
                    value.asInt();

            case LONG ->
                    Math.toIntExact(value.asLong());

            case DOUBLE ->
                    (int) value.asDouble();

            case STRING ->
                    Integer.parseInt(value.asText().trim());

            default ->
                    throw unsupportedConversion(
                            sourceType,
                            DataType.INTEGER
                    );
        };
    }

    private long parseLong(
            JsonNode value,
            DataType sourceType
    ) {
        return switch (sourceType) {
            case INTEGER ->
                    value.asLong();

            case LONG ->
                    value.asLong();

            case DOUBLE ->
                    (long) value.asDouble();

            case STRING ->
                    Long.parseLong(value.asText().trim());

            default ->
                    throw unsupportedConversion(
                            sourceType,
                            DataType.LONG
                    );
        };
    }

    private double parseDouble(
            JsonNode value,
            DataType sourceType
    ) {
        return switch (sourceType) {
            case INTEGER ->
                    value.asDouble();

            case LONG ->
                    value.asDouble();

            case DOUBLE ->
                    value.asDouble();

            case STRING ->
                    Double.parseDouble(value.asText().trim());

            default ->
                    throw unsupportedConversion(
                            sourceType,
                            DataType.DOUBLE
                    );
        };
    }

    private boolean parseBoolean(
            JsonNode value,
            DataType sourceType
    ) {
        return switch (sourceType) {
            case BOOLEAN ->
                    value.asBoolean();

            case STRING ->
                    Boolean.parseBoolean(
                            value.asText().trim()
                    );

            default ->
                    throw unsupportedConversion(
                            sourceType,
                            DataType.BOOLEAN
                    );
        };
    }

    private void replaceValue(
            JsonNode root,
            String fieldPath,
            JsonNode value
    ) {
        if ("$".equals(fieldPath)) {
            return;
        }

        String parentPath = fieldPath.substring(
                0,
                fieldPath.lastIndexOf('.')
        );

        String fieldName = fieldPath.substring(
                fieldPath.lastIndexOf('.') + 1
        );

        JsonNode parent = root.at(toJsonPointer(parentPath));

        if (parent instanceof ObjectNode objectNode) {
            objectNode.set(fieldName, value);
            return;
        }

        if (parent instanceof ArrayNode arrayNode) {
            int index = Integer.parseInt(fieldName);
            arrayNode.set(index, value);
            return;
        }

        throw new IllegalArgumentException(
                "Unable to replace field: " + fieldPath
        );
    }

    private IllegalArgumentException unsupportedConversion(
            DataType sourceType,
            DataType targetType
    ) {
        return new IllegalArgumentException(
                "Unsupported conversion: "
                        + sourceType
                        + " -> "
                        + targetType
        );
    }

}
