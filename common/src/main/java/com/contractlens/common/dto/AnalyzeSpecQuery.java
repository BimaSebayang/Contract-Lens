package com.contractlens.common.dto;

import java.util.UUID;

public record AnalyzeSpecQuery(
        String targetUrl,
        String method,
        UUID tokenId
) {
}
