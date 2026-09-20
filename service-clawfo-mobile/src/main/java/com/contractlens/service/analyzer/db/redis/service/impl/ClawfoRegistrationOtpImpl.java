package com.contractlens.service.analyzer.db.redis.service.impl;

import com.contractlens.service.analyzer.db.redis.service.RegistrationOtpService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

import java.util.Optional;
import java.util.concurrent.TimeUnit;

@Component
@RequiredArgsConstructor
public class ClawfoRegistrationOtpImpl implements RegistrationOtpService {


    private static final String KEY_PREFIX =
            "clawfo:registration:otp:";

    private final StringRedisTemplate redisTemplate;

    @Override
    public void save(
            String ticketId,
            String otp,
            long ttlSeconds
    ) {
        redisTemplate.opsForValue().set(
                buildKey(ticketId),
                otp,
                ttlSeconds,
                TimeUnit.SECONDS
        );
    }

    @Override
    public Optional<String> findOtpByTicketId(
            String ticketId
    ) {
        return Optional.ofNullable(
                redisTemplate.opsForValue().get(
                        buildKey(ticketId)
                )
        );
    }

    @Override
    public boolean exists(
            String ticketId
    ) {
        return redisTemplate.hasKey(
                buildKey(ticketId)
        );
    }

    @Override
    public void delete(
            String ticketId
    ) {
        redisTemplate.delete(
                buildKey(ticketId)
        );
    }

    private String buildKey(String ticketId) {
        return KEY_PREFIX + ticketId;
    }

}
