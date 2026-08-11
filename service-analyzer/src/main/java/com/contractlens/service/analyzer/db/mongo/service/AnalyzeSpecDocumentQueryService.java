package com.contractlens.service.analyzer.db.mongo.service;

import com.contractlens.common.dto.AnalyzeSpecQuery;
import com.contractlens.common.dto.GatewayTransactionEvent;
import com.contractlens.service.analyzer.db.mongo.dao.AnalyzeSpecDocument;
import com.contractlens.service.analyzer.db.mongo.repository.AnalyzeRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Slf4j
@Service
@AllArgsConstructor
public class AnalyzeSpecDocumentQueryService {

    private final AnalyzeRepository analyzeRepository;


    public AnalyzeSpecDocument getMainBaseLine(AnalyzeSpecQuery event) {
        UUID tokenId = event.tokenId();
        String method = event.method();
        String targetUrl = event.targetUrl();

        //Find Existing BaseLine First
        List<AnalyzeSpecDocument> baselines =
                analyzeRepository.findFirstData(
                        tokenId,
                        method,
                        targetUrl
                );



        return baselines.stream()
                .findFirst()
                .orElseGet(() ->
                        analyzeRepository.findFirstData(
                                        tokenId,
                                        method,
                                        targetUrl
                                )
                                .stream()
                                .findFirst()
                                .orElse(null)
                );


    }

    public AnalyzeSpecDocument getMainBaseLine(GatewayTransactionEvent event) {
        UUID tokenId = event.getTokenId();
        String method = event.getMethod();
        String targetUrl = event.getTargetUrl();

        //Find Existing BaseLine First
        List<AnalyzeSpecDocument> baselines =
                analyzeRepository.findLatestWithBaseline(
                        tokenId,
                        method,
                        targetUrl
                );

        if (baselines.size() > 1) {

            log.warn(
                    "Multiple baseline found. tokenId={}, method={}, targetUrl={}",
                    tokenId,
                    method,
                    targetUrl
            );

        }

        return baselines.stream()
                .findFirst()
                .orElseGet(() ->
                        analyzeRepository.findFirstData(
                                tokenId,
                                method,
                                targetUrl
                        )
                                .stream()
                                .findFirst()
                                .orElse(null)
                );


    }
}
