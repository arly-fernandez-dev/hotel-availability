package com.mindata.hotelavailability.infrastructure.adapter.rest;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mindata.hotelavailability.domain.model.Search;
import com.mindata.hotelavailability.domain.port.in.CreateSearchUseCase;
import com.mindata.hotelavailability.domain.port.in.GetSearchCountUseCase;
import com.mindata.hotelavailability.domain.model.SearchRequest;
import com.mindata.hotelavailability.infrastructure.exception.SearchNotFoundException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;

import static org.mockito.ArgumentMatchers.*;
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
    private CreateSearchUseCase createSearchUseCase;

    @MockBean
    private GetSearchCountUseCase getSearchCountUseCase;

    @Test
    void testSearchEndpoint() throws Exception {
        SearchRequest request = new SearchRequest("hotel1", "01/01/2024", "02/01/2024", Arrays.asList(30, 25));
        
        when(createSearchUseCase.createSearch(anyString(), anyString(), anyString(), anyList()))
                .thenReturn("test-search-id");

        mockMvc.perform(post("/search")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.searchId").value("test-search-id"));
    }

    @Test
    void testCountEndpoint() throws Exception {
        Search search = new Search("test-id", "hotel1", "01/01/2024", "02/01/2024", Arrays.asList(25, 30));
        GetSearchCountUseCase.SearchCountResult result = new GetSearchCountUseCase.SearchCountResult(search, 5L);

        when(getSearchCountUseCase.getCount("test-id")).thenReturn(result);

        mockMvc.perform(get("/count")
                        .param("searchId", "test-id"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.searchId").value("test-id"))
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

    @Test
    void testCountNotFound() throws Exception {
        when(getSearchCountUseCase.getCount("invalid")).thenThrow(new SearchNotFoundException("invalid"));

        mockMvc.perform(get("/count")
                        .param("searchId", "invalid"))
                .andExpect(status().isNotFound());
    }
}
