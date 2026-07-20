package com.contractlens.service.module.testercontroller.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
@Setter
@Getter
public class TestingStandardGet {

    private String nama;
    private String alamat;
    private String kodePos;


}
