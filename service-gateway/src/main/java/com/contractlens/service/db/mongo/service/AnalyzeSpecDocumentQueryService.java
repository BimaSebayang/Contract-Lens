package com.contractlens.service.db.mongo.service;

import com.contractlens.common.dto.AnalyzeSpecQuery;
import com.contractlens.common.dto.GatewayTransactionEvent;
import com.contractlens.service.db.mongo.AnalyzeRepository;
import com.contractlens.service.db.mongo.dao.AnalyzeSpecDocument;
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
                analyzeRepository.findLatest(
                        tokenId,
                        method,
                        targetUrl
                );



        return baselines.stream()
                .findFirst()
                .orElseGet(() ->
                        analyzeRepository.findLatest(
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
