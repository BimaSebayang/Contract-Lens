package com.contractlens.service.infrastructure;

import com.contractlens.common.dto.ResponseError;
import com.contractlens.common.exception.ModuleException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.io.IOException;
import java.util.Arrays;

@SuppressWarnings({"unchecked", "rawtypes"})
@RestControllerAdvice
@Slf4j
public class ControllerAdvisor {



    @ExceptionHandler(Exception.class)
    public ResponseEntity<ResponseError> handleException(Exception ex) {

        log.error(ex.getMessage());
        Arrays.stream(ex.getStackTrace()).limit(10)
                .forEach(stackTraceElement -> log.error(stackTraceElement.toString()));


        return new ResponseEntity<>(
                ResponseError.builder()
                        .responseCode(HttpStatus.INTERNAL_SERVER_ERROR.value()+"")
                        .responseMessage("There is a problem on systems.")
                        .build()
                , HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(ModuleException.class)
    public ResponseEntity<ResponseError> handleException(ModuleException exception) throws IOException {
        return new ResponseEntity<>(
                ResponseError.builder()
                        .responseCode(exception.getResponseCode())
                        .responseMessage(exception.getResponseMessage())
                        .additionalMessage(exception.getAdditionalMessage())
                        .build(),
                exception.getStatus()
        );
    }


}
