package com.contractlens.service.module.analyzer.service;

import com.contractlens.common.dto.GatewayRequest;
import com.contractlens.service.db.redis.dao.CompatibilityPlan;

import java.util.UUID;

public interface CompatibilityEngine {
    byte[] transform(CompatibilityPlan plan, byte[] responseBody);

    CompatibilityPlan callPlan(UUID tokenId, String urlPath, String method, GatewayRequest request);
}
