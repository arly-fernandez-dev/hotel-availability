package com.mindata.hotelavailability.infrastructure.adapter.rest;

import com.mindata.hotelavailability.domain.model.Search;
import com.mindata.hotelavailability.domain.port.out.SearchRepositoryPort;
import com.mindata.hotelavailability.domain.model.SearchRequest;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

import java.util.ArrayList;

@Component
public class KafkaSearchConsumer {

    private final SearchRepositoryPort repositoryPort;

    public KafkaSearchConsumer(SearchRepositoryPort repositoryPort) {
        this.repositoryPort = repositoryPort;
    }

    @KafkaListener(topics = "hotel_availability_searches", groupId = "hotel-availability-group")
    public void consume(@Payload SearchRequest searchRequest, @Header(KafkaHeaders.RECEIVED_KEY) String searchId) {
        Search search = new Search(
                searchId,
                searchRequest.getHotelId(),
                searchRequest.getCheckIn(),
                searchRequest.getCheckOut(),
                new ArrayList<>(searchRequest.getAges())
        );
        repositoryPort.save(search);
    }
}
