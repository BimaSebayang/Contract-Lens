package com.contractlens.common.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.gson.annotations.SerializedName;
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
    @SerializedName("selected_intent")
    private String selectedIntent;

    @JsonProperty("message_context")
    @SerializedName("message_context")
    private String messageContext;

    @JsonProperty("intent_context")
    @SerializedName("intent_context")
    private String intentContext;
}
