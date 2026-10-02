package com.contractlens.service.analyzer.module.lapaklaundry.controller;

import com.contractlens.common.clawfo.request.ClawfoLapakLaundryRequest;
import com.contractlens.common.clawfo.request.ClawfoLapakLaundryResponse;
import com.contractlens.common.clawfo.response.ClawfoBankResponse;
import com.contractlens.common.clawfo.response.ClawfoMappingResponse;
import com.contractlens.common.enums.WordingClawfo;
import com.contractlens.service.analyzer.module.lapaklaundry.service.ClawfoLaundryService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/V.1.0.0/mobile/lapak-laundry")
@RequiredArgsConstructor
public class ClawfoLaundryController {


    private final ClawfoLaundryService clawfoLaundryService;

    @GetMapping("/banks")
    public ResponseEntity<ClawfoMappingResponse<List<ClawfoBankResponse>>> getListBank(){
        return ResponseEntity.ok(
                ClawfoMappingResponse.of(
                        WordingClawfo.BANK_FOUND,
                        clawfoLaundryService.getAllBanks()
                )
        );
    }

    @PostMapping
    public ResponseEntity<ClawfoMappingResponse<Void>> createLaundry(
            @RequestHeader("X-Device-Id") String deviceId,
            @RequestHeader("X-Ticket-Id") String ticketId,
            @RequestHeader(value = "X-Longitude") String longitude,
            @RequestHeader(value = "X-Latitude") String latitude,
            @RequestHeader(value = "X-Location") String location,
            @Valid @RequestBody ClawfoLapakLaundryRequest request
    ) {

        clawfoLaundryService.createLaundry(
                deviceId,
                ticketId,
                longitude,
                latitude,
                location,
                request
        );

        return ResponseEntity.ok(
                ClawfoMappingResponse.of(
                        WordingClawfo.LAPAK_LAUNDRY_CREATED,
                        null
                )
        );
    }



    @GetMapping("/current-laundry")
    public ResponseEntity<ClawfoMappingResponse<ClawfoLapakLaundryResponse>> getOwnLaundry(
            @RequestHeader("X-Device-Id") String deviceId,
            @RequestHeader("X-Ticket-Id") String ticketId
    ){
        ClawfoLapakLaundryResponse response =   clawfoLaundryService.getOwnLaundry(
                deviceId,
                ticketId
        );

        return ResponseEntity.ok(
                ClawfoMappingResponse.of(
                        WordingClawfo.LAPAK_LAUNDRY_FOUND,
                        response
                )
        );
    }

}
