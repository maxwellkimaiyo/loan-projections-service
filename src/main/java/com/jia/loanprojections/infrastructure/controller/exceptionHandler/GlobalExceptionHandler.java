package com.jia.loanprojections.infrastructure.controller.exceptionHandler;


import com.jia.loanprojections.application.exceptions.LoanProjectionException;
import com.jia.loanprojections.infrastructure.controller.dto.CustomErrorsDto;
import com.jia.loanprojections.infrastructure.controller.response.GenericResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.LocalDateTime;
import java.util.List;

/**
 * The type Global exception handler.
 */
@ControllerAdvice
public class GlobalExceptionHandler {

    /**
     * The Logging.
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    /**
     * Handle loan projection exception.
     *
     * @param e the e
     * @return the response entity
     */
    @ExceptionHandler(LoanProjectionException.class)
    public ResponseEntity<GenericResponse<Object>> handleLoanProjectionException(LoanProjectionException e) {
        LOGGER.error("Handling application error: {}", e.getMessage(), e);
        return buildErrorResponseEntity(e.getMessage(), e.getStatus());
    }

    /**
     * Handle validation exception.
     *
     * @param e the e
     * @return the response entity
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<GenericResponse<Object>> handleValidationExceptions(MethodArgumentNotValidException e) {
        LOGGER.error("Handling validation error: {}", e.getMessage(), e);

        List<String> errors = e.getBindingResult()
                .getFieldErrors()
                .stream()
                .map(DefaultMessageSourceResolvable::getDefaultMessage)
                .toList();

        return buildErrorResponseEntity(errors, HttpStatus.BAD_REQUEST);
    }


    /**
     * Handle generic exception.
     *
     * @param e the e
     * @return the response entity
     */
    @ExceptionHandler(Exception.class)
    public ResponseEntity<GenericResponse<Object>> handleGenericException(Exception e) {
        LOGGER.error("Handling unexpected error: {}", e.getMessage(), e);
        return buildErrorResponseEntity();
    }

    /**
     * Build error response entity.
     *
     * @param message the message
     * @param status  the status
     * @return the response entity
     */
    private ResponseEntity<GenericResponse<Object>> buildErrorResponseEntity(String message, HttpStatus status) {
        GenericResponse<Object> model = new GenericResponse<>(message, null);
        model.addCustomErrorMessage(CustomErrorsDto.builder()
                .message(message)
                .status(status.name())
                .timestamp(LocalDateTime.now())
                .build());

        return ResponseEntity.status(status).body(model);
    }

    /**
     * Build error response entity.
     *
     * @param message the message
     * @param status  the status
     * @return the response entity
     */
    private ResponseEntity<GenericResponse<Object>> buildErrorResponseEntity(List<String> message, HttpStatus status) {
        GenericResponse<Object> model = new GenericResponse<>(message.get(0), null);
        message.forEach(errorMessage -> model.addCustomErrorMessage(CustomErrorsDto.builder()
                .message(errorMessage)
                .status(status.name())
                .timestamp(LocalDateTime.now())
                .build()));
        return ResponseEntity.status(status).body(model);
    }

    /**
     * Build error response entity.
     *
     * @return the response entity
     */
    private ResponseEntity<GenericResponse<Object>> buildErrorResponseEntity() {
        GenericResponse<Object> model = new GenericResponse<>(HttpStatus.INTERNAL_SERVER_ERROR.name(), null);
        model.addCustomErrorMessage(CustomErrorsDto.builder()
                .message("An internal server error occurred while processing your request. Please try again later or contact support for assistance.")
                .status(HttpStatus.INTERNAL_SERVER_ERROR.name())
                .timestamp(LocalDateTime.now())
                .build());

        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(model);
    }

}
