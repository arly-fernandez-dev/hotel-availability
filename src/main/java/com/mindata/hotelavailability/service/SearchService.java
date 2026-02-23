package com.mindata.hotelavailability.service;

import com.mindata.hotelavailability.dto.CountResponse;
import com.mindata.hotelavailability.dto.SearchRequest;
import com.mindata.hotelavailability.dto.SearchResponse;
import com.mindata.hotelavailability.exception.SearchNotFoundException;
import com.mindata.hotelavailability.model.SearchEntity;
import com.mindata.hotelavailability.repository.SearchRepository;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

@Service
public class SearchService {

    private final KafkaTemplate<String, SearchRequest> kafkaTemplate;
    private final SearchRepository searchRepository;

    public SearchService(KafkaTemplate<String, SearchRequest> kafkaTemplate, SearchRepository searchRepository) {
        this.kafkaTemplate = kafkaTemplate;
        this.searchRepository = searchRepository;
    }

    public SearchResponse createSearch(SearchRequest request) {
        String searchId = UUID.randomUUID().toString();
        kafkaTemplate.send("hotel_availability_searches", searchId, request);
        return new SearchResponse(searchId);
    }

    public CountResponse getCount(String searchId) {
        SearchEntity searchEntity = searchRepository.findById(searchId)
                .orElseThrow(() -> new SearchNotFoundException(searchId));

        List<Integer> sortedAges = new ArrayList<>(searchEntity.getAges());
        Collections.sort(sortedAges);

        List<SearchEntity> similarSearches = searchRepository.findByHotelIdAndCheckinAndCheckout(
                searchEntity.getHotelId(),
                searchEntity.getCheckin(),
                searchEntity.getCheckout()
        );

        long count = similarSearches.stream()
                .filter(s -> {
                    List<Integer> otherAges = new ArrayList<>(s.getAges());
                    Collections.sort(otherAges);
                    return sortedAges.equals(otherAges);
                })
                .count();

        SearchRequest searchRequest = new SearchRequest(
                searchEntity.getHotelId(),
                searchEntity.getCheckin(),
                searchEntity.getCheckout(),
                sortedAges
        );

        return new CountResponse(searchId, searchRequest, count);
    }
}
