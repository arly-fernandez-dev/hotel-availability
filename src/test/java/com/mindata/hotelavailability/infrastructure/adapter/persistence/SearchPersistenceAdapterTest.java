package com.mindata.hotelavailability.infrastructure.adapter.persistence;

import com.mindata.hotelavailability.domain.model.Search;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class SearchPersistenceAdapterTest {

    @Mock
    private SearchRepository repository;

    @InjectMocks
    private SearchPersistenceAdapter adapter;

    @Test
    void testSave() {
        Search search = new Search("id1", "hotel1", "01/01/2024", "02/01/2024", Arrays.asList(30, 25));
        
        adapter.save(search);

        verify(repository, times(1)).save(any(SearchEntity.class));
    }

    @Test
    void testFindById() {
        SearchEntity entity = new SearchEntity("hotel1", "01/01/2024", "02/01/2024", Arrays.asList(30, 25));
        entity.setId("id1");
        
        when(repository.findById("id1")).thenReturn(Optional.of(entity));

        Optional<Search> result = adapter.findById("id1");

        assertTrue(result.isPresent());
        assertEquals("id1", result.get().getId());
        assertEquals("hotel1", result.get().getHotelId());
    }

    @Test
    void testFindByIdNotFound() {
        when(repository.findById("invalid")).thenReturn(Optional.empty());

        Optional<Search> result = adapter.findById("invalid");

        assertFalse(result.isPresent());
    }

    @Test
    void testFindByHotelIdAndCheckinAndCheckout() {
        SearchEntity entity1 = new SearchEntity("hotel1", "01/01/2024", "02/01/2024", Arrays.asList(30, 25));
        entity1.setId("id1");
        SearchEntity entity2 = new SearchEntity("hotel1", "01/01/2024", "02/01/2024", Arrays.asList(25, 30));
        entity2.setId("id2");
        
        when(repository.findByHotelIdAndCheckinAndCheckout("hotel1", "01/01/2024", "02/01/2024"))
                .thenReturn(Arrays.asList(entity1, entity2));

        List<Search> results = adapter.findByHotelIdAndCheckinAndCheckout("hotel1", "01/01/2024", "02/01/2024");

        assertEquals(2, results.size());
        assertEquals("id1", results.get(0).getId());
        assertEquals("id2", results.get(1).getId());
    }
}
