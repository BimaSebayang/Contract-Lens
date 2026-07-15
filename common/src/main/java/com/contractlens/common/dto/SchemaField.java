package com.contractlens.common.dto;

import com.contractlens.common.enums.DataType;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SchemaField {

    private String path;

    private DataType dataType;

    private Object sampleValue;

}
