package com.mindata.hotelavailability.infrastructure.adapter.rest;

import com.mindata.hotelavailability.domain.port.in.CreateSearchUseCase;
import com.mindata.hotelavailability.domain.port.in.GetSearchCountUseCase;
import com.mindata.hotelavailability.domain.model.CountResponse;
import com.mindata.hotelavailability.domain.model.SearchRequest;
import com.mindata.hotelavailability.domain.model.SearchResponse;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class SearchController {

    private final CreateSearchUseCase createSearchUseCase;
    private final GetSearchCountUseCase getSearchCountUseCase;

    public SearchController(CreateSearchUseCase createSearchUseCase, GetSearchCountUseCase getSearchCountUseCase) {
        this.createSearchUseCase = createSearchUseCase;
        this.getSearchCountUseCase = getSearchCountUseCase;
    }

    @PostMapping("/search")
    public ResponseEntity<SearchResponse> search(@Valid @RequestBody SearchRequest request) {
        String searchId = createSearchUseCase.createSearch(
                request.getHotelId(),
                request.getCheckIn(),
                request.getCheckOut(),
                request.getAges()
        );
        return ResponseEntity.ok(new SearchResponse(searchId));
    }

    @GetMapping("/count")
    public ResponseEntity<CountResponse> count(@RequestParam String searchId) {
        GetSearchCountUseCase.SearchCountResult result = getSearchCountUseCase.getCount(searchId);
        
        SearchRequest searchRequest = new SearchRequest(
                result.getSearch().getHotelId(),
                result.getSearch().getCheckin(),
                result.getSearch().getCheckout(),
                result.getSearch().getAges()
        );
        
        CountResponse response = new CountResponse(searchId, searchRequest, result.getCount());
        return ResponseEntity.ok(response);
    }
}
