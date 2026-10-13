package com.contractlens.common.enums;

public enum TimeFormatType {

    YYYYMMDDHHMMSS("yyyyMMddHHmmss");

    private final String pattern;

    TimeFormatType(String pattern) {
        this.pattern = pattern;
    }

    public String getPattern() {
        return pattern;
    }
}