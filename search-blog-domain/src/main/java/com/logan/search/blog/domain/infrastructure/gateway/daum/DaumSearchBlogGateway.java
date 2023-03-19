package com.logan.search.blog.domain.infrastructure.gateway.daum;

import static com.logan.search.blog.domain.configuration.infra.resilience.ResilienceConfigName.SEARCH_BLOG_DAUM_GET;

import com.google.common.net.HttpHeaders;
import com.logan.search.blog.domain.exception.resilience.ResilienceException;
import com.logan.search.blog.domain.infrastructure.gateway.SearchBlogResponse;
import com.logan.search.blog.domain.infrastructure.resilience.ResilienceService;
import javax.annotation.PostConstruct;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.web.reactive.function.client.WebClient;

@Slf4j
@Component
@RequiredArgsConstructor
public class DaumSearchBlogGateway {

    @Value("${external-services.search-blog-daum.url}")
    private String searchBlogDaumUrl;
    @Value("${external-services.search-blog-daum.rest-api-key}")
    private String searchBlogDaumRestApiKey;

    private WebClient client;
    private final ResilienceService resilienceService;

    @PostConstruct
    public void init() {
        client = WebClient.builder()
                          .baseUrl(searchBlogDaumUrl)
                          .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                          .defaultHeader(HttpHeaders.AUTHORIZATION, searchBlogDaumRestApiKey)
                          .build();
    }

    public SearchBlogResponse search(LinkedMultiValueMap<String, String> params)
      throws ResilienceException {
        try {
            return resilienceService
              .decorate(SEARCH_BLOG_DAUM_GET, () -> {
                  DaumSearchBlogResponse response =
                    client
                      .get()
                      .uri(uriBuilder -> uriBuilder.queryParams(params).build())
                      .retrieve()
                      .bodyToMono(DaumSearchBlogResponse.class).block();
                  log.debug("response = {}", response);
                  return SearchBlogResponse.from(response);
              })
              .get();
        } catch (ResilienceException e) {
            log.error("resilience exception occurred; {}", e.getMessage(), e);
            throw e;
        } catch (Exception e) {
            log.error("Unexpected exception occurred; {}", e.getMessage(), e);
            throw e;
        }
    }
}
