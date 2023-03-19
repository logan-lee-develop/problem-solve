package com.logan.search.blog.domain.model;

import com.logan.search.blog.domain.entity.KeywordEntity;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Keyword {

    private Long keywordId;
    private String keyword;
    private Long hitCount;

    public static Keyword from(KeywordEntity entity) {
        return Keyword
          .builder()
          .keywordId(entity.getKeywordId())
          .keyword(entity.getKeyword())
          .hitCount(entity.getHitCount())
          .build();
    }
}
