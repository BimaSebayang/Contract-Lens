package com.contractlens.service.analyzer.db.mongo.service.impl;


import com.contractlens.service.analyzer.db.mongo.dao.ClawfoRegistrationDocument;
import com.contractlens.service.analyzer.db.mongo.repository.ClawfoRegistrationRepository;
import com.contractlens.service.analyzer.db.mongo.service.RegistrationService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.mongodb.core.FindAndModifyOptions;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class ClawfoRegistrationService  implements RegistrationService {

    private final ClawfoRegistrationRepository repository;
    private final MongoTemplate mongoTemplate;
    private final PasswordEncoder passwordEncoder;


    @Override
    public ClawfoRegistrationDocument upsert(
            ClawfoRegistrationDocument document
    ) {


        String encodedPassword = passwordEncoder.encode(
                document.getPassword()
        );

        document.setPassword(encodedPassword);

        Query query = new Query(
                Criteria.where("_id").is(document.getEmail())
        );

        Update update = new Update()
                .set("ticketIds", document.getTicketIds())
                .set("deviceId", document.getDeviceId())
                .set("namaPemilik", document.getNamaPemilik())
                .set("namaLapak", document.getNamaLapak())
                .set("password", document.getPassword())
                .set("tnc", document.getTnc())
                .set("laundryCode",document.getLaundryCode());



        mongoTemplate.upsert(
                query,
                update,
                ClawfoRegistrationDocument.class
        );

        return document;
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
