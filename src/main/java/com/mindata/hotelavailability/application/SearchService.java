package com.mindata.hotelavailability.application;

import com.mindata.hotelavailability.domain.model.Search;
import com.mindata.hotelavailability.domain.port.in.CreateSearchUseCase;
import com.mindata.hotelavailability.domain.port.in.GetSearchCountUseCase;
import com.mindata.hotelavailability.domain.port.out.SearchEventPublisherPort;
import com.mindata.hotelavailability.domain.port.out.SearchRepositoryPort;
import com.mindata.hotelavailability.infrastructure.exception.SearchNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

@Service
public class SearchService implements CreateSearchUseCase, GetSearchCountUseCase {

    private final SearchEventPublisherPort eventPublisher;
    private final SearchRepositoryPort repository;

    public SearchService(SearchEventPublisherPort eventPublisher, SearchRepositoryPort repository) {
        this.eventPublisher = eventPublisher;
        this.repository = repository;
    }

    @Override
    public String createSearch(String hotelId, String checkin, String checkout, List<Integer> ages) {
        String searchId = UUID.randomUUID().toString();
        eventPublisher.publishSearch(searchId, hotelId, checkin, checkout, ages);
        return searchId;
    }

    @Override
    public SearchCountResult getCount(String searchId) {
        Search search = repository.findById(searchId)
                .orElseThrow(() -> new SearchNotFoundException(searchId));

        List<Integer> sortedAges = new ArrayList<>(search.getAges());
        Collections.sort(sortedAges);

        List<Search> similarSearches = repository.findByHotelIdAndCheckinAndCheckout(
                search.getHotelId(),
                search.getCheckin(),
                search.getCheckout()
        );

        long count = similarSearches.stream()
                .filter(s -> {
                    List<Integer> otherAges = new ArrayList<>(s.getAges());
                    Collections.sort(otherAges);
                    return sortedAges.equals(otherAges);
                })
                .count();

        Search resultSearch = new Search(search.getId(), search.getHotelId(), 
                search.getCheckin(), search.getCheckout(), sortedAges);

        return new SearchCountResult(resultSearch, count);
    }
}
