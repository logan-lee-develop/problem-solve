package com.logan.search.blog.domain.configuration.infra.repository;

import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
@EnableJpaRepositories(
  // TODO: read/write db split
  basePackages = "com.logan.search.blog.domain.infrastructure.repository")
@EnableTransactionManagement
@EntityScan("com.logan.search.blog.domain.entity")
public class KeywordRepositoryConfiguration {
}
