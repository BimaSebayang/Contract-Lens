package com.contractlens.service;
import org.springframework.boot.*;
import org.springframework.boot.autoconfigure.*;
import org.springframework.cloud.openfeign.EnableFeignClients;

@EnableFeignClients
@SpringBootApplication
public class Gateway {
 public static void main(String[] args){
   SpringApplication.run(Gateway.class,args);
 }}