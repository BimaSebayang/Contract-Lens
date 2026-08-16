package com.contractlens.common.dto;

import com.contractlens.common.enums.PromptType;

public record ContractLensAiPromptUpsertRequest(
        String promptKey,
        PromptType promptType,
        String content,
        Integer version
) {
}
