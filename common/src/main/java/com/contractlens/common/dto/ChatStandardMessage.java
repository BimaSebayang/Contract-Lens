package com.contractlens.common.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ChatStandardMessage {

    private String message;

    private String response;

    @JsonProperty("conversation_id")
    private String conversationId;



}
