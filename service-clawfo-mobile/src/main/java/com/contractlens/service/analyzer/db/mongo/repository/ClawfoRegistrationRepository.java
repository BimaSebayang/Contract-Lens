package com.contractlens.service.analyzer.db.mongo.repository;
import com.contractlens.service.analyzer.db.mongo.dao.ClawfoRegistrationDocument;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface ClawfoRegistrationRepository
        extends MongoRepository<ClawfoRegistrationDocument, String> {

    boolean existsByEmail(String email);

    boolean existsByDeviceId(String deviceId);

    Optional<ClawfoRegistrationDocument> findByEmail(String email);

    Optional<ClawfoRegistrationDocument> findByDeviceId(String deviceId);
}
