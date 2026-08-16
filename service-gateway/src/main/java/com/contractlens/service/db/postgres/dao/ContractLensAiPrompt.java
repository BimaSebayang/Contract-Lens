package com.contractlens.service.db.postgres.dao;

import com.contractlens.common.enums.PromptType;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(
        name = "contractlens_ai_prompt",
        uniqueConstraints = {
                @UniqueConstraint(
                        name = "uk_ai_prompt_key_version",
                        columnNames = {"prompt_key", "version"}
                )
        }
)
public class ContractLensAiPrompt extends  BaseAuditEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(
            name = "prompt_key",
            nullable = false,
            length = 150
    )
    private String promptKey;

    @Enumerated(EnumType.STRING)
    @Column(
            name = "prompt_type",
            nullable = false,
            length = 50
    )
    private PromptType promptType;

    @Column(
            name = "content",
            nullable = false,
            columnDefinition = "TEXT"
    )
    private String content;

    @Column(
            name = "version",
            nullable = false
    )
    private Integer version;

    @Column(
            name = "is_active",
            nullable = false
    )
    private Boolean isActive = false;

}
