package com.logan.search.blog.domain.configuration.infra.cache;

import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

@Slf4j
@EnableCaching
@EnableScheduling
@Configuration
public class CacheConfiguration {

    @CacheEvict(value = "keywords", allEntries = true)
    @Scheduled(fixedRateString = "${caching.spring.keywordsTTL}")
    public void clearGroups() {
        log.debug("Clearing caches");
    }
}
