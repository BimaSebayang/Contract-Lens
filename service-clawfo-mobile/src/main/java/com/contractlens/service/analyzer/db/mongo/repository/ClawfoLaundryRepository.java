package com.contractlens.service.analyzer.db.mongo.repository;


import com.contractlens.service.analyzer.db.mongo.dao.ClawfoLaundryDocument;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ClawfoLaundryRepository  extends MongoRepository<ClawfoLaundryDocument, String> {
    List<ClawfoLaundryDocument> findAllByEmail(String email);

    Optional<ClawfoLaundryDocument> findByEmailAndNamaLaundry(
            String email,
            String namaLaundry
    );

    Optional<ClawfoLaundryDocument> findByLaundryCode(String laundryCode);
}
