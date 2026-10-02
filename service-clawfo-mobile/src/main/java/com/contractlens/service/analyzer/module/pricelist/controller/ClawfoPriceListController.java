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
@RequestMapping("/V.1.0.0/mobile/price-list")
@RequiredArgsConstructor
public class ClawfoPriceListController {

    private final ClawfoPriceListService priceListService;

    @PostMapping("/banner")
    public ResponseEntity<ClawfoMappingResponse<Void>> postDetailPriceListBanner(
            @RequestHeader("X-Device-Id") String deviceId,
            @RequestHeader("X-Ticket-Id") String ticketId,
            @RequestHeader(value = "X-Longitude") String longitude,
            @RequestHeader(value = "X-Latitude") String latitude,
            @RequestHeader(value = "X-Location") String location,
            @RequestBody ClawfoPriceListDocument.ClawfoBannerDocument serviceBanner
    ) {

        priceListService.postDetailPriceListBanner(
                deviceId,
                ticketId,
                longitude,
                latitude,
                location,
                serviceBanner
        );

        return ResponseEntity.ok(
                ClawfoMappingResponse.of(
                        WordingClawfo.BANNER_SAVED_SUCCESS,
                        null
                )
        );
    }


    @DeleteMapping("/banner")
    public ResponseEntity<ClawfoMappingResponse<Void>> deleteBannerLaundry(
            @RequestHeader("X-Device-Id") String deviceId,
            @RequestHeader("X-Ticket-Id") String ticketId,
            @RequestHeader(value = "X-Longitude") String longitude,
            @RequestHeader(value = "X-Latitude") String latitude,
            @RequestHeader(value = "X-Location") String location,
            @RequestHeader("X-Banner-id") String bannerId
    ){
        priceListService.deleteBannerLaundry(
                deviceId,
                ticketId,
                longitude,
                latitude,
                location,
                bannerId
        );

        return ResponseEntity.ok(
                ClawfoMappingResponse.of(
                        WordingClawfo.LAPAK_LAUNDRY_DELETED,
                        null
                )
        );
    }

    @DeleteMapping("/service")
    public ResponseEntity<ClawfoMappingResponse<Void>> deleteBannerService(
            @RequestHeader("X-Device-Id") String deviceId,
            @RequestHeader("X-Ticket-Id") String ticketId,
            @RequestHeader(value = "X-Longitude") String longitude,
            @RequestHeader(value = "X-Latitude") String latitude,
            @RequestHeader(value = "X-Location") String location,
            @RequestHeader("X-Service-id") String serviceId
    ){
        priceListService.deleteServiceLaundry(
                deviceId,
                ticketId,
                longitude,
                latitude,
                location,
                serviceId
        );

        return ResponseEntity.ok(
                ClawfoMappingResponse.of(
                        WordingClawfo.LAPAK_LAUNDRY_DELETED,
                        null
                )
        );
    }

    @PostMapping("/service")
    public ResponseEntity<ClawfoMappingResponse<Void>> postDetailPriceListService(
            @RequestHeader("X-Device-Id") String deviceId,
            @RequestHeader("X-Ticket-Id") String ticketId,
            @RequestHeader(value = "X-Longitude") String longitude,
            @RequestHeader(value = "X-Latitude") String latitude,
            @RequestHeader(value = "X-Location") String location,
            @RequestBody ClawfoPriceListDocument.ClawfoServiceDocument serviceDocument
    ) {

        priceListService.postDetailPriceListService(
                deviceId,
                ticketId,
                longitude,
                latitude,
                location,
                serviceDocument
        );

        return ResponseEntity.ok(
                ClawfoMappingResponse.of(
                        WordingClawfo.SERVICE_SAVED_SUCCESS,
                        null
                )
        );
    }

    @GetMapping("/detail")
    public ResponseEntity<ClawfoMappingResponse<ClawfoPriceListDocument>> getDetailPriceList(
            @RequestHeader("X-Device-Id") String deviceId,
            @RequestHeader("X-Ticket-Id") String ticketId
    ) {

        ClawfoPriceListDocument clawfoPriceListDocument =
                priceListService.getDetailPriceList(
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