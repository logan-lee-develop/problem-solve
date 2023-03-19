package com.logan.search.blog.domain.configuration.infra.resilience;


import static com.logan.search.blog.domain.configuration.infra.resilience.ResilienceConfigName.SEARCH_BLOG_DAUM_GET;
import static com.logan.search.blog.domain.configuration.infra.resilience.ResilienceConfigName.SEARCH_BLOG_NAVER_GET;

import io.github.resilience4j.core.IntervalFunction;
import java.time.Duration;
import java.util.HashMap;
import java.util.Map;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ResilienceConfigMapProvider {

    @Bean
    public ResilienceConfigMap resilienceConfigMap() {
        Map<String, ResilienceConfig> map = new HashMap<>();

        map.put(SEARCH_BLOG_DAUM_GET, ResilienceConfig.builder()
                                                      .maxConcurrentCall(20)
                                                      .timeoutDuration(Duration.ofSeconds(3))
                                                      .slowCallDuration(Duration.ofSeconds(3))
                                                      .minimumNumberOfCall(100)
                                                      .failureRate(75F)
                                                      .maxAttempts(3)
                                                      .intervalFunction(IntervalFunction.ofDefaults())
                                                      .failAfterMaxAttempts(true)
                                                      .build());

        map.put(SEARCH_BLOG_NAVER_GET, ResilienceConfig.builder()
                                                      .maxConcurrentCall(20)
                                                      .timeoutDuration(Duration.ofSeconds(3))
                                                      .slowCallDuration(Duration.ofSeconds(3))
                                                      .minimumNumberOfCall(100)
                                                      .failureRate(75F)
                                                      .maxAttempts(3)
                                                      .intervalFunction(IntervalFunction.ofDefaults())
                                                      .failAfterMaxAttempts(true)
                                                      .build());

        return ResilienceConfigMap.builder()
                                  .configs(map)
                                  .build();
    }
}
