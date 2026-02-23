package com.mindata.hotelavailability.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mindata.hotelavailability.dto.CountResponse;
import com.mindata.hotelavailability.dto.SearchRequest;
import com.mindata.hotelavailability.dto.SearchResponse;
import com.mindata.hotelavailability.service.SearchService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(SearchController.class)
class SearchControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private SearchService searchService;

    @Test
    void testSearchEndpoint() throws Exception {
        SearchRequest request = new SearchRequest("1234aBc", "29/12/2023", "31/12/2023", Arrays.asList(30, 29, 1, 3));
        SearchResponse response = new SearchResponse("test-search-id");

        when(searchService.createSearch(any(SearchRequest.class))).thenReturn(response);

        mockMvc.perform(post("/search")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.searchId").value("test-search-id"));
    }

    @Test
    void testCountEndpoint() throws Exception {
        SearchRequest searchRequest = new SearchRequest("1234aBc", "29/12/2023", "31/12/2023", Arrays.asList(1, 3, 29, 30));
        CountResponse response = new CountResponse("test-search-id", searchRequest, 5L);

        when(searchService.getCount(anyString())).thenReturn(response);

        mockMvc.perform(get("/count")
                        .param("searchId", "test-search-id"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.searchId").value("test-search-id"))
                .andExpect(jsonPath("$.count").value(5));
    }

    @Test
    void testSearchValidation() throws Exception {
        SearchRequest invalidRequest = new SearchRequest("", "", "", null);

        mockMvc.perform(post("/search")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(invalidRequest)))
                .andExpect(status().isBadRequest());
    }
}
