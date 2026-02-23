package com.mindata.hotelavailability.infrastructure.exception;

import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.*;

class GlobalExceptionHandlerTest {

    @Test
    void testHandleSearchNotFound() {
        GlobalExceptionHandler handler = new GlobalExceptionHandler();
        SearchNotFoundException exception = new SearchNotFoundException("test-id");
        
        ResponseEntity<?> response = handler.handleSearchNotFound(exception);
        
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    @Test
    void testSearchNotFoundException() {
        SearchNotFoundException exception = new SearchNotFoundException("test-id");
        
        assertTrue(exception.getMessage().contains("test-id"));
    }
}
