package com.jia.loanprojections.application.util;

import com.jia.loanprojections.application.exceptions.LoanProjectionException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class DateUtilTest {


    @Test
    @DisplayName("Test date")
    void testGetDate() {
        String dateString = "01/11/2023";
        LocalDate expectedDate = LocalDate.of(2023, 11, 1);
        LocalDate actualDate = DateUtil.getDate(dateString);
        // Assert
        assertEquals(expectedDate, actualDate);
    }

    @Test
    @DisplayName("Test is date no or in future date")
    void testIsDateNowOrFuture() {
        LocalDate currentDate = LocalDate.now();
        DateTimeFormatter  formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        // Assert
        assertTrue(DateUtil.isDateNowOrFuture(currentDate.format(formatter)));
        assertTrue(DateUtil.isDateNowOrFuture(currentDate.plusDays(1).format(formatter)));
        assertFalse(DateUtil.isDateNowOrFuture(currentDate.minusDays(1).format(formatter)));
    }

    @Test
    @DisplayName("Test valid date format")
    void testInvalidDateFormat() {
        String invalidDate = "2023-11-01";
        // Assert
        assertThrows(LoanProjectionException.class, () -> DateUtil.isDateNowOrFuture(invalidDate));
    }
}
