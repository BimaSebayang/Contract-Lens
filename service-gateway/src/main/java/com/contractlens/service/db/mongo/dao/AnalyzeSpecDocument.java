package com.contractlens.service.db.mongo.dao;

import com.contractlens.common.dto.ContractCompareResult;
import com.contractlens.common.dto.ContractSnapshot;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.CompoundIndex;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "analyze_spec_document")
@CompoundIndex(
        name = "idx_baseline_lookup_v1",
        def = "{'tokenId':1,'method':1,'targetUrl':1,'baseline':1,'analyzedTime':-1}"
)
@CompoundIndex(
        name = "idx_baseline_lookup_v2",
        def = "{'tokenId':1,'method':1,'targetUrl':1,'analyzedTime':-1}"
)
public class AnalyzeSpecDocument {

    @Id
    private String id = UUID.randomUUID()+"."+ LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMddHHmmSS"));

    private Boolean isBaseline = false;
    private String compareDocId;

    private UUID transactionId;

    /**
     * Endpoint Identifier
     */
    private UUID tokenId;

    /**
     * HTTP Information
     */
    private String method;
    private String targetUrl;
    private Integer statusCode;
    private Long duration;

    /**
     * Metadata
     */
    private Instant requestTime;


    /**
     * Snapshot
     */
    private ContractSnapshot requestHeaderSnapshot;
    private ContractSnapshot requestBodySnapshot;
    private ContractSnapshot responseHeadersSnapshot;
    private ContractSnapshot responseBodySnapshot;

    /**
     * Compare
     */
    private ContractCompareResult requestHeaderCompareResult;
    private ContractCompareResult requestBodyCompareResult;
    private ContractCompareResult responseHeadersCompareResult;
    private ContractCompareResult responseBodyCompareResult;


    private Instant analyzedTime = Instant.now();
}
