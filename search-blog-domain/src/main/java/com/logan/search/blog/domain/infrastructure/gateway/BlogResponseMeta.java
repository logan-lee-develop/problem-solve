package com.logan.search.blog.domain.infrastructure.gateway;

import com.logan.search.blog.domain.infrastructure.gateway.daum.DaumBlogResponseMeta;
import com.logan.search.blog.domain.infrastructure.gateway.naver.NaverSearchBlogResponse;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class BlogResponseMeta {

    private boolean is_end;
    private long pageable_count;
    private long total_count;

    public static BlogResponseMeta from(DaumBlogResponseMeta meta) {
        return
          BlogResponseMeta
            .builder()
            .is_end(meta.is_end())
            .pageable_count(meta.getPageable_count())
            .total_count(meta.getTotal_count())
            .build();
    }

    public static BlogResponseMeta from(NaverSearchBlogResponse response) {
        return
          BlogResponseMeta
            .builder()
            .is_end(false)
            .pageable_count(response.getTotal() / response.getDisplay())
            .total_count(response.getTotal())
            .build();
    }
}
