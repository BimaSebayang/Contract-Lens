package com.contractlens.service.analyzer;
import org.springframework.boot.*;
import org.springframework.boot.autoconfigure.*;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

@SpringBootApplication(
        exclude = {
                DataSourceAutoConfiguration.class
        }
)
public class Analyzer {
 public static void main(String[] args){
   SpringApplication.run(Analyzer.class,args);

 }}