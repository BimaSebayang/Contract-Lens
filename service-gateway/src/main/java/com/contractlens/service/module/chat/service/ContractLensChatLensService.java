package com.contractlens.service.module.chat.service;

import com.contractlens.common.dto.*;
import com.contractlens.common.exception.ModuleException;
import com.contractlens.service.integration.feign.service.ContractLensAiClientService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;


@Service
@RequiredArgsConstructor
public class ContractLensChatLensService {

    private final ContractLensAiClientService contractLensAiClientService;
    private final ObjectMapper objectMapper;

    public IntentDetectionResponse detectIntent(ChatAIRequest request) {
        List<ChatAiResponse> responses = contractLensAiClientService.detectIntent(request);
        ChatAiResponse aiResponse = responses.get(0);
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
        ChatAIRequest intentRequest = new ChatAIRequest();
        intentRequest.setMessage(request.getMessage());
        intentRequest.setConversationId(request.getConversationId());

        IntentDetectionResponse intentResponse = detectIntent(intentRequest);

        return resultFromIntents(intentResponse,request);
    }

    public ChatStandardMessage resultFromIntents(IntentDetectionResponse intentResponse,ChatStandardMessage request){
        List<ChatAiResponse> chatAiResponses = List.of();
        ChatAIRequest chatAIRequest = new ChatAIRequest();
        chatAIRequest.setConversationId(request.getConversationId());
        chatAIRequest.setMessage(request.getMessage());
        switch (intentResponse.getIntent()){
            case "RECOMMEND_COMPATIBILITY":
                //chatAiResponses = contractLensAiClientService.rec(chatAIRequest);
                break;
            case "CHECK_COMPATIBILITY":
                //chatAiResponses = contractLensAiClientService.rec(chatAIRequest);
                break;
            case "EXPLAIN_IMPACT":
                //chatAiResponses = contractLensAiClientService.rec(chatAIRequest);
                break;
            case "ANALYZE_API":
                //chatAiResponses = contractLensAiClientService.rec(chatAIRequest);
                break;
            case "TEACH_HOW_TO_USE_CONTRACTLENS":
                chatAiResponses = contractLensAiClientService.howToUse(chatAIRequest);
                break;
            case "INTRODUCE_CONTRACTLENS":
                chatAiResponses = contractLensAiClientService.introduceContract(chatAIRequest);
                break;
            case "GREETING_ALREADY_KNOW_APPLICATION":
                chatAiResponses = contractLensAiClientService.greetingAlreadyKnow(chatAIRequest);
                break;
            case "GREETING_FIRST_TIMER":
                chatAiResponses = contractLensAiClientService.greetingFirstTimer(chatAIRequest);
                break;
            case "UNKNOWN":
                chatAiResponses = contractLensAiClientService.unknown(chatAIRequest);
                break;
            default:
                chatAiResponses = new ArrayList<>();
        }


        if(CollectionUtils.isEmpty(chatAiResponses)){
            return ChatStandardMessage.builder()
                    .aiResponse("Tidak Dapat Menentukan Result Karena Intent : " + intentResponse.getIntent() + " Tidak ditemukan")
                    .conversationId(request.getConversationId())
                    .message(chatAIRequest.getMessage())
                    .intent(intentResponse.getIntent())
                    .reasoning("UNFOUND")
                    .build();
        }

        DetectedMessageResult messageResult = parseMessage(chatAiResponses.get(0).getContent());

        return ChatStandardMessage.builder()
                .aiResponse(messageResult.getMessage())
                .conversationId(request.getConversationId())
                .message(chatAIRequest.getMessage())
                .reasoning(chatAiResponses.get(0).getReason())
                .nextIntent(messageResult.getNextIntent())
                .intent(intentResponse.getIntent())
                .build();

    }

    private DetectedMessageResult parseMessage(String content) {
        try {
            return objectMapper.readValue(
                    content,
                    DetectedMessageResult.class
            );
        } catch (JsonProcessingException e) {

            throw new ModuleException(
                    "Failed to parse intent detection response",
                    e.getMessage(),
                    HttpStatus.INTERNAL_SERVER_ERROR
            );

        }
    }

    public ChatAiMessageResponse chatV2(ChatAiMessageRequest request) {
        ChatAIRequest chatAIRequest = new ChatAIRequest();
        chatAIRequest.setMessage(request.getMessage());
        chatAIRequest.setConversationId(request.getConversationId());
        ChatAiResponse aiResponse = contractLensAiClientService.chatMe(chatAIRequest);

        ChatAiMessageResponse standardMessage = new ChatAiMessageResponse();
        standardMessage.setAiResponse(aiResponse.getContent());
        return standardMessage;
    }
}
