package com.logan.search.blog.domain.infrastructure.gateway.daum;

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
public class DaumSearchBlogResponse {

    private List<DaumBlogDocument> documents;
    private DaumBlogResponseMeta meta;


}
