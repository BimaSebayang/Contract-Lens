package com.contractlens.service.analyzer.db.mongo.dao.component;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class ClawfoTimerComponent {
    private String createdBy;
    private LocalDateTime createdDate;
    private String updatedBy;
    private LocalDateTime updatedDate;
}
