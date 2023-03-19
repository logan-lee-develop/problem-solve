package com.logan.search.blog.domain.configuration.infra.resilience;

import java.util.Map;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Builder
public class ResilienceConfigMap {
    private Map<String, ResilienceConfig> configs;
}
