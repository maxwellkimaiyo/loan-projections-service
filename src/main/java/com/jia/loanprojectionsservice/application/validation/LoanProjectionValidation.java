package com.jia.loanprojectionsservice.application.validation;

import com.jia.loanprojectionsservice.application.exceptions.LoanProjectionException;
import com.jia.loanprojectionsservice.infrastructure.controller.request.LoanProjectionRequest;
import com.jia.loanprojectionsservice.util.DateUtil;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import static com.jia.loanprojectionsservice.domain.enums.DurationUnitType.WEEKS;
import static com.jia.loanprojectionsservice.domain.enums.LoanTypes.MONTHLY;
import static com.jia.loanprojectionsservice.domain.enums.LoanTypes.WEEKLY;

@Component
public class LoanProjectionValidation {


    /**
     * Validate the LoanProjectionRequest.
     *
     * @param request the Loan projection request to validate
     */
    public void validateRequest(LoanProjectionRequest request) {
        validateStartDate(request.getStartDate());
        validateLoanDuration(request.getLoanDuration(), request.getInstallmentFrequency());
        validateLoanDurationUnit(request);
    }



    /**
     * Validates the loan duration unit within the given loan projection request.
     *
     * @param request the loan projection request to validate
     */

    private void validateLoanDurationUnit(LoanProjectionRequest request) {
        if (WEEKS.name().equalsIgnoreCase(request.getLoanDurationUnit()) && MONTHLY.name().equalsIgnoreCase(request.getInstallmentFrequency())) {
            throw new LoanProjectionException("Un supported installment frequencies", HttpStatus.BAD_REQUEST);
        }
    }


    /**
     * Validates the start date.
     *
     * @param startDate the start date to be validated
     */

    private void validateStartDate(String startDate) {
        if (!DateUtil.isDateNowOrFuture(startDate)) {
            throw new LoanProjectionException("The start date must be today or in the future.", HttpStatus.BAD_REQUEST);
        }
    }


    /**
     * Validate if the loan duration is allowed based on the installment frequency.
     *
     * @param loanDuration         the loan duration
     * @param installmentFrequency the loan frequency
     */

    private void validateLoanDuration(int loanDuration, String installmentFrequency) {
        int maxDuration = getMaxLoanDuration(installmentFrequency);
        if (loanDuration > maxDuration) {
            throw new LoanProjectionException("The loan duration exceeds the maximum allowed.", HttpStatus.BAD_REQUEST);
        }
    }


    /**
     * Get the maximum loan duration based on the given installment frequency.
     *
     * @param installmentFrequency the installment frequency (e.g., "WEEKLY" or "MONTHLY")
     * @return the maximum loan duration
     */

    private int getMaxLoanDuration(String installmentFrequency) {
        return (WEEKLY.name().equalsIgnoreCase(installmentFrequency)) ? 4 * 12 : 12;
    }

}
