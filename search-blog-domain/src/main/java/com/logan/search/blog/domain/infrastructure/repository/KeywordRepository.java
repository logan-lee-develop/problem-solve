package com.logan.search.blog.domain.infrastructure.repository;

import com.logan.search.blog.domain.entity.KeywordEntity;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface KeywordRepository extends JpaRepository<KeywordEntity, Long> {

    List<KeywordEntity> findTop10ByOrderByHitCountDesc();
    KeywordEntity findByKeyword(String keyword);
}
