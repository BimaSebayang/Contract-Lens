package com.contractlens.service.analyzer.db.redis.service;

import java.util.Optional;

public interface RegistrationOtpService {
    void save(
            String ticketId,
            String otp,
            long ttlSeconds
    );

    Optional<String> findOtpByTicketId(
            String ticketId
    );

    boolean exists(
            String ticketId
    );

    void delete(
            String ticketId
    );
}
