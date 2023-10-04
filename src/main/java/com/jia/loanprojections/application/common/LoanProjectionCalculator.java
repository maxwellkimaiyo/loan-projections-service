package com.jia.loanprojections.application.common;


import org.springframework.stereotype.Component;

@Component
public class LoanProjectionCalculator {

    static final double PERCENTAGE = 100.0;

    /**
     * Calculates loan interest amount
     * @param principalAmount the principal amount
     * @param interestRate the interest rate
     * @return interest amount
     */
    public double calculateInterestAmount(double principalAmount, double interestRate) {
        return Math.round((interestRate / PERCENTAGE) * principalAmount);
    }

    /**
     * Calculates loan service fee
     * @param principalAmount the principal amount
     * @param interestRate the interest rate
     * @param serviceFeeCap the service fee cap
     * @return service fee
     */
    public double calculateServiceFee(double principalAmount, double interestRate, double serviceFeeCap) {
        double serviceFee =  (interestRate / PERCENTAGE) * principalAmount;
       return Math.min(serviceFeeCap, Math.round(serviceFee));
    }

    /**
     * Calculates loan instalment amount
     * @param principalAmount the principal amount
     * @param loanDuration the loan duration
     * @return instalment amount
     */
    public double calculateInstalmentAmount(double principalAmount, int loanDuration) {
        return Math.round(principalAmount / loanDuration);
    }
}
