package com.contractlens.service.integration.feign;

import com.contractlens.common.dto.IntentAiRequest;
import com.contractlens.common.dto.IntentAiResponse;
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
    List<IntentAiResponse> detectIntent(@RequestBody IntentAiRequest request);

}
