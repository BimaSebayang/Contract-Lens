package com.contractlens.service.db.redis.service.impl;

import com.contractlens.common.constant.ServiceConstants;
import com.contractlens.common.dto.CreateGatewayRouteRequest;
import com.contractlens.common.dto.GatewayRouteDto;
import com.contractlens.common.dto.RouteProvider;
import com.contractlens.service.db.postgres.repository.GatewayRouteRepository;
import com.contractlens.service.db.redis.dao.GatewayRoute;
import com.contractlens.service.db.redis.repository.GatewayRouteRedisRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service(ServiceConstants.GATEWAY_SERVICE_RD)
@AllArgsConstructor
public class RedisRouteProvider implements RouteProvider {

    private final GatewayRouteRedisRepository repository;

    @Override
    public GatewayRouteDto resolve(String token) {
        return repository.findById(token)
                .map(route -> {

                    if(!route.getActive()){
                        return null;
                    }

                    GatewayRouteDto dto = new GatewayRouteDto();

                    BeanUtils.copyProperties(route, dto);

                    return dto;

                }).orElse(null);
    }

    @Override
    public String createGateway(CreateGatewayRouteRequest route) {
        GatewayRoute cache = GatewayRoute.builder()
                .id(route.getUuid())
                .token(route.getToken())
                .targetUrl(route.getTargetUrl())
                .description(route.getDescription())
                .active(route.getActive())
                .ttl(-1L)
                .build();

        repository.save(cache);
        return cache.getId().toString();
    }

    @Override
    public void updateGateway(CreateGatewayRouteRequest createGatewayRouteRequest) {
        createGatewayRouteRequest.setUuid(UUID.fromString(createGatewayRouteRequest.getToken()));
        createGateway(createGatewayRouteRequest);
    }

}
