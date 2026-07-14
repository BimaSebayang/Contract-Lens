package com.contractlens.service.module.testercontroller.controller;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/standard")
public class StandardController {

    @GetMapping("/health")
    public ResponseEntity<?> health() {

        return ResponseEntity.ok(Map.of(
                "service", "Analyzer",
                "status", "UP",
                "timestamp", LocalDateTime.now()
        ));
    }

    @GetMapping("/contracts/{contractId}")
    public ResponseEntity<?> getContract(
            @PathVariable UUID contractId
    ) {

        return ResponseEntity.ok(Map.of(
                "contractId", contractId,
                "title", "Employment Agreement",
                "riskScore", 25,
                "status", "SUCCESS"
        ));
    }

    @PostMapping("/contracts/analyze")
    public ResponseEntity<?> analyze(
            @RequestBody Map<String, Object> request
    ) {

        return ResponseEntity.ok(Map.of(
                "status", "SUCCESS",
                "message", "Contract analysis completed.",
                "request", request
        ));
    }

    @DeleteMapping("/contracts/{contractId}")
    public ResponseEntity<?> delete(
            @PathVariable UUID contractId
    ) {

        return ResponseEntity.ok(Map.of(
                "status", "SUCCESS",
                "deletedContractId", contractId
        ));
    }

    @RequestMapping(
            value = "/oracle-check/{*path}",
            method = {
                    RequestMethod.GET,
                    RequestMethod.POST,
                    RequestMethod.PUT,
                    RequestMethod.PATCH,
                    RequestMethod.DELETE
            }
    )
    public ResponseEntity<?> echo(
            @PathVariable String path,
            @RequestHeader Map<String, String> headers,
            @RequestParam Map<String, String> params,
            @RequestBody(required = false) String body,
            HttpServletRequest request
    ) {

        Map<String, Object> response = new LinkedHashMap<>();

        response.put("service", "Analyzer");
        response.put("method", request.getMethod());
        response.put("uri", request.getRequestURI());
        response.put("path", path);
        response.put("query", params);
        response.put("headers", headers);
        response.put("body", body);

        return ResponseEntity.ok(response);
    }

    @GetMapping("/whitelist/geolocation-check")
    public ResponseEntity<?> geolocationCheck(
            @RequestParam String latitude,
            @RequestParam String longitude,
            @RequestParam(required = false) String deviceId,
            @RequestParam(required = false) String ipAddress
    ) {

        return ResponseEntity.ok(Map.of(
                "service", "Analyzer",
                "latitude", latitude,
                "longitude", longitude,
                "deviceId", deviceId,
                "ipAddress", ipAddress
        ));
    }
}
