package com.contractlens.service.db.postgres.repository;

import com.contractlens.service.db.postgres.dao.GatewayRoute;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface GatewayRouteRepository extends JpaRepository<GatewayRoute, UUID> {

    @Query("""
            SELECT g
            FROM GatewayRoute g
            WHERE g.token = ?1
              AND g.active = true
            """)
    Optional<GatewayRoute> findByTokenWithConditionActive(String token);

}
