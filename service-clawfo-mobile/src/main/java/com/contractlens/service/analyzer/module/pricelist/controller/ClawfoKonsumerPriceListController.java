package com.contractlens.service.analyzer.module.pricelist.controller;

import com.contractlens.common.clawfo.response.ClawfoMappingResponse;
import com.contractlens.common.enums.WordingClawfo;
import com.contractlens.service.analyzer.db.mongo.dao.ClawfoPriceListDocument;
import com.contractlens.service.analyzer.module.pricelist.service.ClawfoPriceListService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/V.1.0.0/web/price-list")
@RequiredArgsConstructor
public class ClawfoKonsumerPriceListController {

    private final ClawfoPriceListService priceListService;

    @GetMapping("/detail")
    public ResponseEntity<ClawfoMappingResponse<ClawfoPriceListDocument>> getDetailPriceList(
            @RequestHeader("X-Device-Id") String deviceId,
            @RequestHeader("X-Ticket-Id") String ticketId
    ) {
        ClawfoPriceListDocument clawfoPriceListDocument =
                priceListService.getDetailPriceListWeb(
                        deviceId,
                        ticketId
                );

        return ResponseEntity.ok(
                ClawfoMappingResponse.of(
                        WordingClawfo.PRICE_LIST_FOUND,
                        clawfoPriceListDocument
                )
        );
    }

}
