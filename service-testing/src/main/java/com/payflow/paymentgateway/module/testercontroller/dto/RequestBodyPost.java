package com.payflow.paymentgateway.module.testercontroller.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
@Setter
@Getter
public class RequestBodyPost {

    private String search;
    private String index;

}
