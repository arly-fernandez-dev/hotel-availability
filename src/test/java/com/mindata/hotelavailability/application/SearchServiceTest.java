package com.mindata.hotelavailability.application;

import com.mindata.hotelavailability.domain.model.Search;
import com.mindata.hotelavailability.domain.port.in.GetSearchCountUseCase;
import com.mindata.hotelavailability.domain.port.out.SearchEventPublisherPort;
import com.mindata.hotelavailability.domain.port.out.SearchRepositoryPort;
import com.mindata.hotelavailability.infrastructure.exception.SearchNotFoundException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class SearchServiceTest {

    @Mock
    private SearchEventPublisherPort eventPublisher;

    @Mock
    private SearchRepositoryPort repository;

    @InjectMocks
    private SearchService searchService;

    @Test
    void testCreateSearch() {
        String searchId = searchService.createSearch("hotel1", "01/01/2024", "02/01/2024", Arrays.asList(30, 25));

        assertNotNull(searchId);
        verify(eventPublisher, times(1)).publishSearch(anyString(), eq("hotel1"), eq("01/01/2024"), eq("02/01/2024"), anyList());
    }

    @Test
    void testGetCount() {
        String searchId = "test-id";
        Search search = new Search(searchId, "hotel1", "01/01/2024", "02/01/2024", Arrays.asList(30, 25));
        
        Search similar1 = new Search("id1", "hotel1", "01/01/2024", "02/01/2024", Arrays.asList(25, 30));
        Search similar2 = new Search("id2", "hotel1", "01/01/2024", "02/01/2024", Arrays.asList(30, 25));
        List<Search> similarSearches = Arrays.asList(search, similar1, similar2);

        when(repository.findById(searchId)).thenReturn(Optional.of(search));
        when(repository.findByHotelIdAndCheckinAndCheckout("hotel1", "01/01/2024", "02/01/2024"))
                .thenReturn(similarSearches);

        GetSearchCountUseCase.SearchCountResult result = searchService.getCount(searchId);

        assertEquals(3L, result.getCount());
        assertEquals(searchId, result.getSearch().getId());
    }

    @Test
    void testGetCountNotFound() {
        when(repository.findById("invalid")).thenReturn(Optional.empty());

        assertThrows(SearchNotFoundException.class, () -> searchService.getCount("invalid"));
    }

    @Test
    void testGetCountWithDifferentAges() {
        String searchId = "test-id";
        Search search = new Search(searchId, "hotel1", "01/01/2024", "02/01/2024", Arrays.asList(30, 25));
        
        Search different = new Search("id2", "hotel1", "01/01/2024", "02/01/2024", Arrays.asList(20, 25));
        List<Search> searches = Arrays.asList(search, different);

        when(repository.findById(searchId)).thenReturn(Optional.of(search));
        when(repository.findByHotelIdAndCheckinAndCheckout("hotel1", "01/01/2024", "02/01/2024"))
                .thenReturn(searches);

        GetSearchCountUseCase.SearchCountResult result = searchService.getCount(searchId);

        assertEquals(1L, result.getCount());
    }
}
