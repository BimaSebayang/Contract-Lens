package com.contractlens.service.analyzer.module.service.impl;

import com.contractlens.common.dto.ContractSnapshot;
import com.contractlens.service.analyzer.module.service.JsonParser;
import com.contractlens.service.analyzer.module.service.JsonReaderSvc;
import com.contractlens.service.analyzer.module.service.NodeSniper;
import com.fasterxml.jackson.databind.JsonNode;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.io.InputStream;

@Service
@AllArgsConstructor
public class JsonReaderSvcImpl implements JsonReaderSvc {

    private final NodeSniper nodeSniper;
    private final JsonParser jsonParser;

    @Override
    public ContractSnapshot readContract(String contract) {
        JsonNode jsonNode = jsonParser.parse(contract);
        return nodeSniper.normalizer(jsonNode);
    }

    @Override
    public ContractSnapshot readContract(byte[] contract) {
        JsonNode jsonNode = jsonParser.parse(contract);
        return nodeSniper.normalizer(jsonNode);
    }

    @Override
    public ContractSnapshot readContract(InputStream contract) {
        JsonNode jsonNode = jsonParser.parse(contract);
        return nodeSniper.normalizer(jsonNode);
    }

}
