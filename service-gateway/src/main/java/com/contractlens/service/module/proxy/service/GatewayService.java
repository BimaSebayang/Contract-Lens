package com.contractlens.service.module.proxy.service;

import com.contractlens.common.dto.GatewayRequest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;

public interface GatewayService {
    ResponseEntity<?> forward(GatewayRequest gatewayRequest, HttpHeaders headers);
}
