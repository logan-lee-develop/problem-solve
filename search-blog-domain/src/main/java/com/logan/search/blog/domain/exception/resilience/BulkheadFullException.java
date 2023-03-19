package com.logan.search.blog.domain.exception.resilience;

public class BulkheadFullException extends ResilienceException {
    public BulkheadFullException(String message, Throwable cause) {
        super(message, cause);
    }
}
