package com.contractlens.service.analyzer.module.service.impl;

import com.contractlens.common.dto.ContractSnapshot;
import com.contractlens.common.dto.SchemaField;
import com.contractlens.common.enums.DataType;
import com.contractlens.service.analyzer.module.service.NodeSniper;
import com.fasterxml.jackson.databind.JsonNode;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Iterator;
import java.util.Map;

@Service
@AllArgsConstructor
public class NodeSniperImpl implements NodeSniper {
    @Override
    public ContractSnapshot normalizer(JsonNode jsonNode) {
        ContractSnapshot snapshot = new ContractSnapshot();

        traverse("$", jsonNode, snapshot);

        return snapshot;
    }

    private void traverse(
            String path,
            JsonNode node,
            ContractSnapshot snapshot
    ) {

        traverseNull(path, node, snapshot);
        traverseObject(path, node, snapshot);
        traverseArray(path, node, snapshot);

        DataType resolveType = resolveType(node);
        Object extractValue = extractValue(node);

        snapshot.addField(
                createField(
                        path,
                        resolveType,
                        extractValue
                )
        );

    }

    private void traverseNull(   String path,
                                 JsonNode node,
                                 ContractSnapshot snapshot){
        if (node == null || node.isNull()) {

            snapshot.addField(createField(path, DataType.NULL, null));

        }
    }

    private void traverseArray(   String path,
                                  JsonNode node,
                                  ContractSnapshot snapshot){
        if (node.isArray()) {

            snapshot.addField(createField(path, DataType.ARRAY, null));

            if(node.size()>0 && node.get(0).isObject()) {
                traverse(
                        path + "[]",
                        node.get(0),
                        snapshot
                );
            }

        }
    }

    private void traverseObject(
            String path,
            JsonNode node,
            ContractSnapshot snapshot
    ){
        if (node.isObject()) {

            Iterator<Map.Entry<String, JsonNode>> fields = node.fields();

            while (fields.hasNext()) {

                Map.Entry<String, JsonNode> field = fields.next();

                traverse(
                        path + "." + field.getKey(),
                        field.getValue(),
                        snapshot
                );

            }

        }
    }


    private SchemaField createField(
            String path,
            DataType dataType,
            Object value
    ) {

        SchemaField field = new SchemaField();

        field.setPath(path);
        field.setDataType(dataType);
        field.setSampleValue(value);

        return field;

    }

    private DataType resolveType(JsonNode node) {

        if (node.isTextual()) {
            return DataType.STRING;
        }

        if (node.isInt()) {
            return DataType.INTEGER;
        }

        if (node.isLong()) {
            return DataType.LONG;
        }

        if (node.isBoolean()) {
            return DataType.BOOLEAN;
        }

        if (node.isFloatingPointNumber()) {
            return DataType.DOUBLE;
        }

        if(node.isArray()){
            return DataType.ARRAY;
        }

        if(node.isObject()){
            return DataType.HASHMAP;
        }

        if(node.isNull()){
            return DataType.NULL;
        }

        return DataType.UNKNOWN;

    }

    private Object extractValue(JsonNode node) {

        if (node.isTextual()) {
            return node.asText();
        }

        if (node.isInt()) {
            return node.asInt();
        }

        if (node.isLong()) {
            return node.asLong();
        }

        if (node.isBoolean()) {
            return node.asBoolean();
        }

        if (node.isFloatingPointNumber()) {
            return node.asDouble();
        }

        if(node.isNull()){
            return null;
        }

        return node.toString();

    }

}
