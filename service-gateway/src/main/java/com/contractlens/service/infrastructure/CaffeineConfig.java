package com.contractlens.service.infrastructure;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import com.github.benmanes.caffeine.cache.Cache;
import com.github.benmanes.caffeine.cache.Caffeine;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class CaffeineConfig {

    @Bean
    public Cache<String, String> routeResolveSolverCache() {
        return Caffeine.newBuilder()
                .maximumSize(10_000)
                .recordStats()
                .build();
    }
}
