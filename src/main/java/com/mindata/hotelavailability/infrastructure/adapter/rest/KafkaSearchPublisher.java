package com.mindata.hotelavailability.infrastructure.adapter.rest;

import com.mindata.hotelavailability.domain.port.out.SearchEventPublisherPort;
import com.mindata.hotelavailability.domain.model.SearchRequest;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class KafkaSearchPublisher implements SearchEventPublisherPort {

    private final KafkaTemplate<String, SearchRequest> kafkaTemplate;

    public KafkaSearchPublisher(KafkaTemplate<String, SearchRequest> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    @Override
    public void publishSearch(String searchId, String hotelId, String checkin, String checkout, List<Integer> ages) {
        SearchRequest request = new SearchRequest(hotelId, checkin, checkout, ages);
        kafkaTemplate.send("hotel_availability_searches", searchId, request);
    }
}
