package com.contractlens.common.clawfo.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ClawfoLoginResponse {

    private String accessToken;

    private String refreshToken;

    private Long expiresIn;
}
