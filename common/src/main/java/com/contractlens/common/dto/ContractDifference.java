package com.contractlens.common.dto;

import com.contractlens.common.enums.DataType;
import com.contractlens.common.enums.DifferenceType;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ContractDifference {

    private DifferenceType type;

    private String path;

    private DataType expected;

    private DataType actual;

}
