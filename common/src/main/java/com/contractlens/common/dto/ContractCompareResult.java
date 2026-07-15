package com.contractlens.common.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class ContractCompareResult {

    private boolean matched = true;

    private List<ContractDifference> differences = new ArrayList<>();

}
