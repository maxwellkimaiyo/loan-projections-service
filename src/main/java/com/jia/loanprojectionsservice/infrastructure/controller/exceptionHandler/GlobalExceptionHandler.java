package com.jia.loanprojectionsservice.infrastructure.controller.exceptionHandler;


import com.jia.loanprojectionsservice.application.exceptions.AppException;
import com.jia.loanprojectionsservice.infrastructure.controller.response.CustomErrorsDTO;
import com.jia.loanprojectionsservice.infrastructure.controller.response.GenericResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.context.support.DefaultMessageSourceResolvable;

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
     * Handle app exception response entity.
     *
     * @param e the e
     * @return the response entity
     */
    @ExceptionHandler(AppException.class)
    public ResponseEntity<GenericResponse<Object>> handleAppException(AppException e) {
        LOGGER.error("Handling application error: ", e);
        return buildErrorResponseEntity(e.getMessage(), e.getStatus());
    }


    /**
     * Handle validation exception response entity.
     *
     * @param e the e
     * @return the response entity
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<GenericResponse<Object>> handleValidationExceptions(MethodArgumentNotValidException e) {
        LOGGER.error("Handling validation error:", e);

        List<String> errors = e.getBindingResult()
                .getFieldErrors()
                .stream()
                .map(DefaultMessageSourceResolvable::getDefaultMessage)
                .toList();

        return buildErrorResponseEntity(errors, HttpStatus.BAD_REQUEST);
    }


    /**
     * Handle generic exception response entity.
     *
     * @param e the e
     * @return the response entity
     */
    @ExceptionHandler(Exception.class)
    public ResponseEntity<GenericResponse<Object>> handleGenericException(Exception e) {
        LOGGER.error("Handling unexpected error: ", e.getCause());
        return buildErrorResponseEntity();
    }

    /**
     * Build error response entity response entity.
     *
     * @param message the message
     * @param status  the status
     * @return the response entity
     */
    private ResponseEntity<GenericResponse<Object>> buildErrorResponseEntity(String message, HttpStatus status) {
        GenericResponse<Object> model = new GenericResponse<>(message, null);
        model.addCustomErrorMessage(CustomErrorsDTO.builder()
                .message(message)
                .timestamp(LocalDateTime.now())
                .build());

        return ResponseEntity.status(status).body(model);
    }

    /**
     * Build error response entity response entity.
     *
     * @param message the message
     * @param status  the status
     * @return the response entity
     */
    private ResponseEntity<GenericResponse<Object>> buildErrorResponseEntity(List<String> message, HttpStatus status) {
        GenericResponse<Object> model = new GenericResponse<>(message.get(0), null);
        message.forEach(errorMessage -> model.addCustomErrorMessage(CustomErrorsDTO.builder()
                .message(errorMessage)
                .timestamp(LocalDateTime.now())
                .build()));
        return ResponseEntity.status(status).body(model);
    }

    /**
     * Build error response entity response entity.
     *
     * @return the response entity
     */
    private ResponseEntity<GenericResponse<Object>> buildErrorResponseEntity() {
        GenericResponse<Object> model = new GenericResponse<>(HttpStatus.INTERNAL_SERVER_ERROR.name(), null);
        model.addCustomErrorMessage(CustomErrorsDTO.builder()
                .message(HttpStatus.INTERNAL_SERVER_ERROR.name())
                .timestamp(LocalDateTime.now())
                .build());

        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(model);
    }

}
