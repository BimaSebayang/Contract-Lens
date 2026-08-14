package com.contractlens.common.util;

import org.springframework.data.redis.core.script.DigestUtils;

public class DigestUtility {

    public static String compabilityPlanId(String tokenId, String requestMethod, String url){
        return DigestUtils.sha1DigestAsHex(
                tokenId
                        + "|" + requestMethod
                        + "|" + url
        );
    }
}
