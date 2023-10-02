package com.jia.loanprojectionsservice.util;

import com.jia.loanprojectionsservice.application.exceptions.LoanProjectionException;
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
            // Date format to match input string
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");

            // Parse input date string into a LocalDate object
            LocalDate dateToCheck = LocalDate.parse(inputDate, formatter);

            // Get the current date
            LocalDate currentDate = LocalDate.now();

            // return true if date is today or in the future
            return dateToCheck.isEqual(currentDate) || dateToCheck.isAfter(currentDate);

        } catch (DateTimeParseException e) {
            throw new LoanProjectionException("Invalid date format: " + inputDate + ". Accepted date format 01/01/2000", HttpStatus.BAD_REQUEST);
        }
    }
}
