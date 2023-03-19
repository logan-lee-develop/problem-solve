package com.logan.search.blog.domain.service;

import com.logan.search.blog.domain.infrastructure.gateway.SearchBlogResponse;
import com.logan.search.blog.domain.infrastructure.gateway.SearchQueryRequest;
import com.logan.search.blog.domain.infrastructure.gateway.daum.DaumSearchBlogGateway;
import com.logan.search.blog.domain.infrastructure.gateway.naver.NaverSearchBlogGateway;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class SearchBlogService {

    public final DaumSearchBlogGateway daumSearchBlogGateway;
    public final NaverSearchBlogGateway naverSearchBlogGateway;
    public final KeywordService keywordService;

    public SearchBlogResponse search(SearchQueryRequest searchQueryRequest) {

        keywordService.update(searchQueryRequest.getQuery());

        SearchBlogResponse searchBlogResponse;
        try {
            searchBlogResponse =
              daumSearchBlogGateway.search(searchQueryRequest.toDaumQueryMap());
        } catch (Exception e) {
            searchBlogResponse =
              naverSearchBlogGateway.search(searchQueryRequest.toNaverQueryMap());
        }
        return searchBlogResponse;
    }
}
