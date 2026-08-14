package com.contractlens.service.infrastructure;


import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import feign.Response;
import feign.codec.Decoder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.util.StreamUtils;

import java.io.IOException;
import java.lang.reflect.Type;
import java.nio.charset.StandardCharsets;
import java.time.ZonedDateTime;
import java.util.Objects;

@Component
public class CustomSuccessDecoder implements Decoder {

    private static final Logger log =
            LoggerFactory.getLogger(CustomSuccessDecoder.class);

    private static final Gson gson = new GsonBuilder()
            .registerTypeAdapter(ZonedDateTime.class, new ZonedDateTimeFeignAdapter())
            .create();


    @Override
    public Object decode(Response response, Type type) throws IOException {
        String responseBody = getResponseBody(response);
        log.info("Response external Body ==> {}", responseBody);
        if(Objects.equals(type.getTypeName(),"java.lang.String")){
            return responseBody;
        }
        return gson.fromJson(responseBody,type);
    }

    private String getResponseBody(Response response) throws IOException {
        return StreamUtils.copyToString(response.body().asInputStream(), StandardCharsets.UTF_8);
    }

}