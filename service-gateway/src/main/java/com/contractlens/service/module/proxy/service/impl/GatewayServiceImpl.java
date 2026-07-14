package com.contractlens.service.module.proxy.service.impl;

import com.contractlens.common.dto.GatewayRequest;
import com.contractlens.service.module.proxy.service.GatewayService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class GatewayServiceImpl implements GatewayService {

    private final RestTemplate restTemplate;

    @Override
    public ResponseEntity<?> forward(GatewayRequest request, HttpHeaders headers) {
        log.info("forward for : request = {}, headers = {}",request,headers);

        //TODO :: Get By Token Id PAthnya nanti
        String targetUrl = "http://localhost:9001/";

        UUID tokenId = request.tokenId();

        StringBuilder url = new StringBuilder();

        url.append(targetUrl);

        String prefix = "/gateway/" + tokenId+"/";


        url.append(request.path().substring(prefix.length()));

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
