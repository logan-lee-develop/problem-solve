package com.logan.search.blog.api.controller.v1;

import com.logan.search.blog.api.controller.v1.dto.PageResponse;
import com.logan.search.blog.domain.infrastructure.gateway.BlogDocument;
import com.logan.search.blog.domain.infrastructure.gateway.SearchBlogResponse;
import com.logan.search.blog.domain.infrastructure.gateway.SearchQueryRequest;
import com.logan.search.blog.domain.service.SearchBlogService;
import io.swagger.annotations.ApiOperation;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@ApiOperation(value = "search api")
@Slf4j
@Validated
@RequiredArgsConstructor
@RestController
@RequestMapping(value = "/api/v1/search-blog/page")
public class SearchBlogController {

    private final SearchBlogService searchBlogService;

    @GetMapping
    public PageResponse<BlogDocument> searchBlogByPageCondition(
      @RequestParam(defaultValue = "") @NotBlank @NotNull String query,
      @RequestParam(defaultValue = "accuracy", required = false) String sort,
      @RequestParam(defaultValue = "1", required = false) @Min(1) @Max(50) int page,
      @RequestParam(defaultValue = "10", required = false) @Min(1) @Max(50) int size) {

        SearchQueryRequest request =
          SearchQueryRequest.builder()
                            .query(query)
                            .sort(StringUtils.equalsIgnoreCase("accuracy", sort) ? "accuracy" : "recency")
                            .page(page)
                            .size(size)
                            .build();

        return convertToPageResponseFrom(searchBlogService.search(request));
    }

    private PageResponse<BlogDocument> convertToPageResponseFrom(SearchBlogResponse searchBlogResponse) {
        PageResponse<BlogDocument> response = new PageResponse<>();
        response.setContent(searchBlogResponse.getDocuments());
        response.setTotalPages(searchBlogResponse.getMeta().getPageable_count());
        response.setTotalElements(searchBlogResponse.getMeta().getTotal_count());
        return response;
    }
}
