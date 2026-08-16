package com.contractlens.service.module.chat.controller;

import com.contractlens.common.dto.ChatStandardMessage;
import com.contractlens.common.dto.IntentAiRequest;
import com.contractlens.common.dto.IntentDetectionResponse;
import com.contractlens.service.module.chat.service.ContractLensChatLensService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v1/chat/contract-lens")
@RequiredArgsConstructor
public class ContractLensChatController {

    private final ContractLensChatLensService contractLensChatLensService;

    @PostMapping("/intents")
    public IntentDetectionResponse detectIntent(
            @RequestBody IntentAiRequest request
    ) {
        return contractLensChatLensService.detectIntent(request);
    }


    @PostMapping
    public ChatStandardMessage chat(
            @RequestBody ChatStandardMessage request
    ) {
        return contractLensChatLensService.chat(request);
    }

}
