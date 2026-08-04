package com.contractlens.common.dto;

import lombok.*;

import java.util.UUID;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class GatewayRouteDto {

    private UUID id;

    private String token;

    private String targetUrl;

    private String description;

    private Boolean active;

}
