package com.contractlens.service.infrastructure;

import feign.Client;
import feign.codec.Decoder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.cert.X509Certificate;

@SuppressWarnings({"java:S4830","java:S5527","java:S1168"})
@Configuration
public class MyFeignConfiguration {


    private static final Logger log = LoggerFactory.getLogger(MyFeignConfiguration.class);

    @Bean
    public Decoder customDecoder() {
        return new CustomSuccessDecoder();
    }

    @Bean
    public Client feignClient() throws NoSuchAlgorithmException, KeyManagementException {
        log.info("MyFeignConfiguration run feignClient");
        TrustManager[] trustAllCerts = new TrustManager[]{
                new X509TrustManager() {
                    @Override
                    public void checkClientTrusted(X509Certificate[] chain, String authType) {
                        log.info("MyFeignConfiguration run checkClientTrusted");
                    }

                    @Override
                    public void checkServerTrusted(X509Certificate[] chain, String authType) {
                        log.info("MyFeignConfiguration run checkServerTrusted");
                    }

                    @Override
                    public X509Certificate[] getAcceptedIssuers() {
                        log.info("MyFeignConfiguration run getAcceptedIssuers");
                        return null;
                    }
                }
        };

        SSLContext ssl = SSLContext.getInstance("TLS");
        ssl.init(null,trustAllCerts,new SecureRandom());

        return new Client.Default(ssl.getSocketFactory(),(h,s)->true);
    }

}