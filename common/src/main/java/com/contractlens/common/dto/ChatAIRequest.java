package com.contractlens.common.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

@Setter
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ChatAIRequest {

    private String message;

    @JsonProperty("conversation_id")
    private String conversationId;
}
