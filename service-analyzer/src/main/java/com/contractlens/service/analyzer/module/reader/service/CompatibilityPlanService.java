package com.contractlens.service.analyzer.module.reader.service;

import com.contractlens.common.dto.CompatibilityPlan;
import com.contractlens.service.analyzer.db.mongo.dao.AnalyzeSpecDocument;

public interface CompatibilityPlanService {

    CompatibilityPlan generate(AnalyzeSpecDocument document);

}
