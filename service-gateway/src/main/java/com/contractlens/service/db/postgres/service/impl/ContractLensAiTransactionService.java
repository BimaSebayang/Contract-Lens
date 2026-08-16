package com.contractlens.service.db.postgres.service.impl;

import com.contractlens.service.db.postgres.dao.ContractLensAiIntent;
import com.contractlens.service.db.postgres.dao.ContractLensAiPrompt;
import com.contractlens.service.db.postgres.repository.ContractLensAiIntentRepository;
import com.contractlens.service.db.postgres.repository.ContractLensAiPromptRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class ContractLensAiTransactionService {

    private final ContractLensAiPromptRepository promptRepository;
    private final ContractLensAiIntentRepository intentRepository;

    @Transactional
    public ContractLensAiPrompt upsertPrompt(
            ContractLensAiPrompt request,
            String updatedBy
    ) {

        ContractLensAiPrompt entity = promptRepository
                .findByPromptKeyAndVersion(
                        request.getPromptKey(),
                        request.getVersion()
                )
                .orElseGet(ContractLensAiPrompt::new);

        BeanUtils.copyProperties(
                request,
                entity,
                "id",
                "createdAt",
                "updatedAt"
        );

        if (entity.getId() == null) {
            entity.setCreatedAt(LocalDateTime.now());
        }

        entity.setUpdatedAt(LocalDateTime.now());
        entity.setUpdatedBy(updatedBy);
        entity.setIsActive(true);

        promptRepository.deactivateOtherVersions(
                request.getPromptKey(),
                request.getVersion(),
                updatedBy
        );

        return promptRepository.save(entity);
    }

    @Transactional
    public ContractLensAiIntent upsertIntent(
            ContractLensAiIntent request,
            String updatedBy
    ) {

        ContractLensAiIntent entity = intentRepository
                .findByIntentCodeAndVersion(
                        request.getIntentCode(),
                        request.getVersion()
                )
                .orElseGet(ContractLensAiIntent::new);

        BeanUtils.copyProperties(
                request,
                entity,
                "id",
                "createdAt",
                "updatedAt"
        );

        if (entity.getId() == null) {
            entity.setCreatedAt(LocalDateTime.now());
        }

        entity.setUpdatedAt(LocalDateTime.now());
        entity.setUpdatedBy(updatedBy);
        entity.setIsActive(true);

        intentRepository.deactivateOtherVersions(
                request.getIntentCode(),
                request.getVersion(),
                updatedBy
        );

        return intentRepository.save(entity);
    }

}
