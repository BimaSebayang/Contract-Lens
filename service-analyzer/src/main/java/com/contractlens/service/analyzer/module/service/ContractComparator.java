package com.contractlens.service.analyzer.module.service;

import com.contractlens.common.dto.ContractCompareResult;
import com.contractlens.common.dto.ContractSnapshot;

public interface ContractComparator {

    ContractCompareResult compare(
            ContractSnapshot baseline,
            ContractSnapshot runtime
    );

    ContractCompareResult compare(
            String baseline,
            String runtime
    );

}
