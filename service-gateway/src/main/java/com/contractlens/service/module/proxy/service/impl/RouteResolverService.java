package com.contractlens.service.module.proxy.service.impl;

import com.contractlens.common.constant.ServiceConstants;
import com.contractlens.common.dto.CreateGatewayRouteRequest;
import com.contractlens.common.dto.GatewayRouteDto;
import com.contractlens.common.dto.RouteProvider;
import com.contractlens.common.exception.ModuleException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.Objects;
import java.util.UUID;

@Service
public class RouteResolverService {

    @Qualifier(ServiceConstants.GATEWAY_SERVICE_RD)
    @Autowired
    private RouteProvider routeRedis;

    @Qualifier(ServiceConstants.GATEWAY_SERVICE_PS)
    @Autowired
    private RouteProvider routePostgres;

    public GatewayRouteDto resolve(String token){
        GatewayRouteDto resolveRedis = routeRedis.resolve(token);
        if(!Objects.isNull(resolveRedis)){
            return resolveRedis;
        }else{
            GatewayRouteDto resolvePostgres = routePostgres.resolve(token);
            if(!Objects.isNull(resolvePostgres)){
                routeRedis.createGateway(CreateGatewayRouteRequest.builder()
                                .token(resolvePostgres.getToken())
                                .targetUrl(resolvePostgres.getTargetUrl())
                                .description(resolvePostgres.getDescription())
                                .active(resolvePostgres.getActive())
                                .uuid(resolvePostgres.getId())
                                .build());
                return resolvePostgres;
            }else{
                throw new ModuleException("Token "+token + " is not found",null, HttpStatus.NOT_FOUND);
            }
        }
    }

    public String createToken(CreateGatewayRouteRequest createGatewayRouteRequest){
        String token = routePostgres.createGateway(createGatewayRouteRequest);
        createGatewayRouteRequest.setToken(token);
        createGatewayRouteRequest.setUuid(UUID.fromString(token));
        routeRedis.createGateway(createGatewayRouteRequest);
        return token;
    }

    public void updateToken(CreateGatewayRouteRequest createGatewayRouteRequest){
        routePostgres.updateGateway(createGatewayRouteRequest);
        routeRedis.updateGateway(createGatewayRouteRequest);
    }



}
