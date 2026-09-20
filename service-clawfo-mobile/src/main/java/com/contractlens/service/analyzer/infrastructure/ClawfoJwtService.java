package com.contractlens.service.analyzer.infrastructure;

import com.contractlens.common.dto.ClawfoJwtPayload;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.UUID;

@Service
public class ClawfoJwtService {

    private final SecretKey secretKey;
    private final long accessTokenExpiration;
    private final long refreshTokenExpiration;

    public ClawfoJwtService(
            @Value("${clawfo.jwt.secret}") String secret,
            @Value("${clawfo.jwt.access-token-expiration}") long accessTokenExpiration,
            @Value("${clawfo.jwt.refresh-token-expiration}") long refreshTokenExpiration
    ) {
        this.secretKey = Keys.hmacShaKeyFor(
                secret.getBytes(StandardCharsets.UTF_8)
        );

        this.accessTokenExpiration = accessTokenExpiration;
        this.refreshTokenExpiration = refreshTokenExpiration;
    }

    public String generateAccessToken(
            String email,
            String deviceId,
            String longitude,
            String latitude
    ) {
        return generateToken(
                email,
                deviceId,
                longitude,
                latitude,
                accessTokenExpiration,
                "access"
        );
    }

    public String generateRefreshToken(
            String email,
            String deviceId,
            String longitude,
            String latitude
    ) {
        return generateToken(
                email,
                deviceId,
                longitude,
                latitude,
                refreshTokenExpiration,
                "refresh"
        );
    }


    private String generateToken(
            String email,
            String deviceId,
            String longitude,
            String latitude,
            long expiration,
            String tokenType
    ) {

        Date now = new Date();

        return Jwts.builder()
                .id(UUID.randomUUID().toString())
                .subject(email)
                .claim("deviceId", deviceId)
                .claim("longitude", longitude)
                .claim("latitude", latitude)
                .claim("type", tokenType)
                .issuedAt(now)
                .expiration(
                        new Date(now.getTime() + expiration)
                )
                .signWith(secretKey)
                .compact();
    }

    public ClawfoJwtPayload getTokenPayload(String token) {

        Claims claims = Jwts.parser()
                .verifyWith(secretKey)
                .build()
                .parseSignedClaims(token)
                .getPayload();

        return ClawfoJwtPayload.builder()
                .tokenId(claims.getId())
                .email(claims.getSubject())
                .deviceId(claims.get("deviceId", String.class))
                .longitude(claims.get("longitude", String.class))
                .latitude(claims.get("latitude", String.class))
                .type(claims.get("type", String.class))
                .issuedAt(toLocalDateTime(claims.getIssuedAt()))
                .expiration(toLocalDateTime(claims.getExpiration()))
                .build();
    }

    private LocalDateTime toLocalDateTime(Date date) {
        return date.toInstant()
                .atZone(ZoneId.systemDefault())
                .toLocalDateTime();
    }
}