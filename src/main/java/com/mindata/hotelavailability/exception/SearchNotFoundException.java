package com.mindata.hotelavailability.exception;

public class SearchNotFoundException extends RuntimeException {
    
    public SearchNotFoundException(String searchId) {
        super("Search not found with id: " + searchId);
    }
}
