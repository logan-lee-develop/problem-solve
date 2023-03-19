package com.logan.search.blog.domain.exception.resilience;

public class CallNotPermittedException extends ResilienceException {
    public CallNotPermittedException(String message, Throwable cause) {
        super(message, cause);
    }
}
