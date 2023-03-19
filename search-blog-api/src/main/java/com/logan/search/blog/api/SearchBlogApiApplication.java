package com.logan.search.blog.api;

import com.logan.search.blog.domain.DomainPackagePlaceHolder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@Slf4j
@SpringBootApplication(
  scanBasePackageClasses = {
    ApiPackagePlaceHolder.class,
    DomainPackagePlaceHolder.class
  }
)
public class SearchBlogApiApplication {

    public static void main(String[] args) {
        SpringApplication.run(SearchBlogApiApplication.class, args);
    }
}
