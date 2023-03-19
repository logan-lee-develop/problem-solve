package com.logan.search.blog.domain.infrastructure.gateway.naver;

import lombok.Data;

@Data
public class NaverBlogDocument {

    private String title;
    private String link;
    private String description;
    private String bloggername;
    private String bloggerlink;
    private String postdate;
}
