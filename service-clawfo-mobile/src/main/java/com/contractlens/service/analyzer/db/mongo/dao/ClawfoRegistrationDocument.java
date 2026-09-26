package com.contractlens.service.analyzer.db.mongo.dao;

import com.contractlens.common.clawfo.request.ClawfoMappingRegistRequest;
import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Collections;
import java.util.HexFormat;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "clawfo_registration")
public class ClawfoRegistrationDocument {

    @Id
    private String email;

    private String laundryCode;

    private List<String> ticketIds;

    private String deviceId;

    private String namaPemilik;

    private String namaLapak;

    private String password;

    private Boolean tnc;

    public ClawfoRegistrationDocument toDocument(
            ClawfoMappingRegistRequest request,
            String ticket,
            String deviceId
    ) {
        return ClawfoRegistrationDocument.builder()
                .laundryCode(generateHash(request.getNamaLapak(), request.getNamaPemilik()))
                .email(request.getEmail())
                .ticketIds(Collections.singletonList(ticket))
                .deviceId(deviceId)
                .namaPemilik(request.getNamaPemilik())
                .namaLapak(request.getNamaLapak())
                .password(request.getPassword())
                .tnc(request.isTnc())
                .build();
    }

    @SneakyThrows
    private static String generateHash(
            String namaLapak,
            String namaPemilik
    ) {

        String value = String.join(
                ":",
                namaLapak,
                namaPemilik,
                LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss"))
        );

        MessageDigest digest = MessageDigest.getInstance("SHA-256");

        byte[] hash = digest.digest(
                value.getBytes(StandardCharsets.UTF_8)
        );

        return HexFormat.of().formatHex(hash);
    }
}
