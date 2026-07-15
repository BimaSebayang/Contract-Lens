package com.contractlens.common.dto;

import lombok.Getter;

import java.util.LinkedHashMap;
import java.util.Map;

@Getter
public class ContractSnapshot {

    private final Map<String, SchemaField> fields = new LinkedHashMap<>();

    public void addField(SchemaField field) {
        fields.put(field.getPath(), field);
    }

}
