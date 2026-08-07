package com.contractlens.service.analyzer.module.proxy.service;

import com.contractlens.common.dto.ContractDifference;
import com.contractlens.common.dto.GatewayRequest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface GatewayService {

    ResponseEntity<List<ContractDifference>> queryforward(GatewayRequest gatewayRequest, HttpHeaders headers);

}
