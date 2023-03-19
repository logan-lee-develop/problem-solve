package com.logan.search.blog.api.controller.v1.dto;

import java.util.List;
import lombok.Data;

@Data
public class PageResponse<T> {
    private List<T> content;
    private long totalPages;
    private long totalElements;
}