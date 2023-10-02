package com.jia.loanprojectionsservice.infrastructure.controller.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;


/**
 * Represents the basic data structure we shall send back with every response
 * All Api endpoints should respond with the {@link GenericResponse}
 */
@Data
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class GenericResponse<T>  {

    /**
     * the message
     */
    @JsonProperty("message")
    final String message;

    /**
     * the data
     */
    @JsonProperty("data")
    final T data;

    /**
     * the error lists
     */
    @JsonProperty("errors")
    List<CustomErrorsDTO> errors = new ArrayList<>();


    /**
     * The GenericResponse constructor
     * @param message the message, the human-readable message
     * @param data the Object data, the data sent back. This is contextual to the actual response being sent back.
     */
    public GenericResponse(String message, T data) {
        this.message = message;
        this.data = data;
    }


    /**
     * add custom errors to the response
     * @param errorMessage the message error
     */
    public void addCustomErrorMessage(CustomErrorsDTO errorMessage) {
        this.errors.add(errorMessage);
    }


}