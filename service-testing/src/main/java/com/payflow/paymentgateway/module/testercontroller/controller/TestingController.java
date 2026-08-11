package com.payflow.paymentgateway.module.testercontroller.controller;

import com.payflow.paymentgateway.module.testercontroller.dto.RequestBodyPost;
import com.payflow.paymentgateway.module.testercontroller.dto.TestingStandardGet;
import com.payflow.paymentgateway.module.testercontroller.dto.TestingStandardPost;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/standard")
public class TestingController {


    @GetMapping("/v1/opal-value")
    ResponseEntity<TestingStandardPost> setStandardGet(){

        return ResponseEntity.ok(
                TestingStandardPost.builder()
                        .id(UUID.randomUUID())
                        .standardGet(
                                TestingStandardGet.builder()
                                        .alamat("Alamat Aja")
                                        .nama("Nama Aja")
                                        .kodePos("12345")
                                        .build()
                        )
                        .build()
        );
    }

    @PostMapping("/v1/opal-value")
    ResponseEntity<TestingStandardPost> setStandardPost(@RequestBody RequestBodyPost post){

        return ResponseEntity.ok(
                TestingStandardPost.builder()
                        .id(UUID.randomUUID())
                        .standardGet(
                                TestingStandardGet.builder()
                                        .alamat("Alamat Aja")
                                        .nama("Nama Aja")
                                        .kodePos("12345")
                                        .idPos(1000)
                                        .build()
                        )
                        .build()
        );
    }

}
