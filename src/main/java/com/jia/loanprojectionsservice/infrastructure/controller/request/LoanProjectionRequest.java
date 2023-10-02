
package com.jia.loanprojectionsservice.infrastructure.controller.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class LoanProjectionRequest {

    @NotNull(message = "Loan amount cannot be null")
    @JsonProperty("loan_amount")
    private long loanAmount;

    @NotNull(message = "Installment frequency cannot be null")
    @JsonProperty("installment_frequency")
    private String installmentFrequency;

    @NotNull(message = "Loan duration cannot be null")
    @JsonProperty("loan_duration")
    private long loanDuration;

    @NotNull(message = "Start date cannot be null")
    @JsonProperty("start_date")
    private String startDate;

    @NotNull(message = "Loan duration unit cannot be null")
    @JsonProperty("loan_duration_unit")
    private String loanDurationUnit;

}
