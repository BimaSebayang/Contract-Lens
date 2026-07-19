package com.contractlens.service.module.proxy.service;

import com.contractlens.common.dto.AnalyzeSpecQueryResult;
import com.contractlens.common.dto.ContractDifference;
import com.contractlens.common.dto.GatewayRequest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface GatewayService {
    ResponseEntity<?> forward(GatewayRequest gatewayRequest, HttpHeaders headers);

    ResponseEntity<List<ContractDifference>> queryforward(GatewayRequest gatewayRequest, HttpHeaders headers);
}
