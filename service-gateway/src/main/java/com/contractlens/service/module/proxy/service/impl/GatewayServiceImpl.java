package com.contractlens.service.module.proxy.service.impl;

import com.contractlens.common.constant.ServiceConstants;
import com.contractlens.common.dto.*;
import com.contractlens.service.integration.message.RabbitEventPublisher;
import com.contractlens.service.module.proxy.service.GatewayService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.time.Duration;
import java.time.Instant;
import java.util.List;
import java.util.UUID;

@Service
@Slf4j
public class GatewayServiceImpl implements GatewayService {

    private final RestTemplate restTemplate;

    @Qualifier(ServiceConstants.GATEWAY_ANALYZER_PUBLISHER)
    @Autowired
    private RabbitEventPublisher rabbitEventPublisher;

    private final RouteResolverService routeResolverService;

    public GatewayServiceImpl(RestTemplate restTemplate, RouteResolverService routeResolverService) {
        this.restTemplate = restTemplate;
        this.routeResolverService = routeResolverService;
    }

    @Override
    public ResponseEntity<?> forward(GatewayRequest request, HttpHeaders headers) {
        log.info("forward for : request = {}, headers = {}",request,headers);

        String targetUrl = routeResolverService.resolve(request.tokenId().toString()).getTargetUrl();

        UUID tokenId = request.tokenId();

        StringBuilder url = new StringBuilder();

        url.append(targetUrl);

        String prefix = "/gateway/" + tokenId+"/";


        url.append(request.path().substring(prefix.length()));

        if (request.query() != null && !request.query().isBlank()) {

            url.append("?");

            url.append(request.query());

        }

        HttpEntity<byte[]> entity;
        if(request.body().length>0){
            entity = new HttpEntity<>(
                request.body(),
                headers
            );
        }else {
            entity = new HttpEntity<>(headers);
        }


        Instant start = Instant.now();
        ResponseEntity<byte[]> response = restTemplate.exchange(
                url.toString(),
                HttpMethod.valueOf(request.method()),
                entity,
                byte[].class
        );
        Instant end = Instant.now();

        rabbitEventPublisher.publish(GatewayTransactionEvent.builder()
                .requestTime(start)
                .transactionId(UUID.randomUUID())
                .tokenId(tokenId)
                .method(request.method())
                .targetUrl(url.toString())
                .statusCode(response.getStatusCode().value())
                .duration(Duration.between(start, end).toMillis())
                .requestHeaders(headers.toSingleValueMap())
                .requestBody(request.body())
                .responseHeaders(response.getHeaders().toSingleValueMap())
                .responseBody(response.getBody())
                .build());


        HttpHeaders responseHeaders = new HttpHeaders();

        response.getHeaders().forEach((name, values) -> {

            if (!name.equalsIgnoreCase(HttpHeaders.TRANSFER_ENCODING)
                    && !name.equalsIgnoreCase(HttpHeaders.CONTENT_LENGTH)
                    && !name.equalsIgnoreCase(HttpHeaders.CONNECTION)
                    && !name.equalsIgnoreCase("Proxy-Connection")) {

                responseHeaders.put(name, values);

            }

        });

        return ResponseEntity
                .status(response.getStatusCode())
                .headers(responseHeaders)
                .body(response.getBody());

    }

    @Override
    public ResponseEntity<List<ContractDifference>> queryforward(GatewayRequest request, HttpHeaders headers) {
        log.info("queryforward for : request = {}, headers = {}",request,headers);

        //TODO :: Get By Token Id PAthnya nanti
        String targetUrl = routeResolverService.resolve(request.tokenId().toString()).getTargetUrl();

        UUID tokenId = request.tokenId();

        StringBuilder url = new StringBuilder();

        url.append(targetUrl);

        String prefix = "/gateway-inquiry/" + tokenId+"/";


        url.append(request.path().substring(prefix.length()));

        if (request.query() != null && !request.query().isBlank()) {

            url.append("?");

            url.append(request.query());

        }


        ResponseEntity<List<ContractDifference>> response = restTemplate.exchange(
                url.toString(),
                HttpMethod.valueOf(request.method()),
                null,
                new ParameterizedTypeReference<>() {}
        );


        return new ResponseEntity<>(response.getBody(), HttpStatusCode.valueOf(200)
        );
    }


}
