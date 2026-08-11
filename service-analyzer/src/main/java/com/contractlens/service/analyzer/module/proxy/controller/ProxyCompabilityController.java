package com.contractlens.service.analyzer.module.proxy.controller;

import com.contractlens.common.dto.CompatibilityPlan;
import com.contractlens.common.dto.GatewayRequest;
import com.contractlens.service.analyzer.module.proxy.service.GatewayService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/compability-inquiry")
public class ProxyCompabilityController {

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
    public ResponseEntity<CompatibilityPlan> compabilityInquiryBody(
            HttpServletRequest request,
            @PathVariable UUID tokenId) throws IOException {

        GatewayRequest gatewayRequest = new GatewayRequest(
                tokenId,
                request.getMethod(),
                request.getRequestURI(),
                request.getQueryString(),
                request.getInputStream().readAllBytes()
        );

        return gatewayService.compabilityInquiry(gatewayRequest);

    }
}

