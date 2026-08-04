package com.contractlens.service.db.redis.repository;

import com.contractlens.service.db.redis.dao.GatewayRoute;
import org.springframework.data.repository.CrudRepository;

public interface GatewayRouteRedisRepository extends CrudRepository<GatewayRoute, String> {
}
