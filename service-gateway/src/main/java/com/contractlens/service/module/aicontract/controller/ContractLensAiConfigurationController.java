package com.contractlens.service.module.aicontract.controller;

import com.contractlens.common.dto.ContractLensAiIntentResponse;
import com.contractlens.common.dto.ContractLensAiIntentUpsertRequest;
import com.contractlens.common.dto.ContractLensAiPromptResponse;
import com.contractlens.common.dto.ContractLensAiPromptUpsertRequest;
import com.contractlens.service.module.aicontract.service.ContractLensAiConfigurationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequestMapping("/contractlens/ai")
@RequiredArgsConstructor
public class ContractLensAiConfigurationController {

    private final ContractLensAiConfigurationService aiConfigurationService;

    @PostMapping(
            value = "/prompts",
            consumes = MediaType.MULTIPART_FORM_DATA_VALUE
    )
    public ContractLensAiPromptResponse upsertPrompt(
            @ModelAttribute ContractLensAiPromptUpsertRequest request,
            @RequestPart("file") MultipartFile file,
            @RequestHeader("X-User-Id") String updatedBy
    ) throws IOException {

        return aiConfigurationService.upsertPrompt(
                request,
                file.getInputStream(),
                updatedBy
        );
    }

    @PostMapping(
            value = "/intents",
            consumes = MediaType.MULTIPART_FORM_DATA_VALUE
    )
    public ContractLensAiIntentResponse upsertIntent(
            @ModelAttribute ContractLensAiIntentUpsertRequest request,
            @RequestPart("file") MultipartFile file,
            @RequestHeader("X-User-Id") String updatedBy
    ) throws IOException {

        return aiConfigurationService.upsertIntent(
                request,
                file.getInputStream(),
                updatedBy
        );
    }

}
