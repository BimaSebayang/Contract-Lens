package com.contractlens.service.analyzer.module.proxy.service;

import com.contractlens.common.dto.AnalyzeSpecQuery;
import com.contractlens.common.dto.CompatibilityPlan;
import com.contractlens.common.dto.ContractDifference;
import com.contractlens.common.dto.GatewayRequest;
import com.contractlens.service.analyzer.db.mongo.dao.AnalyzeSpecDocument;
import com.contractlens.service.analyzer.db.mongo.service.AnalyzeSpecDocumentQueryService;

import com.contractlens.service.analyzer.module.reader.service.CompatibilityPlanService;
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
public class GatewayService {

    private final AnalyzeSpecDocumentQueryService queryService;
    private final CompatibilityPlanService compatibilityPlanService;

    public ResponseEntity<List<ContractDifference>> queryforward(GatewayRequest request, HttpHeaders headers) {
        log.info("forward for : request = {}, headers = {}",request,headers);

        AnalyzeSpecQuery analyzeSpecQuery = getAnalyzeSpecQuery(request,"/gateway-inquiry/");

        AnalyzeSpecDocument analyzeSpecDocument = queryService.getMainBaseLine(analyzeSpecQuery);

        if(Objects.isNull(analyzeSpecDocument)){
            return ResponseEntity.ok(new ArrayList<>());
        }


        return new ResponseEntity<>(
                analyzeSpecDocument.getResponseBodyCompareResult().getDifferences(), HttpStatusCode.valueOf(200)
        );
    }

    private static AnalyzeSpecQuery getAnalyzeSpecQuery(GatewayRequest request, String prefixUrl) {
        UUID tokenId = request.tokenId();

        StringBuilder url = new StringBuilder();

        String prefix =  prefixUrl + tokenId+"/";

        url.append(request.path().substring(prefix.length()));

        if (request.query() != null && !request.query().isBlank()) {

            url.append("?");

            url.append(request.query());

        }

        return new AnalyzeSpecQuery(
                url.toString(), request.method(), request.tokenId()
        );
    }

    public ResponseEntity<CompatibilityPlan> compabilityInquiry(
            GatewayRequest gatewayRequest
    ) {
        AnalyzeSpecQuery analyzeSpecQuery = getAnalyzeSpecQuery(gatewayRequest,"/compability-inquiry/");

        AnalyzeSpecDocument analyzeSpecDocument = queryService.getLatestData(analyzeSpecQuery);

        if(Objects.isNull(analyzeSpecDocument)){
            return ResponseEntity.ok(new CompatibilityPlan());
        }


        CompatibilityPlan plan = generate(analyzeSpecDocument);

        return ResponseEntity.ok(plan);
    }

    public CompatibilityPlan generate(AnalyzeSpecDocument document) {
        return compatibilityPlanService.generate(document);
    }
}
