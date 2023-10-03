package com.jia.loanprojections.application.validator;

import com.jia.loanprojections.application.exceptions.LoanProjectionException;
import com.jia.loanprojections.infrastructure.controller.request.LoanProjectionRequest;
import com.jia.loanprojections.application.util.DateUtil;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import static com.jia.loanprojections.domain.enums.DurationUnitType.WEEKS;
import static com.jia.loanprojections.domain.enums.LoanInstallmentTypes.MONTHLY;
import static com.jia.loanprojections.domain.enums.LoanInstallmentTypes.WEEKLY;

@Component
public class LoanProjectionValidator {


    /**
     * Validate the LoanProjectionRequest.
     *
     * @param request the Loan projection request to validate
     */
    public void validateRequest(LoanProjectionRequest request) {
        validateStartDate(request.getStartDate());
        validateLoanDuration(request);
        validateLoanDurationUnit(request);
        validateLoanAmount(request.getLoanAmount());
    }


    /**
     * Validates the loan duration unit within the given loan projection request.
     *
     * @param request the loan projection request to validate
     */

    private void validateLoanDurationUnit(LoanProjectionRequest request) {
        if (WEEKS.name().equalsIgnoreCase(request.getLoanDurationUnit()) && MONTHLY.name().equalsIgnoreCase(request.getInstallmentFrequency())) {
            throw new LoanProjectionException("Unsupported installment frequency. Please choose 'weekly' as the installment frequency for your loan.", HttpStatus.BAD_REQUEST);
        }
    }


    /**
     * Validates the start date.
     *
     * @param startDate the start date to be validated
     */

    private void validateStartDate(String startDate) {
        if (!DateUtil.isDateNowOrFuture(startDate)) {
            throw new LoanProjectionException("The loan start date is invalid. Please select a date that is today or in the future.", HttpStatus.BAD_REQUEST);
        }
    }


    /**
     * Validate if the loan duration is allowed.
     *
     * @param request the loan projection request to validate
     */

    private void validateLoanDuration(LoanProjectionRequest request) {
        int maxDuration = getMaxLoanDuration(request.getInstallmentFrequency());
        if (request.getLoanDuration() > maxDuration) {
            throw new LoanProjectionException("The loan duration exceeds the maximum allowed duration of " + maxDuration + " " + request.getLoanDurationUnit().toLowerCase() + ", based on the selected " + request.getInstallmentFrequency().toLowerCase() + " installment frequency.", HttpStatus.BAD_REQUEST);
        } else if (request.getLoanDuration() <= 0) {
            throw new LoanProjectionException("The loan duration cannot be zero. Please enter a valid loan duration greater than zero.", HttpStatus.BAD_REQUEST);

        }
    }

    /**
     * Validate if the loan amount is allowed.
     *
     * @param loanAmount the loan amount
     */

    private void validateLoanAmount(double loanAmount) {
        if (loanAmount <= 0) {
            throw new LoanProjectionException("Please enter a valid loan amount. The loan amount cannot be zero.", HttpStatus.BAD_REQUEST);

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
