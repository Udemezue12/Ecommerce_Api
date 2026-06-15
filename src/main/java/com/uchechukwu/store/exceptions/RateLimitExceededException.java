package com.uchechukwu.store.exceptions;

public class RateLimitExceededException extends RuntimeException {
    public RateLimitExceededException(String message){
        super(message);
    }
    
}
