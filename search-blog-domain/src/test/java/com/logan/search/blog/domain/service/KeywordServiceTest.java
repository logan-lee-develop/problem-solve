package com.logan.search.blog.domain.service;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import com.logan.search.blog.domain.entity.KeywordEntity;
import com.logan.search.blog.domain.infrastructure.repository.KeywordRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class KeywordServiceTest {

    @InjectMocks
    private KeywordService keywordService;

    @Mock
    private KeywordRepository keywordRepository;

    @Mock
    private KeywordEntity entity;

    @Test
    @DisplayName("잘못된 keyword의 hitCount는 업데이트 하지 않는다")
    void invalidKeywordIsNotSave() {

        // invalid case
        keywordService.update("");
        verify(keywordRepository, times(0)).save(any());

        keywordService.update(" ");
        verify(keywordRepository, times(0)).save(any());

        keywordService.update(null);
        verify(keywordRepository, times(0)).save(any());

        // normal case
        keywordService.update("keyword");
        verify(keywordRepository, times(1)).save(any());
    }

    @Test
    @DisplayName("존재하는 keyword의 hitCount를 업데이트 한다")
    void updateHitCountByExists() {

        KeywordEntity entity =
          KeywordEntity.builder().keyword("keyword").hitCount(10L).build();

        doReturn(entity)
          .when(keywordRepository).findByKeyword(any(String.class));

        keywordService.update("keyword");

        verify(keywordRepository, times(1)).save(any());
    }

    @Test
    @DisplayName("존재하는 않는 keyword의 hitCount를 업데이트 한다")
    void updateHitCountByNotExists() {

        doReturn(null)
          .when(keywordRepository).findByKeyword(any(String.class));

        keywordService.update("keyword");

        verify(keywordRepository, times(1)).save(any());
    }

}
