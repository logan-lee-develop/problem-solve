package com.logan.search.blog.domain.infrastructure.gateway.daum;

import lombok.Data;

@Data
public class DaumBlogDocument {

    private String blogname;
    private String contents;
    private String datetime;
    private String thumbnail;
    private String title;
    private String url;
}
