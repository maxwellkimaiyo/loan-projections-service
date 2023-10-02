package com.jia.loanprojectionsservice.application.exceptions;

import lombok.Getter;
import org.springframework.http.HttpStatus;

/**
 * The type App exception.
 */
@Getter
public class LoanProjectionException extends RuntimeException {

    /**
     * The Status.
     */
    private HttpStatus status;


    /**
     * Instantiates a new App exception.
     *
     * @param message the detail message. This is saved for later retrieval by the {@link #getMessage()} method.
     * @param status the status. This is saved for later retrieval by the {@link #getStatus()} method.
     */
    public LoanProjectionException(String message, HttpStatus status) {
        super(message);
        this.status = status;
    }

    /**
     * Instantiates a new App exception.
     *
     * @param message the detail message. This is saved for later retrieval by the {@link #getMessage()} method.
     * @param status the status. This is saved for later retrieval by the {@link #getStatus()} method.
     * @param cause the cause. This is saved for later retrieval by the {@link #getCause()} method.
     */
    public LoanProjectionException(String message, HttpStatus status, Throwable cause) {
        super(message, cause);
        this.status = status;
    }
}
