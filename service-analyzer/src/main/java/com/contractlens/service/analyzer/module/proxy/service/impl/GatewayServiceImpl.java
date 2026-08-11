package com.contractlens.service.analyzer.module.proxy.service.impl;

import com.contractlens.common.dto.AnalyzeSpecQuery;
import com.contractlens.common.dto.ContractDifference;
import com.contractlens.common.dto.GatewayRequest;
import com.contractlens.service.analyzer.db.mongo.dao.AnalyzeSpecDocument;
import com.contractlens.service.analyzer.db.mongo.service.AnalyzeSpecDocumentQueryService;
import com.contractlens.service.analyzer.module.proxy.service.GatewayService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

@Service
@Slf4j
@AllArgsConstructor
public class GatewayServiceImpl implements GatewayService {

    private final AnalyzeSpecDocumentQueryService queryService;

    @Override
    public ResponseEntity<List<ContractDifference>> queryforward(GatewayRequest request, HttpHeaders headers) {
        log.info("forward for : request = {}, headers = {}",request,headers);

        AnalyzeSpecQuery analyzeSpecQuery = getAnalyzeSpecQuery(request);

        AnalyzeSpecDocument analyzeSpecDocument = queryService.getMainBaseLine(analyzeSpecQuery);

        if(Objects.isNull(analyzeSpecDocument)){
            return ResponseEntity.ok(new ArrayList<>());
        }


        return new ResponseEntity<>(
                analyzeSpecDocument.getResponseBodyCompareResult().getDifferences(), HttpStatusCode.valueOf(200)
        );
    }

    private static AnalyzeSpecQuery getAnalyzeSpecQuery(GatewayRequest request) {
        UUID tokenId = request.tokenId();

        StringBuilder url = new StringBuilder();

        String prefix = "/gateway-inquiry/" + tokenId+"/";

        url.append(request.path().substring(prefix.length()));

        if (request.query() != null && !request.query().isBlank()) {

            url.append("?");

            url.append(request.query());

        }

        return new AnalyzeSpecQuery(
                url.toString(), request.method(), request.tokenId()
        );
    }

}
