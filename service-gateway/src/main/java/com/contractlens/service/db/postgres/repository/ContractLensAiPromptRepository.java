package com.contractlens.service.db.postgres.repository;

import com.contractlens.service.db.postgres.dao.ContractLensAiPrompt;
import feign.Param;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ContractLensAiPromptRepository extends
        JpaRepository<ContractLensAiPrompt, Long> {

    Optional<ContractLensAiPrompt> findByPromptKeyAndVersion(
            String promptKey,
            Integer version
    );

    Optional<ContractLensAiPrompt> findByPromptKeyAndIsActiveTrue(
            String promptKey
    );

    @Modifying
    @Query("""
    UPDATE ContractLensAiPrompt p
    SET p.isActive = false,
        p.updatedAt = CURRENT_TIMESTAMP,
        p.updatedBy = :updatedBy
    WHERE p.promptKey = :promptKey
      AND p.version <> :version
    """)
    int deactivateOtherVersions(
            @Param("promptKey") String promptKey,
            @Param("version") Integer version,
            @Param("updatedBy") String updatedBy
    );

}
