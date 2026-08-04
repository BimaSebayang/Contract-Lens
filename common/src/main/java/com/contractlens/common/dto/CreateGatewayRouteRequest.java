package com.contractlens.common.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.util.UUID;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CreateGatewayRouteRequest {
    private UUID uuid;

    private String token;

    @NotBlank
    private String targetUrl;

    private String description;

    @NotNull
    private Boolean active;


}
