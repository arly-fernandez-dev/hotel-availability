package com.mindata.hotelavailability.domain.port.out;

import com.mindata.hotelavailability.domain.model.Search;

import java.util.List;
import java.util.Optional;

public interface SearchRepositoryPort {
    void save(Search search);
    Optional<Search> findById(String id);
    List<Search> findByHotelIdAndCheckinAndCheckout(String hotelId, String checkin, String checkout);
}
