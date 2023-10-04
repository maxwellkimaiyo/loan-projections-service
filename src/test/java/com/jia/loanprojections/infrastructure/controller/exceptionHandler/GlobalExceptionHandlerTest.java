package com.jia.loanprojections.infrastructure.controller.exceptionHandler;

import com.jia.loanprojections.application.exception.LoanProjectionException;
import com.jia.loanprojections.infrastructure.controller.response.GenericResponse;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
class GlobalExceptionHandlerTest {

    @InjectMocks
    private GlobalExceptionHandler exceptionHandler;


    @Test
    @DisplayName("Test handle loan projection exception")
    void testHandleLoanProjectionException() {
        LoanProjectionException e = new LoanProjectionException("Test error", HttpStatus.BAD_REQUEST);
        ResponseEntity<GenericResponse<Object>> responseEntity = exceptionHandler.handleLoanProjectionException(e);
        // Assert
        assertNotNull(responseEntity);
        assertEquals(HttpStatus.BAD_REQUEST, responseEntity.getStatusCode());
        assertNotNull(responseEntity.getBody());
        assertEquals("Test error", responseEntity.getBody().getMessage());
    }

    @Test
    @DisplayName("Test handle generic exception")
    void testHandleGenericException() {
        Exception e = new Exception("Test error");
        ResponseEntity<GenericResponse<Object>> responseEntity = exceptionHandler.handleGenericException(e);
        // Assert
        assertNotNull(responseEntity);
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, responseEntity.getStatusCode());
        assertNotNull(responseEntity.getBody());
        assertEquals("INTERNAL_SERVER_ERROR", responseEntity.getBody().getMessage());
    }

}
