package com.contractlens.service.analyzer.infrastructure;

import com.contractlens.common.clawfo.response.ClawfoMappingResponse;
import com.contractlens.common.enums.WordingClawfo;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingRequestHeaderException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.stream.Collectors;

@ControllerAdvice
public class ClawfoGlobalExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ClawfoMappingResponse<Void>> handleValidationException(
            MethodArgumentNotValidException exception
    ) {

        String message = exception.getBindingResult()
                .getFieldErrors()
                .stream()
                .map(error -> error.getField() + ": " + error.getDefaultMessage())
                .collect(Collectors.joining(", "));

        return new ResponseEntity<>(
                ClawfoMappingResponse.of(
                        "argument.notvalid.handler",
                        message,
                        null
                ),
                HttpStatus.BAD_REQUEST
                );

    }

    @ExceptionHandler(MissingRequestHeaderException.class)
    public ResponseEntity<ClawfoMappingResponse<Void>> handleMissingRequestHeaderException(
            MissingRequestHeaderException exception
    ) {

        String message = String.format(
                "Missing required request header: %s",
                exception.getHeaderName()
        );

        return new ResponseEntity<>(
                ClawfoMappingResponse.of(
                        "argument.missing.header",
                        message,
                        null
                ),
                HttpStatus.BAD_REQUEST
        );
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<ClawfoMappingResponse<Void>> handleHttpMessageNotReadableException(
            HttpMessageNotReadableException exception
    ) {

        String message = "Request body is invalid or unreadable.";

        return new ResponseEntity<>(
                ClawfoMappingResponse.of(
                        "argument.notreadable",
                        message,
                        null
                ),
                HttpStatus.BAD_REQUEST
        );
    }

    @ExceptionHandler(ClawfoException.class)
    public ResponseEntity<ClawfoMappingResponse<Void>> handleClawfoException(
            ClawfoException exception
    ) {

        return new ResponseEntity<>(
                ClawfoMappingResponse.of(
                        exception.getClawfo(),
                        null
                ),
                HttpStatus.OK
        );
    }

}
