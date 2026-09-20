package com.contractlens.common.dto;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ClawfoJwtPayload {
    private String tokenId;

    private String email;

    private String deviceId;

    private String longitude;

    private String latitude;

    private String type;

    private LocalDateTime issuedAt;

    private LocalDateTime  expiration;
}
