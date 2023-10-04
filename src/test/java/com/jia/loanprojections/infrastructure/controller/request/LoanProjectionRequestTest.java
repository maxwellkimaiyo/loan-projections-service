package com.jia.loanprojections.infrastructure.controller.request;

import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.junit.jupiter.api.DisplayName;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

@SpringBootTest
class LoanProjectionRequestTest {

    private final ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
    private final Validator validator = factory.getValidator();

    @Test
    @DisplayName("Test valid loan projection request")
    void testValidLoanProjectionRequest() {
        LoanProjectionRequest request = LoanProjectionRequest.builder()
                .loanAmount(3000)
                .installmentFrequency("WEEKLY")
                .loanDuration(3)
                .startDate("01/11/2023")
                .loanDurationUnit("WEEKS")
                .build();

        assertTrue(validator.validate(request).isEmpty());
    }

    @Test
    @DisplayName("Test invalid loan duration unit")
    void testInvalidLoanDurationUnit() {
        LoanProjectionRequest request = LoanProjectionRequest.builder()
                .loanAmount(3000)
                .installmentFrequency("WEEKLY")
                .loanDuration(3)
                .startDate("01/11/2023")
                .loanDurationUnit("INVALID_LOAN_UNIT")
                .build();

        assertFalse(validator.validate(request).isEmpty());
    }

    @Test
    @DisplayName("Test invalid installment frequency")
    void testInvalidInstallmentFrequency() {
        LoanProjectionRequest request = LoanProjectionRequest.builder()
                .loanAmount(3000)
                .installmentFrequency("INVALID_FREQUENCY")
                .loanDuration(3)
                .startDate("01/11/2023")
                .loanDurationUnit("WEEKS")
                .build();

        assertFalse(validator.validate(request).isEmpty());
    }

    @Test
    @DisplayName("Test loan projection request getters and setters")
    void testLoanProjectionRequestGettersAndSetters() {
        LoanProjectionRequest request = LoanProjectionRequest.builder()
                .loanAmount(3000)
                .installmentFrequency("weekly")
                .loanDuration(3)
                .startDate("01/11/2023")
                .loanDurationUnit("weeks")
                .build();


        double loanAmount = request.getLoanAmount();
        String installmentFrequency = request.getInstallmentFrequency();
        int loanDuration = request.getLoanDuration();
        String startDate = request.getStartDate();
        String loanDurationUnit = request.getLoanDurationUnit();


        assertEquals(3000, loanAmount, 0.001);
        assertEquals("weekly", installmentFrequency);
        assertEquals(3, loanDuration);
        assertEquals("01/11/2023", startDate);
        assertEquals("weeks", loanDurationUnit);
    }

}
