package com.contractlens.service.module.proxy.controller;

import com.contractlens.common.dto.AnalyzeSpecQueryResult;
import com.contractlens.common.dto.ContractDifference;
import com.contractlens.common.dto.GatewayRequest;
import com.contractlens.service.module.proxy.service.GatewayService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/gateway-inquiry")
public class ProxyQueryController {

    private final GatewayService gatewayService;

    @RequestMapping(
            value = "/{tokenId}/**",
            method = {
                    RequestMethod.GET,
                    RequestMethod.POST,
                    RequestMethod.PUT,
                    RequestMethod.DELETE,
                    RequestMethod.PATCH,
                    RequestMethod.OPTIONS,
                    RequestMethod.HEAD
            })
    public ResponseEntity<List<ContractDifference>> proxy(
            HttpServletRequest request,
            @PathVariable UUID tokenId,
            @RequestHeader HttpHeaders headers) throws IOException {

        GatewayRequest gatewayRequest = new GatewayRequest(
                tokenId,
                request.getMethod(),
                request.getRequestURI(),
                request.getQueryString(),
                request.getInputStream().readAllBytes()
        );

        return gatewayService.queryforward(gatewayRequest,headers);

    }
}
