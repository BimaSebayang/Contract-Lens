package com.contractlens.service.infrastructure;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.Arrays;

@SuppressWarnings({"unchecked", "rawtypes"})
@RestControllerAdvice
@Slf4j
public class ControllerAdvisor {


    @ExceptionHandler(Exception.class)
    public ResponseEntity<?> handleException(Exception ex) {

        log.error(ex.getMessage());
        Arrays.stream(ex.getStackTrace()).limit(10)
                .forEach(stackTraceElement -> log.error(stackTraceElement.toString()));


        return new ResponseEntity<>(ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
    }


}
