package com.contractlens.common.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

@Setter
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class IntentAiResponse {

    private String content;

    private String reason;

    private String context;
}
