package com.contractlens.service.infrastructure;


import feign.RequestInterceptor;
import feign.RequestTemplate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class FeignRequestLoggerInterceptor implements RequestInterceptor {

    private static final Logger log =
            LoggerFactory.getLogger(FeignRequestLoggerInterceptor.class);

    @Override
    public void apply(RequestTemplate requestTemplate) {

        String path  = requestTemplate.feignTarget().url();
        String url = requestTemplate.url();
        log.info("Request external path,url: {}{}", path,url);

        log.info("Request external Headers: {}", requestTemplate.headers());

        String body = "";
        if (requestTemplate.body() != null) {
            body = new String(requestTemplate.body());
        }
        log.info("Request external Body: {}", body);

    }

}
