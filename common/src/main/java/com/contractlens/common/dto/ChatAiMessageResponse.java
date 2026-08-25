package com.contractlens.common.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.*;

import java.util.List;

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

    private List<ActionButton> actions;

    @Setter
    @Getter
    public static class ActionButton{
        @JsonProperty("ai_header")
        private String aiHeader;
        @JsonProperty("ai_detail")
        private String aiDetail;
        private String intent;
    }

}
