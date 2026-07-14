package com.contractlens.common.dto;

import java.util.UUID;

public record GatewayRequest(
                             UUID tokenId,

                             String method,

                             String path,

                             String query,

                             byte[] body) {
}
