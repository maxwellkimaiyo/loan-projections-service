package com.jia.loanprojections.application.service.loanFeeProjection;

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
 * The loan projection service.
 */
@Service
@Transactional
public class LoanFeeProjectionsServiceImpl implements LoanFeeProjectionsService {

    /**
     * The Logging.
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(LoanFeeProjectionsServiceImpl.class);


    final LoanProductRepository loanProductRepository;

    private final LoanProjectionCalculator loanProjectionCalculator;

    final LoanProjectionValidation loanProjectionValidation;

    @Autowired
    public LoanFeeProjectionsServiceImpl(LoanProductRepository loanProductRepository, LoanProjectionCalculator loanProjectionCalculator, LoanProjectionValidation loanProjectionValidation) {
        this.loanProductRepository = loanProductRepository;
        this.loanProjectionCalculator = loanProjectionCalculator;
        this.loanProjectionValidation = loanProjectionValidation;
    }

    /**
     * Get loan fee projections based on the provided request.
     *
     * @param request the loan projection request
     * @return a list of loan fee projections
     */
    @Override
    @Cacheable(value = "getLoanFeeProjections")
    public LoanProjectionResponse getLoanFeeProjections(LoanProjectionRequest request) {
        loanProjectionValidation.validateRequest(request);
        try {
            // Retrieve loan product configuration from loan product entity table
            Optional<LoanProductEntity> productEntity = loanProductRepository.findByLoanInstalmentType(request.getInstallmentFrequency());
            if (productEntity.isPresent()) {
                List<LoanProjections> feeProjections = generateLoanFeeProjections(request, productEntity.get());
                return new LoanProjectionResponse(feeProjections);
            }
        } catch (Exception e) {
            LOGGER.error("Error occurred while calculating loan fee projections: {}", e.getMessage(), e);
        }
        return new LoanProjectionResponse();
    }


    /**
     * Generates loan fee projections based on the provided request and loan product configuration.
     *
     * @param request           The request object containing loan details
     * @param loanProductEntity The product entity configuration specifying fee rates
     * @return The list of loan fee projections
     */

    private List<LoanProjections> generateLoanFeeProjections(LoanProjectionRequest request, LoanProductEntity loanProductEntity) {
        ArrayList<LoanProjections> loanFeeProjections = new ArrayList<>();

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

        double interestAmount = loanProjectionCalculator.calculateInterestAmount(principalAmount, loanInterestRate);

        for (int duration = 1; duration <= loanDuration; duration++) {
            LocalDate dateIncurred = startDate.plus(duration, isWeeklyInstallment ? ChronoUnit.WEEKS : ChronoUnit.MONTHS);

            if (isWeeklyInstallment && duration % 2 == 0) {
                double serviceFee = loanProjectionCalculator.calculateServiceFee(principalAmount, serviceInterestRate, serviceFeeCap);
                loanFeeProjections.add(new LoanProjections(dateIncurred, (long) serviceFee));
            } else if (!isWeeklyInstallment && monthsSinceLastServiceFee == 3) {
                double serviceFee = loanProjectionCalculator.calculateServiceFee(principalAmount, serviceInterestRate, serviceFeeCap);
                loanFeeProjections.add(new LoanProjections(dateIncurred, (long) serviceFee));
                monthsSinceLastServiceFee = 0;
            }

            loanFeeProjections.add(new LoanProjections(dateIncurred, (long) interestAmount));

            if (!isWeeklyInstallment) {
                monthsSinceLastServiceFee++;
            }

        }

        return loanFeeProjections;
    }


}
