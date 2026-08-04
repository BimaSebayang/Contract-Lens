package com.contractlens.common.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class ModuleException extends RuntimeException {

    private final String responseCode;
    private final String responseMessage;
    private final String additionalMessage;
    private final HttpStatus status;

    public ModuleException(String responseMessage, String additionalMessage, HttpStatus status) {
        super(responseMessage);
        this.responseCode = status.value()+"";
        this.responseMessage = responseMessage;
        this.status = status;
        this.additionalMessage = additionalMessage;
    }

}
