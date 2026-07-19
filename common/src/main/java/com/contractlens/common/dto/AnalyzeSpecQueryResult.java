package com.contractlens.common.dto;

import com.contractlens.common.enums.DataType;
import com.contractlens.common.enums.DifferenceType;


public record AnalyzeSpecQueryResult(
        String path,
        DifferenceType difference,
        DataType expected,
        DataType actual
) {
}
