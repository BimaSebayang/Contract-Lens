package com.contractlens.service.analyzer.module.registrationlogin.controller;

import com.contractlens.common.clawfo.request.ClawfoMappingLoginRequest;
import com.contractlens.common.clawfo.request.ClawfoRefreshTokenRequest;
import com.contractlens.common.clawfo.response.ClawfoLoginResponse;
import com.contractlens.common.clawfo.response.ClawfoMappingResponse;
import com.contractlens.common.enums.WordingClawfo;
import com.contractlens.service.analyzer.module.registrationlogin.service.LoginClawfoService;
import jakarta.mail.MessagingException;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.UnsupportedEncodingException;

@RestController
@RequestMapping("/V.1.0.0/mobile/login")
@RequiredArgsConstructor
public class LoginClawfoController {

    private final LoginClawfoService loginClawfoService;


    @PostMapping
    public ResponseEntity<ClawfoMappingResponse<ClawfoLoginResponse>> login(
            @RequestHeader("X-Device-Id") String deviceId,
            @RequestHeader(value = "X-Longitude") String longitude,
            @RequestHeader(value = "X-Latitude") String latitude,
            @RequestHeader(value = "X-Location") String location,
            @Valid @RequestBody ClawfoMappingLoginRequest request
    ) throws MessagingException, UnsupportedEncodingException {

        ClawfoLoginResponse clawfoLoginResponse = loginClawfoService.login(
                deviceId,
                longitude,
                latitude,
                location,
                request
        );

        return ResponseEntity.ok(
                ClawfoMappingResponse.of(
                        WordingClawfo.LOGIN_SUCCESS,
                        clawfoLoginResponse
                )
        );
    }

    @PostMapping("/refresh")
    public ResponseEntity<ClawfoMappingResponse<ClawfoLoginResponse>> refresh(
            @RequestHeader("X-Device-Id") String deviceId,
            @RequestHeader(value = "X-Longitude") String longitude,
            @RequestHeader(value = "X-Latitude") String latitude,
            @RequestHeader(value = "X-Location") String location,
            @Valid @RequestBody ClawfoRefreshTokenRequest request
    ) {

        ClawfoLoginResponse clawfoLoginResponse =
                loginClawfoService.refreshToken(
                        deviceId,
                        longitude,
                        latitude,
                        location,
                        request.getRefreshToken()
                );

        return ResponseEntity.ok(
                ClawfoMappingResponse.of(
                        WordingClawfo.LOGIN_SUCCESS,
                        clawfoLoginResponse
                )
        );
    }

}
