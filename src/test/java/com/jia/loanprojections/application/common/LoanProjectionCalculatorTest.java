package com.jia.loanprojections.application.common;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
class LoanProjectionCalculatorTest {


    @InjectMocks
    private LoanProjectionCalculator calculator;


    @Test
    @DisplayName("Test calculate interest amount")
    void testCalculateInterestAmount() {
        double principalAmount = 3000.0;
        double interestRate = 1.0;
        double expectedInterestAmount = 30.0;

        double actualInterestAmount = calculator.calculateInterestAmount(principalAmount, interestRate);
        assertEquals(expectedInterestAmount, actualInterestAmount);
    }

    @Test
    @DisplayName("Test calculate service fee")
    void testCalculateServiceFee() {
        double principalAmount = 3000.0;
        double interestRate = 0.5;
        double serviceFeeCap = 50.0;
        double expectedServiceFee = 15.0;

        double actualServiceFee = calculator.calculateServiceFee(principalAmount, interestRate, serviceFeeCap);
        assertEquals(expectedServiceFee, actualServiceFee);
    }

    @Test
    @DisplayName("Test calculate service fee with cap")
    void testCalculateServiceFeeWithCap() {
        double principalAmount = 20000.0;
        double interestRate = 0.5;
        double serviceFeeCap = 50.0;
        double expectedServiceFee = 50.0;

        double actualServiceFee = calculator.calculateServiceFee(principalAmount, interestRate, serviceFeeCap);
        assertEquals(expectedServiceFee, actualServiceFee);
    }

    @Test
    @DisplayName("Test calculate instalment amount")
    void testCalculateInstalmentAmount() {
        double principalAmount = 3000.0;
        int loanDuration = 3;
        double expectedInstalmentAmount = 1000.0;

        double actualInstalmentAmount = calculator.calculateInstalmentAmount(principalAmount, loanDuration);
        assertEquals(expectedInstalmentAmount, actualInstalmentAmount);
    }
}