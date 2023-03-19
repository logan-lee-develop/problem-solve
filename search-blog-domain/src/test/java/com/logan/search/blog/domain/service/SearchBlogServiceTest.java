package com.logan.search.blog.domain.service;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.logan.search.blog.domain.infrastructure.gateway.SearchQueryRequest;
import com.logan.search.blog.domain.infrastructure.gateway.daum.DaumSearchBlogGateway;
import com.logan.search.blog.domain.infrastructure.gateway.naver.NaverSearchBlogGateway;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.util.LinkedMultiValueMap;

@ExtendWith(MockitoExtension.class)
public class SearchBlogServiceTest {

    @InjectMocks
    private SearchBlogService searchBlogService;

    @Mock
    private DaumSearchBlogGateway daumSearchBlogGateway;

    @Mock
    private NaverSearchBlogGateway naverSearchBlogGateway;

    @Mock
    private KeywordService keywordService;

    private final SearchQueryRequest SEARCH_QUERY_REQUEST =
      SearchQueryRequest.builder()
                        .query("keyword")
                        .page(1)
                        .size(5)
                        .sort("accuracy").build();

    @Test
    @DisplayName("daum blog 검색 한다.")
    void searchDaumBlog() {

        searchBlogService.search(SEARCH_QUERY_REQUEST);

        verify(keywordService, times(1)).update(any(String.class));
        verify(daumSearchBlogGateway, times(1)).search(any(LinkedMultiValueMap.class));
        verify(naverSearchBlogGateway, times(0)).search(any(LinkedMultiValueMap.class));
    }

    @Test
    @DisplayName("daum blog 검색시 오류가 발생하면 naver blog를 검색 한다.")
    void searchNaverBlog() {

        when(daumSearchBlogGateway.search(any(LinkedMultiValueMap.class)))
          .thenThrow(new RuntimeException("runtime exception!!!"));

        searchBlogService.search(SEARCH_QUERY_REQUEST);

        verify(keywordService, times(1)).update(any(String.class));
        verify(daumSearchBlogGateway, times(1)).search(any(LinkedMultiValueMap.class));
        verify(naverSearchBlogGateway, times(1)).search(any(LinkedMultiValueMap.class));
    }
}
