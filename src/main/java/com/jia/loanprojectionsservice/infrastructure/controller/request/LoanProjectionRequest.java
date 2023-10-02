
package com.jia.loanprojectionsservice.infrastructure.controller.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.jia.loanprojectionsservice.util.ToUpperCaseDeserializer;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

@Data
public class LoanProjectionRequest {

    @NotNull(message = "Loan amount cannot be null")
    @JsonProperty("loan_amount")
    private double loanAmount;

    @NotNull(message = "Installment frequency cannot be null")
    @JsonProperty("installment_frequency")
    @JsonDeserialize(using = ToUpperCaseDeserializer.class)
    @Pattern(regexp = "^(MONTHLY|WEEKLY)$", message = "Invalid installment_frequency value. Accepted values are WEEKLY or MONTHLY.")
    private String installmentFrequency;

    @NotNull(message = "Loan duration cannot be null")
    @JsonProperty("loan_duration")
    private int loanDuration;

    @NotNull(message = "Start date cannot be null")
    @JsonProperty("start_date")
    private String startDate;

    @NotNull(message = "Loan duration unit cannot be null")
    @JsonProperty("loan_duration_unit")
    @JsonDeserialize(using = ToUpperCaseDeserializer.class)
    @Pattern(regexp = "^(MONTHS|WEEKS)$", message = "Invalid loan_duration_unit value. Accepted values are WEEKS or MONTHS.")
    private String loanDurationUnit;


}
