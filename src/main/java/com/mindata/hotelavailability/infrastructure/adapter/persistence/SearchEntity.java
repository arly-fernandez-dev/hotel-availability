package com.mindata.hotelavailability.infrastructure.adapter.persistence;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Document(collection = "searches")
public class SearchEntity {

    @Id
    private String id;
    private String hotelId;
    private String checkin;
    private String checkout;
    private List<Integer> ages;

    public SearchEntity() {
    }

    public SearchEntity(String hotelId, String checkin, String checkout, List<Integer> ages) {
        this.hotelId = hotelId;
        this.checkin = checkin;
        this.checkout = checkout;
        this.ages = ages;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getHotelId() {
        return hotelId;
    }

    public void setHotelId(String hotelId) {
        this.hotelId = hotelId;
    }

    public String getCheckin() {
        return checkin;
    }

    public void setCheckin(String checkin) {
        this.checkin = checkin;
    }

    public String getCheckout() {
        return checkout;
    }

    public void setCheckout(String checkout) {
        this.checkout = checkout;
    }

    public List<Integer> getAges() {
        return ages;
    }

    public void setAges(List<Integer> ages) {
        this.ages = ages;
    }
}
