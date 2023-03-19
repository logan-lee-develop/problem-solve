package com.logan.search.blog.domain.exception.resilience;

public class ResilienceException extends RuntimeException {
    public ResilienceException(String message, Throwable cause) {
        super(message, cause);
    }
}
