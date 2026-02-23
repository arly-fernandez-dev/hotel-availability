package com.mindata.hotelavailability.controller;

import com.mindata.hotelavailability.dto.CountResponse;
import com.mindata.hotelavailability.dto.SearchRequest;
import com.mindata.hotelavailability.dto.SearchResponse;
import com.mindata.hotelavailability.service.SearchService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class SearchController {

    private final SearchService searchService;

    public SearchController(SearchService searchService) {
        this.searchService = searchService;
    }

    @PostMapping("/search")
    public ResponseEntity<SearchResponse> search(@Valid @RequestBody SearchRequest request) {
        return ResponseEntity.ok(searchService.createSearch(request));
    }

    @GetMapping("/count")
    public ResponseEntity<CountResponse> count(@RequestParam String searchId) {
        return ResponseEntity.ok(searchService.getCount(searchId));
    }
}
