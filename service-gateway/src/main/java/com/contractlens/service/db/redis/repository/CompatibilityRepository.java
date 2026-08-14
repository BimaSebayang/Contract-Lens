package com.contractlens.service.db.redis.repository;

import com.contractlens.service.db.redis.dao.CompatibilityPlan;
import org.springframework.data.repository.CrudRepository;

public interface CompatibilityRepository extends CrudRepository<CompatibilityPlan, String> {
}
