package com.mindata.hotelavailability.domain.model;

public class SearchResponse {

    private String searchId;

    public SearchResponse() {
    }

    public SearchResponse(String searchId) {
        this.searchId = searchId;
    }

    public String getSearchId() {
        return searchId;
    }

    public void setSearchId(String searchId) {
        this.searchId = searchId;
    }
}
