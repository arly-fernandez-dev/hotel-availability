package com.mindata.hotelavailability.domain.port.in;

public interface CreateSearchUseCase {
    String createSearch(String hotelId, String checkin, String checkout, java.util.List<Integer> ages);
}
