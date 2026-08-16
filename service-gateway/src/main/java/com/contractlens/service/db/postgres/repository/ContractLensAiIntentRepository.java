package com.contractlens.service.db.postgres.repository;

import com.contractlens.service.db.postgres.dao.ContractLensAiIntent;
import feign.Param;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ContractLensAiIntentRepository
        extends JpaRepository<ContractLensAiIntent, Long> {

    Optional<ContractLensAiIntent> findByIntentCodeAndVersion(
            String intentCode,
            Integer version
    );

    Optional<ContractLensAiIntent> findByIntentCodeAndIsActiveTrue(
            String intentCode
    );

    List<ContractLensAiIntent> findAllByIsActiveTrueOrderByPriorityDesc();

    @Modifying
    @Query("""
    UPDATE ContractLensAiIntent i
    SET i.isActive = false,
        i.updatedAt = CURRENT_TIMESTAMP,
        i.updatedBy = :updatedBy
    WHERE i.intentCode = :intentCode
      AND i.version <> :version
""")
    int deactivateOtherVersions(
            @Param("intentCode") String intentCode,
            @Param("version") Integer version,
            @Param("updatedBy") String updatedBy
    );

}
