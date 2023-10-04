package com.jia.loanprojections.infrastructure.controller.response;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class LoanProjectionsTest {

    @Test
    @DisplayName("Test loan projections constructor")
    void testLoanProjectionsConstructor() {
        LocalDate date = LocalDate.of(2023, 11, 8);
        long amount = 30;

        LoanProjections loanProjections = new LoanProjections(date, amount);

        assertThat(loanProjections.getDate()).isEqualTo(date);
        assertThat(loanProjections.getAmount()).isEqualTo(amount);
    }

    @Test
    @DisplayName("Test loan projections setter getter")
    void testLoanProjectionsSetterGetter() {
        LocalDate date = LocalDate.of(2023, 11, 15);
        long amount = 15;

        LoanProjections loanProjections = new LoanProjections();
        loanProjections.setDate(date);
        loanProjections.setAmount(amount);

        assertThat(loanProjections.getDate()).isEqualTo(date);
        assertThat(loanProjections.getAmount()).isEqualTo(amount);
    }

}
