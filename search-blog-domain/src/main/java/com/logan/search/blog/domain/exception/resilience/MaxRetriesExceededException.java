package com.logan.search.blog.domain.exception.resilience;

public class MaxRetriesExceededException extends ResilienceException {
    public MaxRetriesExceededException(String message, Throwable cause) {
        super(message, cause);
    }
}
