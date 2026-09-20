package com.contractlens.service.analyzer.db.mongo.service;

import com.contractlens.service.analyzer.db.mongo.dao.ClawfoRegistrationDocument;

import java.util.Optional;

public interface RegistrationService {

    ClawfoRegistrationDocument upsert(
            ClawfoRegistrationDocument document
    );

    boolean existsByEmail(String email);

    boolean existsByDeviceId(String deviceId);

    Optional<ClawfoRegistrationDocument> findByEmail(
            String email
    );

    Optional<ClawfoRegistrationDocument> findByDeviceId(
            String deviceId
    );
}
