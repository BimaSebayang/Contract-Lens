package com.contractlens.common.dto;

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
}
