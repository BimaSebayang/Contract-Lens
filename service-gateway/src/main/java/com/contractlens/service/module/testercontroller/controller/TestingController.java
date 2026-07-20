package com.contractlens.service.module.testercontroller.controller;

import com.contractlens.service.module.testercontroller.dto.RequestBodyPost;
import com.contractlens.service.module.testercontroller.dto.TestingStandardGet;
import com.contractlens.service.module.testercontroller.dto.TestingStandardPost;
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
                                        .build()
                        )
                        .build()
        );
    }

}
