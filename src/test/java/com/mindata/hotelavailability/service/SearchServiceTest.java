package com.mindata.hotelavailability.service;

import com.mindata.hotelavailability.dto.CountResponse;
import com.mindata.hotelavailability.dto.SearchRequest;
import com.mindata.hotelavailability.dto.SearchResponse;
import com.mindata.hotelavailability.model.SearchEntity;
import com.mindata.hotelavailability.repository.SearchRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.kafka.core.KafkaTemplate;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class SearchServiceTest {

    @Mock
    private KafkaTemplate<String, SearchRequest> kafkaTemplate;

    @Mock
    private SearchRepository searchRepository;

    @InjectMocks
    private SearchService searchService;

    @Test
    void testCreateSearch() {
        SearchRequest request = new SearchRequest("1234aBc", "29/12/2023", "31/12/2023", Arrays.asList(30, 29, 1, 3));

        SearchResponse response = searchService.createSearch(request);

        assertNotNull(response.getSearchId());
        verify(kafkaTemplate, times(1)).send(eq("hotel_availability_searches"), anyString(), eq(request));
    }

    @Test
    void testGetCount() {
        String searchId = "test-id";
        SearchEntity entity = new SearchEntity("1234aBc", "29/12/2023", "31/12/2023", Arrays.asList(30, 29, 1, 3));
        entity.setId(searchId);

        SearchEntity similar1 = new SearchEntity("1234aBc", "29/12/2023", "31/12/2023", Arrays.asList(1, 3, 29, 30));
        SearchEntity similar2 = new SearchEntity("1234aBc", "29/12/2023", "31/12/2023", Arrays.asList(30, 29, 3, 1));
        List<SearchEntity> similarSearches = Arrays.asList(entity, similar1, similar2);

        when(searchRepository.findById(searchId)).thenReturn(Optional.of(entity));
        when(searchRepository.findByHotelIdAndCheckinAndCheckout("1234aBc", "29/12/2023", "31/12/2023"))
                .thenReturn(similarSearches);

        CountResponse response = searchService.getCount(searchId);

        assertEquals(searchId, response.getSearchId());
        assertEquals(3L, response.getCount());
        assertEquals("1234aBc", response.getSearch().getHotelId());
    }
}
