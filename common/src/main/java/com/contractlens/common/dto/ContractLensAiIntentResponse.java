package com.contractlens.common.dto;

public record ContractLensAiIntentResponse(
        Long id,
        String intentCode,
        String description,
        String route,
        Integer version,
        Integer priority,
        Boolean isActive
) {
}
