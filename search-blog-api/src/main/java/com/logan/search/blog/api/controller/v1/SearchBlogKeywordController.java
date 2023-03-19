package com.logan.search.blog.api.controller.v1;

import com.logan.search.blog.domain.model.Keyword;
import com.logan.search.blog.domain.service.KeywordService;
import io.swagger.annotations.ApiOperation;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@ApiOperation(value = "search keyword top-ten")
@Slf4j
@Validated
@RequiredArgsConstructor
@RestController
@RequestMapping(value = "/api/v1/search-blog/keyword/top-ten")
public class SearchBlogKeywordController {

    private final KeywordService keywordService;

    @GetMapping
    public List<Keyword> getCoveragesByAssignedTimeBetween() {
        return keywordService.getTopTen();
    }

}
