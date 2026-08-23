package com.contractlens.common.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.*;

@JsonPropertyOrder({
        "ai_response",
})
@Builder
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ChatAiMessageResponse {

    @JsonProperty("ai_response")
    private String aiResponse;

    private String intent;

}
