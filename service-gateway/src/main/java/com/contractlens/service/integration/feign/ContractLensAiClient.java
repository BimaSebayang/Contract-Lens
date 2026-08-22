package com.contractlens.service.integration.feign;

import com.contractlens.common.dto.ChatAIRequest;
import com.contractlens.common.dto.ChatAiResponse;
import com.contractlens.service.infrastructure.MyFeignConfiguration;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

@FeignClient(
        name = "${external.contract-lens-api.name}",
        url = "${external.contract-lens-api.url}",
        configuration = MyFeignConfiguration.class
)
public interface ContractLensAiClient {

    @PostMapping(
            value = "/v1/chat/contract-lens/intents",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    List<ChatAiResponse> detectIntent(@RequestBody ChatAIRequest request);



    @PostMapping("/v1/chat/contract-lens/greeting-first-timer")
    List<ChatAiResponse> greetingFirstTimer(
            @RequestBody ChatAIRequest request
    );

    @PostMapping("/v1/chat/contract-lens/unknown")
    List<ChatAiResponse> unknown(
            @RequestBody ChatAIRequest request
    );

    @PostMapping("/v1/chat/contract-lens/greeting-already-know")
    List<ChatAiResponse> greetingAlreadyKnow(
            @RequestBody ChatAIRequest request
    );

    @PostMapping("/v1/chat/contract-lens/introduce-contract")
    List<ChatAiResponse> introduceContract(
            @RequestBody ChatAIRequest request
    );

    @PostMapping("/v1/chat/contract-lens/how-to-use")
    List<ChatAiResponse> howToUse(
            @RequestBody ChatAIRequest request
    );


}
