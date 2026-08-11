package com.contractlens.service.analyzer.module.reader.service.impl;

import com.contractlens.common.dto.CompatibilityPlan;
import com.contractlens.common.dto.ContractCompareResult;
import com.contractlens.common.dto.ContractDifference;
import com.contractlens.common.enums.DataType;
import com.contractlens.common.enums.DifferenceType;
import com.contractlens.common.enums.TransformationType;
import com.contractlens.service.analyzer.db.mongo.dao.AnalyzeSpecDocument;
import com.contractlens.service.analyzer.module.reader.service.CompatibilityPlanService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CompatibilityPlanServiceImpl
        implements CompatibilityPlanService {

    @Override
    public CompatibilityPlan generate(AnalyzeSpecDocument document) {

        List<CompatibilityPlan.Transformation> transformations =
                new ArrayList<>();

        addTransformations(
                document.getRequestHeaderCompareResult(),
                transformations
        );

        addTransformations(
                document.getRequestBodyCompareResult(),
                transformations
        );

        addTransformations(
                document.getResponseHeadersCompareResult(),
                transformations
        );

        addTransformations(
                document.getResponseBodyCompareResult(),
                transformations
        );

        return CompatibilityPlan.builder()
                .planId(document.getId())
                .contractId(document.getId())
                .apiPath(document.getTargetUrl())
                .method(document.getMethod())
                .transformations(transformations)
                .build();
    }

    private void addTransformations(
            ContractCompareResult compareResult,
            List<CompatibilityPlan.Transformation> transformations
    ) {
        if (compareResult == null
                || compareResult.isMatched()
                || compareResult.getDifferences() == null) {
            return;
        }

        for (ContractDifference difference : compareResult.getDifferences()) {

            CompatibilityPlan.Transformation transformation =
                    createTransformation(difference);

            if (transformation != null) {
                transformations.add(transformation);
            }
        }
    }

    private CompatibilityPlan.Transformation createTransformation(
            ContractDifference difference
    ) {

        if (!DifferenceType.TYPE_CHANGED.equals(difference.getType())) {
            return null;
        }

        DataType sourceType = difference.getActual();
        DataType targetType = difference.getExpected();

        if (sourceType == null || targetType == null) {
            return null;
        }

        return CompatibilityPlan.Transformation.builder()
                .fieldPath(difference.getPath())
                .type(TransformationType.TYPE_CONVERSION)
                .sourceType(sourceType)
                .targetType(targetType)
                .build();
    }




}