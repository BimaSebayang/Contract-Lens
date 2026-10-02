package com.contractlens.service.analyzer.db.mongo.dao.component;

import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.data.mongodb.core.index.Indexed;


@EqualsAndHashCode(callSuper = true)
@Data
public class ClawfoComponent extends ClawfoTimerComponent{
    @Indexed
    private String email;
}
