package com.contractlens.common.dto;

public record ContractLensAiIntentUpsertRequest(
        String intentCode,
        String description,
        String route,
        Integer version,
        Integer priority
) {
}
