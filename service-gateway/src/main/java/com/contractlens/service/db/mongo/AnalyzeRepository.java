package com.contractlens.service.db.mongo;

import com.contractlens.service.db.mongo.dao.AnalyzeSpecDocument;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface AnalyzeRepository extends MongoRepository<AnalyzeSpecDocument, String> {

    @Query(
            value = """
        {
            'tokenId': ?0,
            'method': ?1,
            'targetUrl': ?2
        }
        """,
            sort = "{ 'analyzedTime' : -1 }"
    )
    List<AnalyzeSpecDocument> findLatest(
            UUID tokenId,
            String method,
            String targetUrl
    );



}
