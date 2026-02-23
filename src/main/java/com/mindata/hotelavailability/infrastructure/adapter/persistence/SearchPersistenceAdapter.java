package com.mindata.hotelavailability.infrastructure.adapter.persistence;

import com.mindata.hotelavailability.domain.model.Search;
import com.mindata.hotelavailability.domain.port.out.SearchRepositoryPort;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
public class SearchPersistenceAdapter implements SearchRepositoryPort {

    private final SearchRepository repository;

    public SearchPersistenceAdapter(SearchRepository repository) {
        this.repository = repository;
    }

    @Override
    public void save(Search search) {
        SearchEntity entity = new SearchEntity(
                search.getHotelId(),
                search.getCheckin(),
                search.getCheckout(),
                search.getAges()
        );
        entity.setId(search.getId());
        repository.save(entity);
    }

    @Override
    public Optional<Search> findById(String id) {
        return repository.findById(id)
                .map(this::toDomain);
    }

    @Override
    public List<Search> findByHotelIdAndCheckinAndCheckout(String hotelId, String checkin, String checkout) {
        return repository.findByHotelIdAndCheckinAndCheckout(hotelId, checkin, checkout)
                .stream()
                .map(this::toDomain)
                .collect(Collectors.toList());
    }

    private Search toDomain(SearchEntity entity) {
        return new Search(
                entity.getId(),
                entity.getHotelId(),
                entity.getCheckin(),
                entity.getCheckout(),
                entity.getAges()
        );
    }
}
