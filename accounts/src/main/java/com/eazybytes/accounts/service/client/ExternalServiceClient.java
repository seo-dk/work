package com.eazybytes.accounts.service.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

@FeignClient(name = "team8-cards")
public interface ExternalServiceClient {

    @GetMapping("/api/fetch")
    String getExternalData();
}