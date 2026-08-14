package com.contractlens.service.db.redis.service;

import com.contractlens.service.db.redis.dao.CompatibilityPlan;
import com.contractlens.service.db.redis.repository.CompatibilityRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CompatibilityPlanService {

    private final CompatibilityRepository repository;

    public CompatibilityPlan upsert(CompatibilityPlan plan) {
        return repository.save(plan);
    }

    public CompatibilityPlan read(String planId) {
        return repository.findById(planId)
                .orElse(null);
    }

    public void delete(String planId) {
        repository.deleteById(planId);
    }

}
