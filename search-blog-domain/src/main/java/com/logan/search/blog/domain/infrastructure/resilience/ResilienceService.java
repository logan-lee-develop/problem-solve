package com.logan.search.blog.domain.infrastructure.resilience;

import com.logan.search.blog.domain.exception.resilience.BulkheadFullException;
import com.logan.search.blog.domain.exception.resilience.CallNotPermittedException;
import com.logan.search.blog.domain.exception.resilience.MaxRetriesExceededException;
import com.logan.search.blog.domain.exception.resilience.ResilienceException;
import com.logan.search.blog.domain.exception.resilience.TimeoutException;
import io.github.resilience4j.bulkhead.BulkheadRegistry;
import io.github.resilience4j.circuitbreaker.CircuitBreakerRegistry;
import io.github.resilience4j.retry.MaxRetriesExceeded;
import io.github.resilience4j.retry.RetryRegistry;
import io.github.resilience4j.timelimiter.TimeLimiterRegistry;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.function.Supplier;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import com.google.common.util.concurrent.ThreadFactoryBuilder;

@Slf4j
@Service
@RequiredArgsConstructor
public class ResilienceService {

    private final BulkheadRegistry bulkheadRegistry;
    private final TimeLimiterRegistry timeLimiterRegistry;
    private final CircuitBreakerRegistry circuitBreakerRegistry;
    private final RetryRegistry retryRegistry;

    private static final ExecutorService timeLimitPool = Executors.newCachedThreadPool(
      new ThreadFactoryBuilder()
        .setNameFormat("resilience-io-pool-%d")
        .setUncaughtExceptionHandler((thread, throwable) -> {
            log.error(String.format("Thread %s thrown exception %s", thread.getName(), throwable.getMessage()),
              throwable);
        })
        .build());

    public <T> Supplier<T> decorate(String name, Supplier<T> supplier) {
        return decorateRetry(name,
                  decorateCircuitBreaker(name,
                    decorateBulkHead(name,
                      decorateTimeLimiter(name, supplier))));
    }

    public <T> Supplier<T> decorateCircuitBreaker(String name, Supplier<T> supplier) {
        return () -> {
            try {
                return circuitBreakerRegistry.circuitBreaker(name)
                  .executeSupplier(supplier);
            } catch (io.github.resilience4j.circuitbreaker.CallNotPermittedException e) {
                throw new CallNotPermittedException(e.getMessage(), e);
            } catch (Exception e) {
                throw new ResilienceException(e.getMessage(), e);
            }
        };
    }

    public <T> Supplier<T> decorateTimeLimiter(String name, Supplier<T> supplier) {
        return () -> {
            try {
                return timeLimiterRegistry.timeLimiter(name)
                  .executeFutureSupplier(() -> CompletableFuture.supplyAsync(supplier, timeLimitPool));
            } catch (java.util.concurrent.TimeoutException te) {
                throw new TimeoutException(te.getMessage(), te);
            } catch (Exception e) {
                throw new ResilienceException(e.getMessage(), e);
            }
        };
    }

    public <T> Supplier<T> decorateBulkHead(String name, Supplier<T> supplier) {
        return () -> {
            try {
                return bulkheadRegistry.bulkhead(name)
                  .executeSupplier(supplier);
            } catch (io.github.resilience4j.bulkhead.BulkheadFullException e) {
                throw new BulkheadFullException(e.getMessage(), e);
            } catch (Exception e) {
                throw new ResilienceException(e.getMessage(), e);
            }
        };
    }

    public <T> Supplier<T> decorateRetry(String name, Supplier<T> supplier) {
        return () -> {
            try {
                return retryRegistry.retry(name)
                  .executeSupplier(supplier);
            } catch (MaxRetriesExceeded e) {
                throw new MaxRetriesExceededException(e.getMessage(), e);
            } catch (Exception e) {
                throw new ResilienceException(e.getMessage(), e);
            }
        };
    }
}
