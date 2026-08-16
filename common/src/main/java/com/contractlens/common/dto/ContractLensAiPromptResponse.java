package com.contractlens.common.dto;

import com.contractlens.common.enums.PromptType;

public record ContractLensAiPromptResponse(
        Long id,
        String promptKey,
        PromptType promptType,
        String content,
        Integer version,
        Boolean isActive
) {
}
