package com.logan.search.blog.domain.exception.resilience;

public class TimeoutException extends ResilienceException {
    public TimeoutException(String message, Throwable cause) {
        super(message, cause);
    }
}
