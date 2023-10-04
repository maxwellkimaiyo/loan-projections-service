package com.jia.loanprojections.application.validator;

import com.jia.loanprojections.application.exceptions.LoanProjectionException;
import com.jia.loanprojections.infrastructure.controller.request.LoanProjectionRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
class LoanProjectionValidatorTest {

    @InjectMocks
    private LoanProjectionValidator validator;

    private LoanProjectionRequest request;

    @BeforeEach
    void setUp() {
        request = new LoanProjectionRequest(
                3000,
                "weekly",
                3,
                "01/11/2023",
                "weeks"
        );

        MockitoAnnotations.initMocks(this);
    }

    @Test
    @DisplayName("Test is valid request")
    void testValidRequest() {
        // Assert
        assertDoesNotThrow(() -> validator.validateRequest(request));
    }

    @Test
    @DisplayName("Test valid start date")
    void testInvalidStartDate() {
        request.setStartDate("2022-12-01");
        // Assert
        assertThrows(LoanProjectionException.class, () -> validator.validateRequest(request));
    }

    @Test
    @DisplayName("Test valid loan duration")
    void testInvalidLoanDuration() {
        request.setLoanDuration(0);
        // Assert
        assertThrows(LoanProjectionException.class, () -> validator.validateRequest(request));
    }

    @Test
    @DisplayName("Test valid installment frequency")
    void testInvalidInstallmentFrequency() {
        request.setLoanDurationUnit("weeks");
        request.setInstallmentFrequency("monthly");
        // Assert
        assertThrows(LoanProjectionException.class, () -> validator.validateRequest(request));
    }

    @Test
    @DisplayName("Test valid loan amount")
    void testLoanAmountZero() {
        request.setLoanAmount(0);
        // Assert
        assertThrows(LoanProjectionException.class, () -> validator.validateRequest(request));
    }
}
