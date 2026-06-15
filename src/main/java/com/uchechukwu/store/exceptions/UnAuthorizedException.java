package com.uchechukwu.store.exceptions;

public class UnAuthorizedException extends RuntimeException {
    public UnAuthorizedException(String message) {
        super(message);
    }
}
