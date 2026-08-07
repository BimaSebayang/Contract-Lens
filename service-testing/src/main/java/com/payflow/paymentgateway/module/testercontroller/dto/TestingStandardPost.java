package com.payflow.paymentgateway.module.testercontroller.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Builder
@Setter
@Getter
public class TestingStandardPost {

    private UUID id;
    private TestingStandardGet standardGet;

}
