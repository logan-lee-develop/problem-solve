package com.logan.search.blog.api.controller.v1;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK)
@AutoConfigureMockMvc
public class SearchBlogControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    @DisplayName("블로그 검색하고 결과 페이지를 조회한다.")
    void getSearchBlogPage() throws Exception {

        mockMvc.perform(
                 MockMvcRequestBuilders
                   .get("/api/v1/search-blog/page")
                   .param("query", "파란색")
                   .param("sort", "accuracy")
                   .param("page", "1")
                   .param("size", "1"))
               .andExpect(status().isOk());
    }

    @Test
    @DisplayName("잘못된 조회 조건으로 검색할때의 Bad_Request 리턴.")
    void getSearchBlogPage2() throws Exception {

        mockMvc.perform(
                 MockMvcRequestBuilders
                   .get("/api/v1/search-blog/page")
                   .param("query", "")
                   .param("sort", "accuracy")
                   .param("page", "1")
                   .param("size", "1"))
               .andExpect(status().isBadRequest());

        mockMvc.perform(
                 MockMvcRequestBuilders
                   .get("/api/v1/search-blog/page")
                   .param("sort", "accuracy")
                   .param("page", "1")
                   .param("size", "1"))
               .andExpect(status().isBadRequest());

        mockMvc.perform(
                 MockMvcRequestBuilders
                   .get("/api/v1/search-blog/page")
                   .param("sort", "accuracy")
                   .param("page", "0")
                   .param("size", "1"))
               .andExpect(status().isBadRequest());

        mockMvc.perform(
                 MockMvcRequestBuilders
                   .get("/api/v1/search-blog/page")
                   .param("sort", "accuracy")
                   .param("page", "1")
                   .param("size", "0"))
               .andExpect(status().isBadRequest());

        mockMvc.perform(
                 MockMvcRequestBuilders
                   .get("/api/v1/search-blog/page"))
               .andExpect(status().isBadRequest());
    }
}
