package com.mindata.hotelavailability.infrastructure.adapter.persistence;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SearchRepository extends MongoRepository<SearchEntity, String> {
    
    List<SearchEntity> findByHotelIdAndCheckinAndCheckout(String hotelId, String checkin, String checkout);
}
