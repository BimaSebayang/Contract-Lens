package com.contractlens.service.analyzer.module.controller;

import com.contractlens.common.dto.ContractSnapshot;
import com.contractlens.service.analyzer.module.service.JsonReaderSvc;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.InputStream;

@RestController
@RequestMapping("/json-reader")
@AllArgsConstructor
public class JsonReaderController {

    private final JsonReaderSvc jsonReaderSvc;

    @PostMapping("/read-contract/string")
    ResponseEntity<ContractSnapshot>  readContracts(@RequestBody String contract){
        return ResponseEntity.ok(jsonReaderSvc.readContract(contract));
    }

    @PostMapping("/read-contract/byte")
    ResponseEntity<ContractSnapshot>  readContracts(@RequestBody byte[] contract){
        return ResponseEntity.ok(jsonReaderSvc.readContract(contract));
    }

    @PostMapping("/read-contract/inputstream")
    ResponseEntity<ContractSnapshot>  readContracts(@RequestBody InputStream contract){
        return ResponseEntity.ok(jsonReaderSvc.readContract(contract));
    }

}
