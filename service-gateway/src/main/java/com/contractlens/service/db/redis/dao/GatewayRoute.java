package com.contractlens.service.db.redis.dao;

import com.contractlens.service.db.postgres.dao.BaseAuditEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.*;
import org.springframework.data.redis.core.RedisHash;
import org.springframework.data.redis.core.TimeToLive;

import java.util.UUID;

@RedisHash("gateway_route")
@Builder
@Getter
public class GatewayRoute extends BaseAuditEntity {

    @Id
    private String token;

    private UUID id;

    private String targetUrl;

    private String description;

    private Boolean active;

    @TimeToLive
    private Long ttl;

}
