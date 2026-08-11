package com.contractlens.common.dto;

import com.contractlens.common.enums.DataType;
import com.contractlens.common.enums.TransformationType;
import lombok.*;

import java.util.List;

@Setter
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CompatibilityPlan {

    private String planId;

    private String contractId;

    private String apiPath;

    private String method;

    private List<Transformation> transformations;

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Transformation {

        private String fieldPath;

        private TransformationType type;

        private DataType sourceType;

        private DataType targetType;
    }

}
