package com.contractlens.service.analyzer.db.mongo.service.impl;


import com.contractlens.common.enums.WordingClawfo;
import com.contractlens.service.analyzer.db.mongo.dao.ClawfoRegistrationDocument;
import com.contractlens.service.analyzer.db.mongo.repository.ClawfoRegistrationRepository;
import com.contractlens.service.analyzer.db.mongo.service.RegistrationService;
import com.contractlens.service.analyzer.infrastructure.ClawfoException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class ClawfoRegistrationServiceImpl implements RegistrationService {

    private final ClawfoRegistrationRepository repository;
    private final PasswordEncoder passwordEncoder;


    @Override
    public void upsert(
            ClawfoRegistrationDocument document
    ) {

        Optional<ClawfoRegistrationDocument> existing =
                repository.findByEmail(document.getEmail());

        if (existing.isPresent()) {
            throw new ClawfoException(
                    WordingClawfo.EMAIL_ALREADY_REGISTERED
            );
        }

        String encodedPassword = passwordEncoder.encode(
                document.getPassword()
        );

        document.setPassword(encodedPassword);
        document.setCreatedBy(document.getEmail());
        document.setCreatedDate(LocalDateTime.now());

        document.setTicketIds(document.getTicketIds());
        document.setDeviceId(document.getDeviceId());
        document.setNamaPemilik(document.getNamaPemilik());
        document.setNamaLapak(document.getNamaLapak());
        document.setTnc(document.getTnc());
        document.setLaundryCode(document.getLaundryCode());

        repository.save(document);
    }

    @Override
    public boolean existsByEmail(String email) {
        return repository.existsByEmail(email);
    }

    @Override
    public boolean existsByDeviceId(String deviceId) {
        return repository.existsByDeviceId(deviceId);
    }

    @Override
    public Optional<ClawfoRegistrationDocument> findByEmail(
            String email
    ) {
        return repository.findByEmail(email);
    }

    @Override
    public Optional<ClawfoRegistrationDocument> findByDeviceId(
            String deviceId
    ) {
        return repository.findByDeviceId(deviceId);
    }
}
