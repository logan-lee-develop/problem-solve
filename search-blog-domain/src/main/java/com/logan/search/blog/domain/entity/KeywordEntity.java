package com.logan.search.blog.domain.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Index;
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(
  name = "keyword",
  indexes = {
    @Index(name = "keyword_idx01", columnList = "hitCount, keyword"),
    @Index(name = "keyword_idx02", columnList = "keyword")}
)
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class KeywordEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long keywordId;

    @Column(nullable = false)
    String keyword;

    @Column(nullable = false)
    Long hitCount;
}
