package com.jia.loanprojections.infrastructure.controller.response;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class LoanProjectionResponseTest {
    @Test
    @DisplayName("Test loan projection response getters and setters")
    void testLoanProjectionResponseGettersAndSetters() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

        List<LoanProjections> loanFeeProjections = new ArrayList<>();
        loanFeeProjections.add(new LoanProjections(LocalDate.parse("2023-11-08", formatter), 30));
        loanFeeProjections.add(new LoanProjections(LocalDate.parse("2023-11-08", formatter), 15));
        LoanProjectionResponse response = LoanProjectionResponse.builder()
                .loanFeeProjections(loanFeeProjections)
                .build();
        List<LoanProjections> retrievedProjections = response.getLoanFeeProjections();

        assertEquals(loanFeeProjections, retrievedProjections);
    }
}