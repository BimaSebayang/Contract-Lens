package com.contractlens.service.analyzer.infrastructure;

import com.contractlens.common.enums.WordingClawfo;
import lombok.Getter;

@Getter
public class ClawfoException extends RuntimeException {
    private final WordingClawfo clawfo;
    public ClawfoException(WordingClawfo clawfo) {
        super(clawfo.getKey());
        this.clawfo= clawfo;
    }
}
