package com.contractlens.service.analyzer.db.mongo.dao;

import com.contractlens.common.clawfo.request.ClawfoMappingRegistRequest;
import com.contractlens.service.analyzer.db.mongo.dao.component.ClawfoTimerComponent;
import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Collections;
import java.util.HexFormat;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "clawfo_registration")
public class ClawfoRegistrationDocument  {

    @Id
    private String email;

    private String laundryCode;

    private List<String> ticketIds;

    private String deviceId;

    private String namaPemilik;

    private String namaLapak;

    private String password;

    private Boolean tnc;

    private String createdBy;
    private LocalDateTime createdDate;

    public ClawfoRegistrationDocument toDocument(
            ClawfoMappingRegistRequest request,
            String ticket,
            String deviceId
    ) {

        ClawfoRegistrationDocument document =
                new ClawfoRegistrationDocument();

        document.setLaundryCode(
                generateHash(
                        request.getNamaLapak(),
                        request.getNamaPemilik()
                )
        );

        document.setEmail(request.getEmail());
        document.setTicketIds(Collections.singletonList(ticket));
        document.setDeviceId(deviceId);
        document.setNamaPemilik(request.getNamaPemilik());
        document.setNamaLapak(request.getNamaLapak());
        document.setPassword(request.getPassword());
        document.setTnc(request.isTnc());


        return document;
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
                LocalDateTime.now()
                        .format(DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss"))
        );

        MessageDigest digest = MessageDigest.getInstance("SHA-256");

        byte[] hash = digest.digest(
                value.getBytes(StandardCharsets.UTF_8)
        );

        return HexFormat.of().formatHex(hash);
    }
}