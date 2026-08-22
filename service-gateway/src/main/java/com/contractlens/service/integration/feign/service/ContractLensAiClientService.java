package com.contractlens.service.integration.feign.service;

import com.contractlens.common.dto.ChatAIRequest;
import com.contractlens.common.dto.ChatAiResponse;
import com.contractlens.common.exception.FeignParseException;
import com.contractlens.common.exception.ModuleException;
import com.contractlens.service.integration.feign.ContractLensAiClient;
import com.contractlens.service.integration.feign.ContractLensAiV2Client;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ContractLensAiClientService {

    private final ContractLensAiClient contractLensAiClient;
    private final ContractLensAiV2Client contractLensAiV2Client;

    public List<ChatAiResponse> detectIntent(ChatAIRequest chatAIRequest) {
        try {
            return contractLensAiClient.detectIntent(chatAIRequest);
        } catch (FeignParseException e) {
            throw new ModuleException(
                    e.getMaps().toString(),
                    e.getMessage(),
                    e.getHttpStatus()
            );
        } catch (Exception e) {
            throw new ModuleException(
                    "Failed to call ContractLens AI service",
                    e.getMessage(),
                    HttpStatus.INTERNAL_SERVER_ERROR
            );
        }
    }

    public List<ChatAiResponse> greetingFirstTimer(ChatAIRequest chatAIRequest) {
        try {
            return contractLensAiClient.greetingFirstTimer(chatAIRequest);
        } catch (FeignParseException e) {
            throw new ModuleException(
                    e.getMaps().toString(),
                    e.getMessage(),
                    e.getHttpStatus()
            );
        } catch (Exception e) {
            throw new ModuleException(
                    "Failed to call ContractLens AI service",
                    e.getMessage(),
                    HttpStatus.INTERNAL_SERVER_ERROR
            );
        }
    }

    public List<ChatAiResponse> unknown(ChatAIRequest chatAIRequest) {
        try {
            return contractLensAiClient.unknown(chatAIRequest);
        } catch (FeignParseException e) {
            throw new ModuleException(
                    e.getMaps().toString(),
                    e.getMessage(),
                    e.getHttpStatus()
            );
        } catch (Exception e) {
            throw new ModuleException(
                    "Failed to call ContractLens AI service",
                    e.getMessage(),
                    HttpStatus.INTERNAL_SERVER_ERROR
            );
        }
    }

    public List<ChatAiResponse> greetingAlreadyKnow(ChatAIRequest chatAIRequest) {
        try {
            return contractLensAiClient.greetingAlreadyKnow(chatAIRequest);
        } catch (FeignParseException e) {
            throw new ModuleException(
                    e.getMaps().toString(),
                    e.getMessage(),
                    e.getHttpStatus()
            );
        } catch (Exception e) {
            throw new ModuleException(
                    "Failed to call ContractLens AI service",
                    e.getMessage(),
                    HttpStatus.INTERNAL_SERVER_ERROR
            );
        }
    }

    public List<ChatAiResponse> introduceContract(ChatAIRequest chatAIRequest) {
        try {
            return contractLensAiClient.introduceContract(chatAIRequest);
        } catch (FeignParseException e) {
            throw new ModuleException(
                    e.getMaps().toString(),
                    e.getMessage(),
                    e.getHttpStatus()
            );
        } catch (Exception e) {
            throw new ModuleException(
                    "Failed to call ContractLens AI service",
                    e.getMessage(),
                    HttpStatus.INTERNAL_SERVER_ERROR
            );
        }
    }

    public List<ChatAiResponse> howToUse(ChatAIRequest chatAIRequest) {
        try {
            return contractLensAiClient.howToUse(chatAIRequest);
        } catch (FeignParseException e) {
            throw new ModuleException(
                    e.getMaps().toString(),
                    e.getMessage(),
                    e.getHttpStatus()
            );
        } catch (Exception e) {
            throw new ModuleException(
                    "Failed to call ContractLens AI service",
                    e.getMessage(),
                    HttpStatus.INTERNAL_SERVER_ERROR
            );
        }
    }

    public ChatAiResponse chatMe(ChatAIRequest aiRequest){
        try {
            return contractLensAiV2Client.chat(aiRequest);
        } catch (FeignParseException e) {
            throw new ModuleException(
                    e.getMaps().toString(),
                    e.getMessage(),
                    e.getHttpStatus()
            );
        } catch (Exception e) {
            throw new ModuleException(
                    "Failed to call ContractLens AI service",
                    e.getMessage(),
                    HttpStatus.INTERNAL_SERVER_ERROR
            );
        }
    }
}