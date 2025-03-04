package com.eazybytes.accounts.exception;

public class CustomCircuitBreakerException extends RuntimeException {
    public CustomCircuitBreakerException(String message) {
        super(message);
    }

    public CustomCircuitBreakerException(String message, Throwable cause) {
        super(message, cause);
    }
}
