package com.jia.loanprojections.infrastructure.controller.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
public class CustomErrorsDto {

    /**
     * The local date & time
     */
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy hh:mm:ss")
    private LocalDateTime timestamp;

    /**
     * The Message.
     */
    private String message;

    /**
     * The Message.
     */
    private String status;

}
