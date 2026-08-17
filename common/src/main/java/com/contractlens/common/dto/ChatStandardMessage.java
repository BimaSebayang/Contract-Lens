package com.contractlens.common.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.*;

@JsonPropertyOrder({
        "ai_response",
        "message",
        "conversation_id",
        "intent",
        "next_intent",
        "reasoning"
})
@Builder
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ChatStandardMessage {

    @JsonProperty("ai_response")
    private String aiResponse;

    private String message;

    @JsonProperty("conversation_id")
    private String conversationId;

    private String intent;

    @JsonProperty("next_intent")
    private String nextIntent;

    private String reasoning;

}
