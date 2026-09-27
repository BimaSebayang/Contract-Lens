package com.contractlens.service.analyzer.module.registrationlogin.controller;

import com.contractlens.common.clawfo.request.ClawfoMappingLoginRequest;
import com.contractlens.common.clawfo.response.ClawfoLoginResponse;
import com.contractlens.common.clawfo.response.ClawfoMappingResponse;
import com.contractlens.common.dto.ClawfoJwtPayload;
import com.contractlens.common.enums.WordingClawfo;
import com.contractlens.service.analyzer.module.registrationlogin.service.LoginClawfoService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/V.1.0.0/mobile/self")
@RequiredArgsConstructor
public class SelfClawfoController {

    private final LoginClawfoService loginClawfoService;

    @GetMapping
    public ResponseEntity<ClawfoMappingResponse<ClawfoJwtPayload>> selfDetail(
            @RequestHeader("X-Device-Id") String deviceId
    ){
        ClawfoJwtPayload jwtPayload = loginClawfoService.selfDetail(deviceId);
        return ResponseEntity.ok(ClawfoMappingResponse.<ClawfoJwtPayload>of(
                WordingClawfo.USER_FOUND,
                jwtPayload
        ));
    }
}
