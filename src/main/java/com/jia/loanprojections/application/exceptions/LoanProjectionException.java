package com.jia.loanprojections.application.exceptions;

import lombok.Getter;
import org.springframework.http.HttpStatus;

/**
 * The loan Projection exception.
 */
@Getter
public class LoanProjectionException extends RuntimeException {

    /**
     * The Status.
     */
    private HttpStatus status;


    /**
     * Instantiates a new loan Projection exception.
     *
     * @param message the detail message. This is saved for later retrieval by the {@link #getMessage()} method.
     * @param status the status. This is saved for later retrieval by the {@link #getStatus()} method.
     */
    public LoanProjectionException(String message, HttpStatus status) {
        super(message);
        this.status = status;
    }

}
