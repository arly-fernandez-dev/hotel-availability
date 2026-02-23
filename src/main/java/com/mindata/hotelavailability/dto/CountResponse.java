package com.mindata.hotelavailability.dto;

public class CountResponse {

    private String searchId;
    private SearchRequest search;
    private long count;

    public CountResponse() {
    }

    public CountResponse(String searchId, SearchRequest search, long count) {
        this.searchId = searchId;
        this.search = search;
        this.count = count;
    }

    public String getSearchId() {
        return searchId;
    }

    public void setSearchId(String searchId) {
        this.searchId = searchId;
    }

    public SearchRequest getSearch() {
        return search;
    }

    public void setSearch(SearchRequest search) {
        this.search = search;
    }

    public long getCount() {
        return count;
    }

    public void setCount(long count) {
        this.count = count;
    }
}
