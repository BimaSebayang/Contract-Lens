package com.contractlens.common.dto;

public interface RouteProvider {

    GatewayRouteDto resolve(String token);
    String createGateway(CreateGatewayRouteRequest createGatewayRouteRequest);
    void updateGateway(CreateGatewayRouteRequest createGatewayRouteRequest);
}
