package com.contractlens.service.infrastructure;

import com.contractlens.common.exception.FeignParseException;
import com.contractlens.common.exception.ModuleException;
import com.fasterxml.jackson.databind.ObjectMapper;
import feign.Response;
import feign.codec.ErrorDecoder;
import org.springframework.http.HttpStatus;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.LinkedHashMap;

public class CustomErrorDecoder implements ErrorDecoder{

    private final ObjectMapper objectMapper = new ObjectMapper(); // Jackson object mapper to convert JSON to objects

    @SuppressWarnings("unchecked")
    @Override
    public Exception decode(String methodKey, Response response) {
        LinkedHashMap<String,Object> responseError = getResponseBodyAsObject(response);
        return new FeignParseException(responseError, HttpStatus.valueOf(response.status()));
    }

    @SuppressWarnings("rawtypes")
    private LinkedHashMap getResponseBodyAsObject(Response response) {
        LinkedHashMap result;
        String responseBody = getResponseBody(response);
        try {
            result = objectMapper.readValue(responseBody,  LinkedHashMap.class);// Convert the response body to the specified class
        } catch (IOException e) {
            throw new ModuleException(e.getMessage(),e.getMessage(),HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return result;
    }

    private String getResponseBody(Response response) {
        StringBuilder body = new StringBuilder();

        try (BufferedReader reader = new BufferedReader(new InputStreamReader(response.body().asInputStream()))) {
            String line;
            while ((line = reader.readLine()) != null) {
                body.append(line);
            }
        } catch (IOException e) {
            // Handle the error in case the body cannot be read
            body.append("Error reading response body");
        }

        return body.toString();
    }

}
