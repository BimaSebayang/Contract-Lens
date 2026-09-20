package com.contractlens.service.analyzer.db.mongo.dao;

import com.contractlens.common.clawfo.request.ClawfoMappingRegistRequest;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Collections;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "clawfo_registration")
public class ClawfoRegistrationDocument {

    @Id
    private String email;

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
                .email(request.getEmail())
                .ticketIds(Collections.singletonList(ticket))
                .deviceId(deviceId)
                .namaPemilik(request.getNamaPemilik())
                .namaLapak(request.getNamaLapak())
                .password(request.getPassword())
                .tnc(request.isTnc())
                .build();
    }
}
