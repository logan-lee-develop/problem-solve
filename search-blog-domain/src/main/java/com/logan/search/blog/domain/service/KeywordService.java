package com.logan.search.blog.domain.service;

import com.logan.search.blog.domain.entity.KeywordEntity;
import com.logan.search.blog.domain.infrastructure.repository.KeywordRepository;
import com.logan.search.blog.domain.model.Keyword;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
public class KeywordService {

    private final KeywordRepository keywordRepository;

    @Transactional
    public void update(final String keyword) {

        if (StringUtils.isBlank(keyword)) {
            return;
        }

        KeywordEntity entity = keywordRepository.findByKeyword(keyword);
        if (Objects.nonNull(entity)) {
            entity.setHitCount(entity.getHitCount() + 1L);
        } else {
            entity = KeywordEntity.builder()
                                  .keyword(keyword)
                                  .hitCount(1L)
                                  .build();
        }
        keywordRepository.save(entity);
    }

    @Transactional(readOnly = true)
    @Cacheable("keywords")
    public List<Keyword> getTopTen() {
        return keywordRepository
          .findTop10ByOrderByHitCountDesc()
          .stream()
          .filter(Objects::nonNull)
          .map(Keyword::from)
          .collect(Collectors.toList());
    }
}
