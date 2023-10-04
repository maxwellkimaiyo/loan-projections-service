package com.jia.loanprojections.application.util;

import com.jia.loanprojections.application.exception.LoanProjectionException;
import org.springframework.http.HttpStatus;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public class DateUtil {
    private DateUtil() {
    }

    public static LocalDate getDate(String dateString) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        return LocalDate.parse(dateString, formatter);
    }
    public static boolean isDateNowOrFuture(String inputDate) {

        try {

            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");

            LocalDate dateToCheck = LocalDate.parse(inputDate, formatter);

            LocalDate currentDate = LocalDate.now();

            // return true if date is today or in the future
            return dateToCheck.isEqual(currentDate) || dateToCheck.isAfter(currentDate);

        } catch (DateTimeParseException e) {
            throw new LoanProjectionException("Invalid date format: " + inputDate + ". Accepted date format 01/01/2000", HttpStatus.BAD_REQUEST);
        }
    }
}
