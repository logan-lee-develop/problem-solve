package com.logan.search.blog.domain.configuration.infra.resilience;

import io.github.resilience4j.core.IntervalFunction;
import java.time.Duration;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ResilienceConfig {
    // bulkhead
    private Integer maxConcurrentCall;
    // time limiter
    private Duration timeoutDuration;
    // circuit breaker
    private Duration slowCallDuration;
    private Integer minimumNumberOfCall;
    private Float failureRate;
    // retry
    private Integer maxAttempts;
    private IntervalFunction intervalFunction;
    private Boolean failAfterMaxAttempts;
}
