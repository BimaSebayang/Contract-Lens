package com.contractlens.service.module.proxy.controller;

import com.contractlens.common.dto.CreateGatewayRouteRequest;
import com.contractlens.common.dto.GatewayRequest;
import com.contractlens.service.module.proxy.service.GatewayService;
import com.contractlens.service.module.proxy.service.impl.RouteResolverService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/gateway")
public class ProxyController {

    private final GatewayService gatewayService;
    private final RouteResolverService routeResolverService;


    @PostMapping
    public ResponseEntity<String> create(
            @RequestBody @Valid CreateGatewayRouteRequest request) {

        String token = routeResolverService.createToken(request);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(token);

    }

    @PutMapping
    public ResponseEntity<Void> update(
            @RequestBody @Valid CreateGatewayRouteRequest request) {

        routeResolverService.updateToken(request);

        return ResponseEntity.noContent().build();

    }



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
    public ResponseEntity<?> proxy(
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

        return gatewayService.forward(gatewayRequest,headers);

    }
}
