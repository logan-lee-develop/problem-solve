package com.logan.search.blog.domain.infrastructure.gateway;

import com.logan.search.blog.domain.infrastructure.gateway.daum.DaumSearchBlogResponse;
import com.logan.search.blog.domain.infrastructure.gateway.naver.NaverSearchBlogResponse;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

@ToString
@Getter
@Builder
public class SearchBlogResponse {

    private List<BlogDocument> documents;
    private BlogResponseMeta meta;

    public static SearchBlogResponse from(DaumSearchBlogResponse response) {
        return SearchBlogResponse
          .builder()
          .documents(response
                       .getDocuments()
                       .stream()
                       .filter(Objects::nonNull)
                       .map(BlogDocument::from)
                       .collect(Collectors.toList()))
          .meta(BlogResponseMeta.from(response.getMeta()))
          .build();
    }

    public static SearchBlogResponse from(NaverSearchBlogResponse response) {

        return SearchBlogResponse
          .builder()
          .documents(response
                       .getItems()
                       .stream()
                       .filter(Objects::nonNull)
                       .map(BlogDocument::from)
                       .collect(Collectors.toList()))
          .meta(BlogResponseMeta.from(response))
          .build();
    }
}
