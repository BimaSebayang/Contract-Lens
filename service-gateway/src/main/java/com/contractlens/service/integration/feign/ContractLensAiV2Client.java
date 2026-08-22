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
public interface ContractLensAiV2Client {

    @PostMapping(
            value = "/v2/chat",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    ChatAiResponse chat(@RequestBody ChatAIRequest request);

}
