package com.mindata.hotelavailability.infrastructure.exception;

public class SearchNotFoundException extends RuntimeException {
    
    public SearchNotFoundException(String searchId) {
        super("Search not found with id: " + searchId);
    }
}
