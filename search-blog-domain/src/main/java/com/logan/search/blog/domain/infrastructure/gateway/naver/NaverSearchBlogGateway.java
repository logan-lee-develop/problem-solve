package com.logan.search.blog.domain.infrastructure.gateway.naver;

import static com.logan.search.blog.domain.configuration.infra.resilience.ResilienceConfigName.SEARCH_BLOG_NAVER_GET;

import com.google.common.net.HttpHeaders;
import com.logan.search.blog.domain.exception.resilience.ResilienceException;
import com.logan.search.blog.domain.infrastructure.gateway.SearchBlogResponse;
import com.logan.search.blog.domain.infrastructure.resilience.ResilienceService;
import javax.annotation.PostConstruct;
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
public class NaverSearchBlogGateway {

    @Value("${external-services.search-blog-naver.url}")
    private String searchBlogNaverUrl;
    @Value("${external-services.search-blog-naver.client-id}")
    private String searchBlogNaverClientId;
    @Value("${external-services.search-blog-naver.client-secret}")
    private String searchBlogNaverClientSecret;

    private WebClient client;
    private final ResilienceService resilienceService;

    @PostConstruct
    public void init() {
        client = WebClient.builder()
                          .baseUrl(searchBlogNaverUrl)
                          .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                          .defaultHeader("X-Naver-Client-Id", searchBlogNaverClientId)
                          .defaultHeader("X-Naver-Client-Secret", searchBlogNaverClientSecret)
                          .build();
    }

    public SearchBlogResponse search(LinkedMultiValueMap<String, String> params)
      throws ResilienceException {
        try {
            return resilienceService
              .decorate(SEARCH_BLOG_NAVER_GET, () -> {
                  NaverSearchBlogResponse response =
                    client
                      .get()
                      .uri(uriBuilder -> uriBuilder.queryParams(params).build())
                      .retrieve()
                      .bodyToMono(NaverSearchBlogResponse.class).block();
                  log.debug("response = {}", response);
                  return SearchBlogResponse.from(response);
              })
              .get();
        } catch (ResilienceException e) {
            log.error("blog search is unavailable; {}", e.getMessage(), e);
            throw e;
        } catch (Exception e) {
            log.error("Unexpected exception occurred; {}", e.getMessage(), e);
            throw e;
        }
    }
}
