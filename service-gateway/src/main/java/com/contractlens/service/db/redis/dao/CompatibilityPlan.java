package com.contractlens.service.db.redis.dao;

import com.contractlens.common.enums.DataType;
import com.contractlens.common.enums.TransformationType;
import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;

import java.util.List;

@Setter
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@RedisHash("compatibility_plan")
public class CompatibilityPlan {

    @Id
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
