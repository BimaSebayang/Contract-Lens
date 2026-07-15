package com.contractlens.service.analyzer.module.service;

import com.contractlens.common.dto.ContractSnapshot;
import com.fasterxml.jackson.databind.JsonNode;

public interface NodeSniper {

    ContractSnapshot normalizer(JsonNode jsonNode);

}
