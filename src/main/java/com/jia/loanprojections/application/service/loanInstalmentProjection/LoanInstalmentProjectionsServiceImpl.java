package com.jia.loanprojections.application.service.loanInstalmentProjection;

import com.jia.loanprojections.application.common.LoanProjectionCalculator;
import com.jia.loanprojections.application.validation.LoanProjectionValidation;
import com.jia.loanprojections.domain.entities.LoanProductEntity;
import com.jia.loanprojections.domain.repository.LoanProductRepository;
import com.jia.loanprojections.infrastructure.controller.request.LoanProjectionRequest;
import com.jia.loanprojections.infrastructure.controller.response.LoanProjectionResponse;
import com.jia.loanprojections.infrastructure.controller.response.LoanProjections;
import com.jia.loanprojections.application.util.DateUtil;
import jakarta.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static com.jia.loanprojections.domain.enums.DurationUnitType.MONTHS;
import static com.jia.loanprojections.domain.enums.LoanInstallmentTypes.WEEKLY;

/**
 * The loan instalment projection service.
 */
@Service
@Transactional
public class LoanInstalmentProjectionsServiceImpl implements LoanInstalmentProjectionsService {

    /**
     * The Logging.
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(LoanInstalmentProjectionsServiceImpl.class);

    final LoanProductRepository loanProductRepository;
    private final LoanProjectionCalculator loanProjectionCalculator;
    final LoanProjectionValidation loanProjectionValidation;

    @Autowired
    public LoanInstalmentProjectionsServiceImpl(LoanProductRepository loanProductRepository, LoanProjectionCalculator loanProjectionCalculator, LoanProjectionValidation loanProjectionValidation) {
        this.loanProductRepository = loanProductRepository;
        this.loanProjectionCalculator = loanProjectionCalculator;
        this.loanProjectionValidation = loanProjectionValidation;
    }


    /**
     * Get loan instalment projections based on the provided request.
     *
     * @param request the loan projection request
     * @return a list of loan instalment projections
     */

    @Override
    @Cacheable(value = "getLoanInstallmentProjections")
    public LoanProjectionResponse getLoanInstallmentProjections(LoanProjectionRequest request) {
        loanProjectionValidation.validateRequest(request);
        try {
            // Retrieve loan product configuration from loan product entity table
            Optional<LoanProductEntity> productEntity = loanProductRepository.findByLoanInstalmentType(request.getInstallmentFrequency());
            if (productEntity.isPresent()) {
                List<LoanProjections> feeProjections = generateLoanInstalmentProjections(request, productEntity.get());
                return new LoanProjectionResponse(feeProjections);
            }
        } catch (Exception e) {
            LOGGER.error("Error occurred while calculating loan instalment projections: {}", e.getMessage(), e);
        }
        return new LoanProjectionResponse();
    }


    /**
     * Generates loan instalment projections based on the provided request and loan product configuration.
     *
     * @param request           The request object containing loan details
     * @param loanProductEntity The product entity configuration specifying fee rates
     * @return The list of loan fee projections
     */
    private List<LoanProjections> generateLoanInstalmentProjections(LoanProjectionRequest request, LoanProductEntity loanProductEntity) {
        ArrayList<LoanProjections> loanInstalmentProjections = new ArrayList<>();

        double principalAmount = request.getLoanAmount();
        int loanDuration = request.getLoanDuration();
        LocalDate startDate = DateUtil.getDate(request.getStartDate());

        double loanInterestRate = loanProductEntity.getLoanFee().getInterestRate();
        double serviceInterestRate = loanProductEntity.getLoanFee().getServiceFeeRate();
        double serviceFeeCap = loanProductEntity.getLoanFee().getServiceFeeCap();

        int monthsSinceLastServiceFee = 0;

        boolean isWeeklyInstallment = WEEKLY.name().equalsIgnoreCase(request.getInstallmentFrequency());

        if (MONTHS.name().equalsIgnoreCase(request.getLoanDurationUnit()) && isWeeklyInstallment) {
            loanDuration *= 4; // convert months to weeks by multiply 4 weeks in each month
        }

        double instalmentAmount = loanProjectionCalculator.calculateInstalmentAmount(principalAmount,loanDuration);
        double interestAmount = loanProjectionCalculator.calculateInterestAmount(principalAmount, loanInterestRate);

        for (int duration = 1; duration <= loanDuration; duration++) {
            LocalDate dateIncurred = startDate.plus(duration, isWeeklyInstallment ? ChronoUnit.WEEKS : ChronoUnit.MONTHS);

            double serviceFee = 0.0;

            if (isWeeklyInstallment && duration % 2 == 0) {
                serviceFee = loanProjectionCalculator.calculateServiceFee(principalAmount, serviceInterestRate, serviceFeeCap);
            } else if (!isWeeklyInstallment && monthsSinceLastServiceFee == 3) {
                serviceFee = loanProjectionCalculator.calculateServiceFee(principalAmount, serviceInterestRate,serviceFeeCap);
                monthsSinceLastServiceFee = 0;
            }

            double totalInstalmentAmount = (interestAmount + instalmentAmount + serviceFee);
            loanInstalmentProjections.add(new LoanProjections(dateIncurred, (long) totalInstalmentAmount));

            if (!isWeeklyInstallment) {
                monthsSinceLastServiceFee++;
            }
        }

        return loanInstalmentProjections;
    }

}
