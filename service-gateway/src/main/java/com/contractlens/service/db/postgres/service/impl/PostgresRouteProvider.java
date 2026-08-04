package com.contractlens.service.db.postgres.service.impl;

import com.contractlens.common.constant.ServiceConstants;
import com.contractlens.common.dto.CreateGatewayRouteRequest;
import com.contractlens.common.dto.GatewayRouteDto;
import com.contractlens.common.dto.RouteProvider;
import com.contractlens.service.db.postgres.dao.GatewayRoute;
import com.contractlens.service.db.postgres.repository.GatewayRouteRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

@Slf4j
@Service(ServiceConstants.GATEWAY_SERVICE_PS)
@AllArgsConstructor
public class PostgresRouteProvider implements RouteProvider {

    private final GatewayRouteRepository repository;

    @Override
    public GatewayRouteDto resolve(String token) {
        return repository.findByTokenWithConditionActive(token)
                .map(route -> {

                    GatewayRouteDto dto = new GatewayRouteDto();

                    BeanUtils.copyProperties(route, dto);

                    return dto;

                }).orElse(null);
    }

    @Override
    @Transactional
    public String createGateway(CreateGatewayRouteRequest request) {
        UUID uuid = UUID.randomUUID();

        LocalDateTime now = LocalDateTime.now();

        GatewayRoute route = GatewayRoute.builder()
                .id(uuid)
                .token(uuid.toString())
                .targetUrl(request.getTargetUrl())
                .description(request.getDescription())
                .active(Boolean.TRUE.equals(request.getActive()))
                .build();

        route.setCreatedBy("SYSTEM");
        route.setUpdatedBy("SYSTEM");
        route.setCreatedAt(now);
        route.setUpdatedAt(now);

        repository.save(route);

        return route.getToken();
    }

    @Override
    @Transactional
    public void updateGateway(CreateGatewayRouteRequest createGatewayRouteRequest) {
        Optional<GatewayRoute> route = repository.findByTokenWithConditionActive(createGatewayRouteRequest.getToken());
        if(route.isEmpty()){
            log.warn("Token {} Not Found.",createGatewayRouteRequest.getToken() );
        }else{
            GatewayRoute routed = route.get();
            routed.setActive(createGatewayRouteRequest.getActive());
            routed.setTargetUrl(createGatewayRouteRequest.getTargetUrl());
            routed.setDescription(createGatewayRouteRequest.getDescription());
            routed.setUpdatedBy("SYSTEM");
            routed.setUpdatedAt(LocalDateTime.now());
            repository.save(routed);
        }
    }

}
