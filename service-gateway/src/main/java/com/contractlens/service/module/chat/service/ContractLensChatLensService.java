package com.contractlens.service.module.chat.service;

import com.contractlens.common.dto.*;
import com.contractlens.common.exception.ModuleException;
import com.contractlens.service.integration.feign.service.ContractLensAiClientService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
@RequiredArgsConstructor
public class ContractLensChatLensService {

    private final ContractLensAiClientService contractLensAiClientService;
    private final ObjectMapper objectMapper;

    public IntentDetectionResponse detectIntent(IntentAiRequest request) {
        List<IntentAiResponse> responses = contractLensAiClientService.detectIntent(request);
        IntentAiResponse aiResponse = responses.get(0);
        DetectedIntent detectedIntent = parseIntent(aiResponse.getContent());

        return IntentDetectionResponse.builder()
                .intent(detectedIntent.getIntent())
                .message(request.getMessage())
                .reason(aiResponse.getReason())
                .confidence(detectedIntent.getConfidence())
                .build();
    }

    private DetectedIntent parseIntent(String content) {
        try {
            return objectMapper.readValue(
                    content,
                    DetectedIntent.class
            );
        } catch (JsonProcessingException e) {

            throw new ModuleException(
                    "Failed to parse intent detection response",
                    e.getMessage(),
                    HttpStatus.INTERNAL_SERVER_ERROR
            );

        }
    }

    public ChatStandardMessage chat(ChatStandardMessage request) {
        IntentAiRequest intentRequest = new IntentAiRequest();
        intentRequest.setMessage(request.getMessage());
        intentRequest.setConversationId(request.getConversationId());

        IntentDetectionResponse intentResponse = detectIntent(intentRequest);

        return new ChatStandardMessage(
                request.getMessage(),
                intentResponse.getReason(),
                request.getConversationId()
        );
    }
}
