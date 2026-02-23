package com.mindata.hotelavailability.domain.port.out;

public interface SearchEventPublisherPort {
    void publishSearch(String searchId, String hotelId, String checkin, String checkout, java.util.List<Integer> ages);
}
