package com.contractlens.common.dto;

import lombok.*;

import java.math.BigDecimal;

@Setter
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class IntentDetectionResponse {

    private String intent;
    private String message;
    private String reason;
    private BigDecimal confidence;

}
