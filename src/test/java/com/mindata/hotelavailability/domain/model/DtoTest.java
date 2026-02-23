package com.mindata.hotelavailability.domain.model;

import org.junit.jupiter.api.Test;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

class DtoTest {

    @Test
    void testSearchResponse() {
        SearchResponse response = new SearchResponse("id1");
        assertEquals("id1", response.getSearchId());
        
        response.setSearchId("id2");
        assertEquals("id2", response.getSearchId());
    }

    @Test
    void testCountResponse() {
        SearchRequest request = new SearchRequest("hotel1", "01/01/2024", "02/01/2024", Arrays.asList(30, 25));
        CountResponse response = new CountResponse("id1", request, 5L);
        
        assertEquals("id1", response.getSearchId());
        assertEquals(5L, response.getCount());
        assertNotNull(response.getSearch());
    }

    @Test
    void testSearchRequestImmutability() {
        SearchRequest request = new SearchRequest("hotel1", "01/01/2024", "02/01/2024", Arrays.asList(30, 25));
        
        assertEquals("hotel1", request.getHotelId());
        assertEquals("01/01/2024", request.getCheckIn());
        assertEquals("02/01/2024", request.getCheckOut());
        assertEquals(2, request.getAges().size());
    }
}
