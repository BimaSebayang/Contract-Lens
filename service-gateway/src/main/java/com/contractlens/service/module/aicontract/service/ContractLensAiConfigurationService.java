package com.contractlens.service.module.aicontract.service;

import com.contractlens.common.dto.ContractLensAiIntentResponse;
import com.contractlens.common.dto.ContractLensAiIntentUpsertRequest;
import com.contractlens.common.dto.ContractLensAiPromptResponse;
import com.contractlens.common.dto.ContractLensAiPromptUpsertRequest;
import com.contractlens.service.db.postgres.dao.ContractLensAiIntent;
import com.contractlens.service.db.postgres.dao.ContractLensAiPrompt;
import com.contractlens.service.db.postgres.service.impl.ContractLensAiTransactionService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;

@Service
@RequiredArgsConstructor
public class ContractLensAiConfigurationService {

    private final ContractLensAiTransactionService aiTransactionService;

    @Transactional
    public ContractLensAiPromptResponse upsertPrompt(
            ContractLensAiPromptUpsertRequest request,
            InputStream inputStream,
            String updatedBy
    ) throws IOException {

        String content = new String(
                inputStream.readAllBytes(),
                StandardCharsets.UTF_8
        );

        ContractLensAiPrompt prompt = new ContractLensAiPrompt();

        prompt.setPromptKey(request.promptKey());
        prompt.setPromptType(request.promptType());
        prompt.setContent(content);
        prompt.setVersion(request.version());

        ContractLensAiPrompt saved =
                aiTransactionService.upsertPrompt(
                        prompt,
                        updatedBy
                );

        return new ContractLensAiPromptResponse(
                saved.getId(),
                saved.getPromptKey(),
                saved.getPromptType(),
                saved.getContent(),
                saved.getVersion(),
                saved.getIsActive()
        );
    }

    @Transactional
    public ContractLensAiIntentResponse upsertIntent(
            ContractLensAiIntentUpsertRequest request,
            InputStream inputStream,
            String updatedBy
    ) throws IOException {

        String description = new String(
                inputStream.readAllBytes(),
                StandardCharsets.UTF_8
        );

        ContractLensAiIntent intent = new ContractLensAiIntent();

        intent.setIntentCode(request.intentCode());
        intent.setDescription(description);
        intent.setRoute(request.route());
        intent.setVersion(request.version());
        intent.setPriority(request.priority());

        ContractLensAiIntent saved =
                aiTransactionService.upsertIntent(
                        intent,
                        updatedBy
                );

        return new ContractLensAiIntentResponse(
                saved.getId(),
                saved.getIntentCode(),
                saved.getDescription(),
                saved.getRoute(),
                saved.getVersion(),
                saved.getPriority(),
                saved.getIsActive()
        );
    }
}
