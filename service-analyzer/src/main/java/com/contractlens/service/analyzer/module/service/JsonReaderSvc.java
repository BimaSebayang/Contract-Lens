package com.contractlens.service.analyzer.module.service;

import com.contractlens.common.dto.ContractSnapshot;

import java.io.InputStream;

public interface JsonReaderSvc {

    ContractSnapshot readContract(String contract);
    ContractSnapshot readContract(byte[] contract);
    ContractSnapshot readContract(InputStream contract);

}
