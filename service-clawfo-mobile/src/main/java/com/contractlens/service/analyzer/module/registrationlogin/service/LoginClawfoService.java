package com.contractlens.service.analyzer.module.registrationlogin.service;

import com.contractlens.common.clawfo.request.ClawfoMappingLoginRequest;
import com.contractlens.common.clawfo.response.ClawfoLoginResponse;
import com.contractlens.common.dto.ClawfoJwtPayload;
import com.contractlens.common.enums.WordingClawfo;
import com.contractlens.common.util.TimestampUtils;
import com.contractlens.service.analyzer.db.mongo.dao.ClawfoRegistrationDocument;
import com.contractlens.service.analyzer.db.mongo.service.RegistrationService;
import com.contractlens.service.analyzer.infrastructure.ClawfoEmailService;
import com.contractlens.service.analyzer.infrastructure.ClawfoException;
import com.contractlens.service.analyzer.infrastructure.ClawfoJwtService;
import io.jsonwebtoken.JwtException;
import jakarta.mail.MessagingException;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.io.UnsupportedEncodingException;

@Slf4j
@Service
@RequiredArgsConstructor
public class LoginClawfoService {

    private final RegistrationService registrationService;
    private final PasswordEncoder passwordEncoder;
    private final ClawfoJwtService jwtService;
    private final ClawfoEmailService clawfoEmailService;

    public ClawfoLoginResponse login(
            String deviceId,
            String longitude,
            String latitude,
            String location,
            @Valid ClawfoMappingLoginRequest request
    ) {

        ClawfoRegistrationDocument user = registrationService
                .findByEmail(request.getEmail())
                .orElseThrow(() -> new ClawfoException(
                        WordingClawfo.LOGIN_FAILED
                ));

        if (!passwordEncoder.matches(
                request.getPassword(),
                user.getPassword()
        )) {
            throw new ClawfoException(
                    WordingClawfo.LOGIN_FAILED
            );
        }

        if (!deviceId.equals(user.getDeviceId())) {
            try {
                clawfoEmailService.sendUnknownDeviceLogin(
                    request.getEmail(),
                    deviceId,
                    location,
                    TimestampUtils.getCurrentTimeStamp()
                );
            } catch (MessagingException | UnsupportedEncodingException e) {
                throw new RuntimeException(e);
            }
        }

        String accessToken = jwtService.generateAccessToken(
                user.getEmail(),
                deviceId,
                longitude,
                latitude,
                user.getNamaPemilik(),
                user.getNamaLapak()
        );

        String refreshToken = jwtService.generateRefreshToken(
                user.getEmail(),
                deviceId,
                longitude,
                latitude,
                user.getNamaPemilik(),
                user.getNamaLapak()
        );

        return ClawfoLoginResponse.builder()
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .expiresIn(3600L)
                .build();
    }

    public ClawfoLoginResponse refreshToken(
            String deviceId,
            String longitude,
            String latitude,
            String location,
            @NotBlank String refreshToken
    ) {

        final ClawfoJwtPayload payload;

        try {
            payload = jwtService.getTokenPayload(refreshToken);
        } catch (JwtException | IllegalArgumentException exception) {
            throw new ClawfoException(
                    WordingClawfo.LOGIN_FAILED
            );
        }

        // Must be a refresh token
        if (!"refresh".equals(payload.getType())) {
            throw new ClawfoException(
                    WordingClawfo.LOGIN_FAILED
            );
        }

        // Refresh token must belong to the current device
        if (!deviceId.equals(payload.getDeviceId())) {
            try {
                clawfoEmailService.sendUnknownDeviceLogin(
                        payload.getEmail(),
                        deviceId,
                        location,
                        TimestampUtils.getCurrentTimeStamp()
                );
            } catch (MessagingException | UnsupportedEncodingException e) {
                throw new RuntimeException(e);
            }

        }

        // Account must still exist
        ClawfoRegistrationDocument user =
                registrationService
                        .findByEmail(payload.getEmail())
                        .orElseThrow(() ->
                                new ClawfoException(
                                        WordingClawfo.LOGIN_FAILED
                                )
                        );

        // Device registered to the account must still match
        if (!deviceId.equals(user.getDeviceId())) {
            try {
                clawfoEmailService.sendUnknownDeviceLogin(
                        user.getEmail(),
                        deviceId,
                        location,
                        TimestampUtils.getCurrentTimeStamp()
                );
            } catch (MessagingException | UnsupportedEncodingException e) {
                throw new RuntimeException(e);
            }

        }

        String newAccessToken =
                jwtService.generateAccessToken(
                        user.getEmail(),
                        deviceId,
                        longitude,
                        latitude,
                        user.getNamaPemilik(),
                        user.getNamaLapak()
                );

        String newRefreshToken =
                jwtService.generateRefreshToken(
                        user.getEmail(),
                        deviceId,
                        longitude,
                        latitude,
                        user.getNamaPemilik(),
                        user.getNamaLapak()
                );

        return ClawfoLoginResponse.builder()
                .accessToken(newAccessToken)
                .refreshToken(newRefreshToken)
                .expiresIn(3600L)
                .build();
    }
}
