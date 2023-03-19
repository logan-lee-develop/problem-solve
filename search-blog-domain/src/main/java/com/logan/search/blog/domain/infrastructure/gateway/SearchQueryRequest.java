package com.logan.search.blog.domain.infrastructure.gateway;

import lombok.Builder;
import lombok.Data;
import org.apache.commons.lang3.StringUtils;
import org.springframework.util.LinkedMultiValueMap;

@Data
@Builder
public class SearchQueryRequest {

    private String query;
    private String sort;
    private int page;
    private int size;

    public LinkedMultiValueMap<String, String> toDaumQueryMap() {
        LinkedMultiValueMap<String, String> map = new LinkedMultiValueMap<>();
        map.add("query", query);
        map.add("sort", sort);
        map.add("page", String.valueOf(page));
        map.add("size", String.valueOf(size));
        return map;
    }


    public LinkedMultiValueMap<String, String> toNaverQueryMap() {
        LinkedMultiValueMap<String, String> map = new LinkedMultiValueMap<>();
        map.add("query", query);
        map.add("sort", StringUtils.equalsIgnoreCase("accuracy", sort) ? "sim" : "date");
        map.add("start", String.valueOf(page));
        map.add("display", String.valueOf(size));
        return map;
    }
}
