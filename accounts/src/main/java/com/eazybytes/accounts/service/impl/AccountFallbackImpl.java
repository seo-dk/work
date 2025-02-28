package com.eazybytes.accounts.service.impl;

import org.springframework.stereotype.Service;
import com.eazybytes.accounts.service.IAccountFallback;
import com.eazybytes.accounts.service.client.ExternalServiceClient;

import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;

@Service
public class AccountFallbackImpl implements IAccountFallback {

    private final ExternalServiceClient externalServiceClient;

    public AccountFallbackImpl(ExternalServiceClient externalServiceClient) {
        this.externalServiceClient = externalServiceClient;
    }

    @Override
    @CircuitBreaker(name = "accountServiceFail", fallbackMethod = "fallback")
    public String getAccountData() {
        // 일부러 50% 확률로 실패하게 만들기 (테스트용)
        if (Math.random() > 0.5) {
            throw new RuntimeException("Account service error");
        }

        // 🔹 RestTemplate 대신 Feign Client 사용
        return externalServiceClient.getExternalData();
    }

    public String fallback(Throwable t) {
        return "Fallback response: Account service is currently unavailable";
    }

}
