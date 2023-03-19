package com.logan.search.blog.domain.infrastructure.gateway.naver;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@ToString
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class NaverSearchBlogResponse {

    private List<NaverBlogDocument> items;
    private String lastBuildDate;
    private Long total;
    private Long start;
    private Long display;
}
