package com.logan.search.blog.domain.infrastructure.gateway;

import com.logan.search.blog.domain.infrastructure.gateway.daum.DaumBlogDocument;
import com.logan.search.blog.domain.infrastructure.gateway.naver.NaverBlogDocument;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class BlogDocument {

    private String blogname;
    private String contents;
    private String datetime;
    private String thumbnail;
    private String title;
    private String url;

    public static BlogDocument from(DaumBlogDocument document) {

        return BlogDocument
          .builder()
          .blogname(document.getBlogname())
          .contents(document.getContents())
          .datetime(document.getDatetime())
          .title(document.getTitle())
          .url(document.getUrl())
          .build();
    }

    public static BlogDocument from(NaverBlogDocument document) {

        return BlogDocument
          .builder()
          .blogname(document.getBloggername())
          .contents(document.getDescription())
          .datetime(document.getPostdate())
          .title(document.getTitle())
          .url(document.getBloggerlink())
          .build();
    }
}
