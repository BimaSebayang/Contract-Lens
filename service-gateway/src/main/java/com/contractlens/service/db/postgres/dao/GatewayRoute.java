package com.contractlens.service.db.postgres.dao;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.*;

import java.util.UUID;

@Entity
@Table(name = "gateway_route")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GatewayRoute extends  BaseAuditEntity{

    @Id
    private UUID id;

    @Column(nullable = false, unique = true)
    private String token;

    @Column(name = "target_url", nullable = false)
    private String targetUrl;

    @Column
    private String description;

    @Column(nullable = false)
    private Boolean active;

}
