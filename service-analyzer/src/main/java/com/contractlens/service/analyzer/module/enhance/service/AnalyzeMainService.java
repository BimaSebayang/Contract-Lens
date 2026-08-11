package com.contractlens.service.analyzer.module.enhance.service;

import com.contractlens.common.dto.*;
import com.contractlens.service.analyzer.db.mongo.dao.AnalyzeSpecDocument;
import com.contractlens.service.analyzer.db.mongo.service.AnalyzeSpecDocumentDelegateService;
import com.contractlens.service.analyzer.db.mongo.service.AnalyzeSpecDocumentQueryService;
import com.contractlens.service.analyzer.module.reader.service.ContractComparator;
import com.contractlens.service.analyzer.module.reader.service.JsonReaderSvc;
import lombok.AllArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Objects;

@Service
@AllArgsConstructor
public class AnalyzeMainService {

    private final AnalyzeSpecDocumentQueryService queryService;
    private final AnalyzeSpecDocumentDelegateService delegateService;
    private final ContractComparator contractComparator;
    private final JsonReaderSvc jsonReaderSvc;

    public void analyze(GatewayTransactionEvent event) {

        AnalyzeSpecDocument baseLine =
                queryService.getMainBaseLine(event);

        AnalyzeSpecDocument runtime =
                buildRuntimeDocument(event);

        if (baseLine == null) {
            runtime.setIsBaseline(true);
            delegateService.save(runtime);
            return;
        }

        runtime.setCompareDocId(baseLine.getId());

        compareContracts(baseLine, runtime);



        AnalyzeSpecDocument latestChange =
                queryService.getLatestData(new AnalyzeSpecQuery(
                        event.getTargetUrl(),
                        event.getMethod(),
                        event.getTokenId()
                ));

        boolean hasSameContract = hasSameContractChange(latestChange, runtime);
        if (hasSameContract) {
            return;
        }

        delegateService.save(runtime);
    }

    private AnalyzeSpecDocument buildRuntimeDocument(
            GatewayTransactionEvent event
    ) {
        AnalyzeSpecDocument runtime = new AnalyzeSpecDocument();

        BeanUtils.copyProperties(event, runtime);

        runtime.setRequestHeaderSnapshot(
                jsonReaderSvc.readContract(event.getRequestHeaders())
        );

        runtime.setRequestBodySnapshot(
                jsonReaderSvc.readContract(event.getRequestBody())
        );

        runtime.setResponseHeadersSnapshot(
                jsonReaderSvc.readContract(event.getResponseHeaders())
        );

        runtime.setResponseBodySnapshot(
                jsonReaderSvc.readContract(event.getResponseBody())
        );

        return runtime;
    }

    private void compareContracts(
            AnalyzeSpecDocument baseLine,
            AnalyzeSpecDocument runtime
    ) {
        runtime.setRequestHeaderCompareResult(
                contractComparator.compare(
                        baseLine.getRequestHeaderSnapshot(),
                        runtime.getRequestHeaderSnapshot()
                )
        );

        runtime.setRequestBodyCompareResult(
                contractComparator.compare(
                        baseLine.getRequestBodySnapshot(),
                        runtime.getRequestBodySnapshot()
                )
        );

        runtime.setResponseHeadersCompareResult(
                contractComparator.compare(
                        baseLine.getResponseHeadersSnapshot(),
                        runtime.getResponseHeadersSnapshot()
                )
        );

        runtime.setResponseBodyCompareResult(
                contractComparator.compare(
                        baseLine.getResponseBodySnapshot(),
                        runtime.getResponseBodySnapshot()
                )
        );
    }

    private boolean hasContractChange(
            AnalyzeSpecDocument runtime
    ) {
        return !runtime.getRequestHeaderCompareResult().isMatched()
                || !runtime.getRequestBodyCompareResult().isMatched()
                || !runtime.getResponseHeadersCompareResult().isMatched()
                || !runtime.getResponseBodyCompareResult().isMatched();
    }

    private boolean hasSameContractChange(
            AnalyzeSpecDocument previous,
            AnalyzeSpecDocument current
    ) {
        if (previous == null) {
            return false;
        }

        boolean sameRequestHeader = sameCompareResult(
                previous.getRequestHeaderCompareResult(),
                current.getRequestHeaderCompareResult()
        );

        boolean sameRequestBody = sameCompareResult(
                previous.getRequestBodyCompareResult(),
                current.getRequestBodyCompareResult()
        );

        boolean sameResponseHeader =sameCompareResult(
                previous.getResponseHeadersCompareResult(),
                current.getResponseHeadersCompareResult()
        );

        boolean sameResponseBody = sameCompareResult(
                previous.getResponseBodyCompareResult(),
                current.getResponseBodyCompareResult());

        return sameRequestBody && sameRequestHeader && sameResponseHeader && sameResponseBody;
    }

    private boolean sameCompareResult(
            ContractCompareResult previous,
            ContractCompareResult current
    ) {
        if (previous == null || current == null) {
            return previous == current;
        }

        if (previous.isMatched() != current.isMatched()) {
            return false;
        }

        return sameDifferences(
                previous.getDifferences(),
                current.getDifferences()
        );
    }

    private boolean sameDifferences(
            List<ContractDifference> previous,
            List<ContractDifference> current
    ) {
        if (previous == null || current == null) {
            return previous == current;
        }

        if (previous.size() != current.size()) {
            return false;
        }

        return previous.stream()
                .allMatch(previousDifference ->
                        current.stream()
                                .anyMatch(currentDifference ->
                                        sameDifference(
                                                previousDifference,
                                                currentDifference
                                        )
                                )
                );
    }

    private boolean sameDifference(
            ContractDifference previous,
            ContractDifference current
    ) {
        return Objects.equals(
                previous.getType(),
                current.getType()
        )
                && Objects.equals(
                previous.getPath(),
                current.getPath()
        )
                && Objects.equals(
                previous.getExpected(),
                current.getExpected()
        )
                && Objects.equals(
                previous.getActual(),
                current.getActual()
        );
    }
}