package com.contractlens.service.analyzer.module.pesanan.controller;

import com.contractlens.common.clawfo.response.ClawfoLoginResponse;
import com.contractlens.common.clawfo.response.ClawfoMappingResponse;
import com.contractlens.common.enums.WordingClawfo;
import com.contractlens.service.analyzer.db.mongo.dao.ClawfoChatDocument;
import com.contractlens.service.analyzer.module.pesanan.service.ClawfoChatService;
import com.contractlens.service.analyzer.module.registrationlogin.service.LoginClawfoService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/V.1.0.0/mobile/pesanan-chat")
@RequiredArgsConstructor
public class ClawfoChatController {

    private final ClawfoChatService chatService;
    private final LoginClawfoService loginClawfoService;

    @GetMapping("/{chatId}")
    public ClawfoChatDocument getChat(
            @PathVariable String chatId,
            @RequestHeader("X-Device-Id") String deviceId,
            @RequestHeader("X-Ticket-Id") String ticketId
    ) {
        return chatService.getChat(
                deviceId,
                ticketId,
                chatId
        );
    }

    @PostMapping("/generate-token-chat")
    public ResponseEntity<ClawfoMappingResponse<ClawfoLoginResponse>> generateTokenChat(
            @RequestHeader("X-Device-Id") String deviceId,
            @RequestHeader(value = "X-Longitude") String longitude,
            @RequestHeader(value = "X-Latitude") String latitude,
            @RequestHeader(value = "X-Location") String location,
            @RequestHeader(value = "C-Code-Laundry") String cCodeLaundry,
            @RequestHeader(value = "C-Name-User") String cUsername,
            @RequestHeader(value = "C-Email-User", required = false) String cEmailUser,
            @RequestHeader(value = "C-Handphone-User") String cHandphoneUser
            ){

        ClawfoLoginResponse clawfoLoginResponse = loginClawfoService.generateTokenChat(
                deviceId,
                longitude,
                latitude,
                location,
                cCodeLaundry,
                cUsername,
                cEmailUser,
                cHandphoneUser
        );

        return ResponseEntity.ok(
                ClawfoMappingResponse.of(
                        WordingClawfo.LOGIN_SUCCESS,
                        clawfoLoginResponse
                )
        );
    }

}
