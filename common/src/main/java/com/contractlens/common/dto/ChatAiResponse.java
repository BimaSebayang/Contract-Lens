package com.contractlens.common.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

@Setter
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ChatAiResponse {

    private String content;

    private String reason;

    private String context;

    @JsonProperty("selected_intent")
    private String selectedIntent;

    @JsonProperty("message_context")
    private String messageContext;

    @JsonProperty("intent_context")
    private String intentContext;
}
