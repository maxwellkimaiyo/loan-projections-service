
package com.jia.loanprojections.infrastructure.controller.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.jia.loanprojections.application.util.ToUpperCaseDeserializer;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class LoanProjectionRequest {

    @NotNull(message = "The [loan_amount] field cannot be null. Please provide a valid value for the loan amount.")
    @JsonProperty("loan_amount")
    private double loanAmount;

    @NotNull(message = "The [installment_frequency] field cannot be null. Please provide a valid value for the installment frequency.")
    @JsonProperty("installment_frequency")
    @JsonDeserialize(using = ToUpperCaseDeserializer.class)
    @Pattern(regexp = "^(MONTHLY|WEEKLY)$", message = "Invalid [loan_duration_unit] value. Please specify the duration unit as either 'weekly' or 'monthly'.")
    private String installmentFrequency;

    @NotNull(message = "The [loan_duration] field cannot be null. Please provide a valid value for the loan duration.")
    @JsonProperty("loan_duration")
    private int loanDuration;

    @NotNull(message = "The [start_date] field cannot be null. Please provide a valid value for the start date.")
    @JsonProperty("start_date")
    private String startDate;

    @NotNull(message = "The [loan_duration_unit] field cannot be null. Please provide a valid value for the loan duration unit.")
    @JsonProperty("loan_duration_unit")
    @JsonDeserialize(using = ToUpperCaseDeserializer.class)
    @Pattern(regexp = "^(MONTHS|WEEKS)$", message = "Invalid [loan_duration_unit] value. Please specify the duration unit as either 'weeks' or 'months'.")
    private String loanDurationUnit;


}
