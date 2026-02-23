package com.mindata.hotelavailability.domain;

import com.mindata.hotelavailability.domain.model.Search;
import org.junit.jupiter.api.Test;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

class SearchTest {

    @Test
    void testSearchCreation() {
        Search search = new Search("id1", "hotel1", "01/01/2024", "02/01/2024", Arrays.asList(30, 25));

        assertEquals("id1", search.getId());
        assertEquals("hotel1", search.getHotelId());
        assertEquals("01/01/2024", search.getCheckin());
        assertEquals("02/01/2024", search.getCheckout());
        assertEquals(2, search.getAges().size());
    }

    @Test
    void testSearchEquality() {
        Search search1 = new Search("id1", "hotel1", "01/01/2024", "02/01/2024", Arrays.asList(30, 25));
        Search search2 = new Search("id1", "hotel2", "03/01/2024", "04/01/2024", Arrays.asList(20, 15));
        Search search3 = new Search("id2", "hotel1", "01/01/2024", "02/01/2024", Arrays.asList(30, 25));

        assertEquals(search1, search2);
        assertNotEquals(search1, search3);
    }

    @Test
    void testSearchHashCode() {
        Search search1 = new Search("id1", "hotel1", "01/01/2024", "02/01/2024", Arrays.asList(30, 25));
        Search search2 = new Search("id1", "hotel2", "03/01/2024", "04/01/2024", Arrays.asList(20, 15));

        assertEquals(search1.hashCode(), search2.hashCode());
    }
}
