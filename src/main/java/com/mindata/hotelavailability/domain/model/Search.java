package com.mindata.hotelavailability.domain.model;

import java.util.List;
import java.util.Objects;

public class Search {

    private String id;
    private String hotelId;
    private String checkin;
    private String checkout;
    private List<Integer> ages;

    public Search(String id, String hotelId, String checkin, String checkout, List<Integer> ages) {
        this.id = id;
        this.hotelId = hotelId;
        this.checkin = checkin;
        this.checkout = checkout;
        this.ages = ages;
    }

    public String getId() {
        return id;
    }

    public String getHotelId() {
        return hotelId;
    }

    public String getCheckin() {
        return checkin;
    }

    public String getCheckout() {
        return checkout;
    }

    public List<Integer> getAges() {
        return ages;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Search search = (Search) o;
        return Objects.equals(id, search.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
