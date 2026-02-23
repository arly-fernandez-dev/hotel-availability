package com.mindata.hotelavailability.model;

import com.mindata.hotelavailability.infrastructure.adapter.persistence.SearchEntity;
import org.junit.jupiter.api.Test;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

class SearchEntityTest {

    @Test
    void testEntityCreation() {
        SearchEntity entity = new SearchEntity("hotel1", "01/01/2024", "02/01/2024", Arrays.asList(30, 25));
        
        assertEquals("hotel1", entity.getHotelId());
        assertEquals("01/01/2024", entity.getCheckin());
        assertEquals("02/01/2024", entity.getCheckout());
        assertEquals(2, entity.getAges().size());
    }

    @Test
    void testEntitySetters() {
        SearchEntity entity = new SearchEntity();
        entity.setId("id1");
        entity.setHotelId("hotel1");
        entity.setCheckin("01/01/2024");
        entity.setCheckout("02/01/2024");
        entity.setAges(Arrays.asList(30, 25));
        
        assertEquals("id1", entity.getId());
        assertEquals("hotel1", entity.getHotelId());
        assertEquals("01/01/2024", entity.getCheckin());
        assertEquals("02/01/2024", entity.getCheckout());
        assertEquals(2, entity.getAges().size());
    }
}
