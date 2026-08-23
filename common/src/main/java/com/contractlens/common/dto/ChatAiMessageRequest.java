package com.contractlens.common.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.*;

@JsonPropertyOrder({
        "message",
        "conversation_id"
})
@Builder
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ChatAiMessageRequest {

    private String message;

    @JsonProperty("conversation_id")
    private String conversationId;

}
