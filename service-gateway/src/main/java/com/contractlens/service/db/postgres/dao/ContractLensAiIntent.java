package com.contractlens.service.db.postgres.dao;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
@Entity
@Table(
        name = "contractlens_ai_intent",
        uniqueConstraints = {
                @UniqueConstraint(
                        name = "uk_ai_intent_code",
                        columnNames = "intent_code"
                )
        }
)
public class ContractLensAiIntent extends  BaseAuditEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(
            name = "intent_code",
            nullable = false,
            length = 100
    )
    private String intentCode;

    @Column(
            name = "description",
            columnDefinition = "TEXT"
    )
    private String description;

    @Column(
            name = "route",
            length = 255
    )
    private String route;

    @Column(
            name = "priority",
            nullable = false
    )
    private Integer priority = 0;

    @Column(
            name = "is_active",
            nullable = false
    )
    private Boolean isActive = true;

    @Column(
            name = "version",
            nullable = false
    )
    private Integer version;

}
