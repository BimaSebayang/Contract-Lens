package com.contractlens.service.analyzer.db.redis.service;

import com.contractlens.service.analyzer.db.redis.dao.CompatibilityPlan;
import com.contractlens.service.analyzer.db.redis.repository.CompatibilityRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CompatibilityTrxService {

    private final CompatibilityRepository repository;

    public void upsert(CompatibilityPlan plan) {
        repository.save(plan);
    }

    public CompatibilityPlan read(String planId) {
        return repository.findById(planId)
                .orElse(null);
    }

    public void delete(String planId) {
        repository.deleteById(planId);
    }

}
