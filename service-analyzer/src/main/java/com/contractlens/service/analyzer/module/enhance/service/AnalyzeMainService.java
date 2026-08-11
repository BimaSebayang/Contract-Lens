package com.contractlens.service.analyzer.module.enhance.service;

import com.contractlens.common.dto.ContractCompareResult;
import com.contractlens.common.dto.ContractSnapshot;
import com.contractlens.common.dto.GatewayTransactionEvent;
import com.contractlens.service.analyzer.db.mongo.dao.AnalyzeSpecDocument;
import com.contractlens.service.analyzer.db.mongo.service.AnalyzeSpecDocumentDelegateService;
import com.contractlens.service.analyzer.db.mongo.service.AnalyzeSpecDocumentQueryService;
import com.contractlens.service.analyzer.module.reader.service.ContractComparator;
import com.contractlens.service.analyzer.module.reader.service.JsonReaderSvc;
import lombok.AllArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class AnalyzeMainService {

    private final AnalyzeSpecDocumentQueryService queryService;
    private final AnalyzeSpecDocumentDelegateService delegateService;
    private final ContractComparator contractComparator;
    private final JsonReaderSvc jsonReaderSvc;

    public void analyze(GatewayTransactionEvent event) {

        AnalyzeSpecDocument baseLine = queryService.getMainBaseLine(event);

        AnalyzeSpecDocument runtime = buildRuntimeDocument(event);

        if (baseLine == null) {
            runtime.setIsBaseline(true);
            delegateService.save(runtime);
            return;
        }

        runtime.setCompareDocId(baseLine.getId());

        compareContracts(baseLine, runtime);

        if (!hasContractChange(runtime)) {
            return;
        }

        delegateService.save(runtime);
    }

    private AnalyzeSpecDocument buildRuntimeDocument(
            GatewayTransactionEvent event
    ) {
        AnalyzeSpecDocument runtime = new AnalyzeSpecDocument();

        BeanUtils.copyProperties(event, runtime);

        ContractSnapshot requestHeaderSnapshot =
                jsonReaderSvc.readContract(event.getRequestHeaders());
        runtime.setRequestHeaderSnapshot(requestHeaderSnapshot);

        ContractSnapshot requestBodySnapshot =
                jsonReaderSvc.readContract(event.getRequestBody());
        runtime.setRequestBodySnapshot(requestBodySnapshot);

        ContractSnapshot responseHeadersSnapshot =
                jsonReaderSvc.readContract(event.getResponseHeaders());
        runtime.setResponseHeadersSnapshot(responseHeadersSnapshot);

        ContractSnapshot responseBodySnapshot =
                jsonReaderSvc.readContract(event.getResponseBody());
        runtime.setResponseBodySnapshot(responseBodySnapshot);

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


}