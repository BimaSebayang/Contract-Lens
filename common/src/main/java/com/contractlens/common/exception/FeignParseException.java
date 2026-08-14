package com.contractlens.common.exception;


import lombok.Getter;
import org.springframework.http.HttpStatus;

import java.util.LinkedHashMap;

@Getter
public class FeignParseException extends RuntimeException{
    private final LinkedHashMap<String,Object> maps;
    private final HttpStatus httpStatus;

    public FeignParseException(LinkedHashMap<String,Object> maps, HttpStatus httpStatus) {
        super(httpStatus.name());
        this.maps = maps;
        this.httpStatus = httpStatus;
    }

}
