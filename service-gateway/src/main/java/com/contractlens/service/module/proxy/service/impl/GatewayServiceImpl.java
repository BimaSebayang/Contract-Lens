package com.contractlens.service.module.proxy.service.impl;

import com.contractlens.common.dto.GatewayRequest;
import com.contractlens.service.infrastructure.RestTemplateConfig;
import com.contractlens.service.module.proxy.service.GatewayService;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
@RequiredArgsConstructor
public class GatewayServiceImpl implements GatewayService {

    private final RestTemplate restTemplate;

    @Override
    public ResponseEntity<?> forward(GatewayRequest request, HttpHeaders headers) {
        //TODO :: Get By Token Id PAthnya nanti
        String targetUrl = "https://sfdcdev.axa.co.id/";

        StringBuilder url = new StringBuilder();

        url.append(targetUrl);

        url.append(request.path());

        if (request.query() != null && !request.query().isBlank()) {

            url.append("?");

            url.append(request.query());

        }

        HttpEntity<byte[]> entity = new HttpEntity<>(
                request.body(),
                headers
        );

        ResponseEntity<byte[]> response = restTemplate.exchange(
                url.toString(),
                HttpMethod.valueOf(request.method()),
                entity,
                byte[].class
        );

        return ResponseEntity
                .status(response.getStatusCode())
                .headers(response.getHeaders())
                .body(response.getBody());

    }
}
