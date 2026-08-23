package com.contractlens.service.module.chat.controller;

import com.contractlens.common.dto.ChatAiMessageRequest;
import com.contractlens.common.dto.ChatAiMessageResponse;
import com.contractlens.common.dto.ChatStandardMessage;
import com.contractlens.service.module.chat.service.ContractLensChatLensService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v2/chat/contract-lens")
@RequiredArgsConstructor
public class ContractLensChatV2Controller {

    private final ContractLensChatLensService contractLensChatLensService;


    @PostMapping
    public ChatAiMessageResponse chat(
            @RequestBody ChatAiMessageRequest request
    ) {
        return contractLensChatLensService.chatV2(request);
    }

}
