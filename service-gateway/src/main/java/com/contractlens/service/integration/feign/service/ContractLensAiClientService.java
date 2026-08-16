package com.contractlens.service.integration.feign.service;

import com.contractlens.common.dto.IntentAiRequest;
import com.contractlens.common.dto.IntentAiResponse;
import com.contractlens.common.exception.FeignParseException;
import com.contractlens.common.exception.ModuleException;
import com.contractlens.service.integration.feign.ContractLensAiClient;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ContractLensAiClientService {
    private final ContractLensAiClient contractLensAiClient;

    public List<IntentAiResponse> detectIntent(IntentAiRequest intentAiRequest){
        try {
            return contractLensAiClient.detectIntent(intentAiRequest);
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
