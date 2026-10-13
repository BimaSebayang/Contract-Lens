package com.contractlens.service.analyzer.db.mongo.repository;

import com.contractlens.service.analyzer.db.mongo.dao.ClawfoChatDocument;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface ClawfoChatRepository  extends MongoRepository<ClawfoChatDocument, String> {
}
