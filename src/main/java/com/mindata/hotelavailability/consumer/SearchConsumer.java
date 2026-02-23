package com.mindata.hotelavailability.consumer;

import com.mindata.hotelavailability.dto.SearchRequest;
import com.mindata.hotelavailability.model.SearchEntity;
import com.mindata.hotelavailability.repository.SearchRepository;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

import java.util.ArrayList;

@Component
public class SearchConsumer {

    private final SearchRepository searchRepository;

    public SearchConsumer(SearchRepository searchRepository) {
        this.searchRepository = searchRepository;
    }

    @KafkaListener(topics = "hotel_availability_searches", groupId = "hotel-availability-group")
    public void consume(@Payload SearchRequest searchRequest, @Header(KafkaHeaders.RECEIVED_KEY) String searchId) {
        SearchEntity entity = new SearchEntity(
                searchRequest.getHotelId(),
                searchRequest.getCheckIn(),
                searchRequest.getCheckOut(),
                new ArrayList<>(searchRequest.getAges())
        );
        entity.setId(searchId);
        searchRepository.save(entity);
    }
}
