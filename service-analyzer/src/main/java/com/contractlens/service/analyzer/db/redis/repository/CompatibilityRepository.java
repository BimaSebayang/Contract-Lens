package com.contractlens.service.analyzer.db.redis.repository;

import com.contractlens.service.analyzer.db.redis.dao.CompatibilityPlan;
import org.springframework.data.repository.CrudRepository;

public interface CompatibilityRepository extends CrudRepository<CompatibilityPlan, String> {
}
