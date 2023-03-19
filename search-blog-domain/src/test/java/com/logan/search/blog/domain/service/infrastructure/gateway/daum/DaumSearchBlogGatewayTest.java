package com.logan.search.blog.domain.service.infrastructure.gateway.daum;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.logan.search.blog.domain.infrastructure.gateway.SearchBlogResponse;
import com.logan.search.blog.domain.infrastructure.gateway.SearchQueryRequest;
import com.logan.search.blog.domain.infrastructure.gateway.daum.DaumSearchBlogGateway;
import com.logan.search.blog.domain.infrastructure.resilience.ResilienceService;
import java.util.function.Supplier;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class DaumSearchBlogGatewayTest {

    @InjectMocks
    private DaumSearchBlogGateway daumSearchBlogGateway;
    @Mock
    private ResilienceService resilienceService;

    @Test
    @DisplayName("Daum blog api를 호출은 resilienceService의 decorate 를 사용한다")
    void test() {

        when(resilienceService.decorate(anyString(), any(Supplier.class)))
          .thenReturn(() -> SearchBlogResponse.builder().build());

        SearchQueryRequest query = SearchQueryRequest.builder().build();

        daumSearchBlogGateway.search(query.toDaumQueryMap());

        verify(resilienceService, times(1)).decorate(anyString(), any(Supplier.class));
    }
}
