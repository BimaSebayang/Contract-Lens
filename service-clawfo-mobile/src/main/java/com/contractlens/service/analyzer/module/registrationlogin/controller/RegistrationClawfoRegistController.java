package com.contractlens.service.analyzer.module.registrationlogin.controller;

import com.contractlens.common.clawfo.request.ClawfoMappingRegistRequest;
import com.contractlens.common.clawfo.response.ClawfoMappingResponse;
import com.contractlens.common.enums.WordingClawfo;
import com.contractlens.service.analyzer.module.registrationlogin.service.RegistrationClawfoService;
import jakarta.mail.MessagingException;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.UnsupportedEncodingException;

@RestController
@RequestMapping("/V.1.0.0/registration")
@RequiredArgsConstructor
public class RegistrationClawfoRegistController {

    private final RegistrationClawfoService registrationClawfoService;

    @PostMapping("/send-otp")
    public ResponseEntity<ClawfoMappingResponse<Void>> sendOtp(
            @RequestHeader("X-Device-Id") String deviceId,
            @RequestHeader("X-Ticket-Id") String ticketId,
            @RequestHeader("X-Email") String email
    ) throws MessagingException, UnsupportedEncodingException {

        registrationClawfoService.createOtp(
                deviceId,
                ticketId,
                email
        );

        return ResponseEntity.ok(
                ClawfoMappingResponse.of(
                        WordingClawfo.OTP_SEND_SUCCESS,
                        null
                )
        );
    }

    @PostMapping("/validate-otp")
    public ResponseEntity<ClawfoMappingResponse<Boolean>> validateOtp(
            @RequestHeader("X-Device-Id") String deviceId,
            @RequestHeader("X-Ticket-Id") String ticketId,
            @RequestHeader("X-Email-Otp") String emailOtp
    ) {

        registrationClawfoService.validateOtp(
                deviceId,
                ticketId,
                emailOtp
        );

        return ResponseEntity.ok(
                ClawfoMappingResponse.of(
                        WordingClawfo.OTP_VALIDATION_SUCCESS,
                        true
                )
        );
    }

    @PostMapping("/create-account")
    public ResponseEntity<ClawfoMappingResponse<Void>> register(
            @RequestHeader("X-Device-Id") String deviceId,
            @RequestHeader("X-Ticket-Id") String ticketId,
            @RequestHeader(value = "X-Longitude", required = false) String longitude,
            @RequestHeader(value = "X-Latitude", required = false) String latitude,
            @Valid @RequestBody ClawfoMappingRegistRequest request
    ) {

        registrationClawfoService.register(
                deviceId,
                ticketId,
                request
        );

        return ResponseEntity.ok(
                ClawfoMappingResponse.of(
                        WordingClawfo.USER_REGISTERED,
                        null
                )
        );
    }

}
