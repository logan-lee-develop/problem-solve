package com.logan.search.blog.domain.infrastructure.gateway.daum;

import lombok.Data;

@Data
public class DaumBlogResponseMeta {

    private boolean is_end;
    private long pageable_count;
    private long total_count;
}
