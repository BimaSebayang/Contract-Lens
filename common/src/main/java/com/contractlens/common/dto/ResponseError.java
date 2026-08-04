package com.contractlens.common.dto;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class ResponseError {

    private final String responseCode;
    private final String responseMessage;
    private final String additionalMessage;

}
