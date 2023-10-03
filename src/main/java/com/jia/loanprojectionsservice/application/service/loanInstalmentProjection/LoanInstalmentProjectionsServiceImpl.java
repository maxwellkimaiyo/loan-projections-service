package com.jia.loanprojectionsservice.application.service.loanInstalmentProjection;

import com.jia.loanprojectionsservice.application.validation.LoanProjectionValidation;
import com.jia.loanprojectionsservice.domain.entities.LoanProductEntity;
import com.jia.loanprojectionsservice.domain.repository.LoanProductRepository;
import com.jia.loanprojectionsservice.infrastructure.controller.request.LoanProjectionRequest;
import com.jia.loanprojectionsservice.infrastructure.controller.response.LoanProjectionResponse;
import com.jia.loanprojectionsservice.infrastructure.controller.response.LoanProjections;
import com.jia.loanprojectionsservice.util.DateUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static com.jia.loanprojectionsservice.domain.enums.DurationUnitType.WEEKS;
import static com.jia.loanprojectionsservice.domain.enums.LoanTypes.MONTHLY;
import static com.jia.loanprojectionsservice.domain.enums.LoanTypes.WEEKLY;

/**
 * The type loan projection service.
 */
@Service
public class LoanInstalmentProjectionsServiceImpl implements LoanInstalmentProjectionsService {

    /**
     * The Logging.
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(LoanInstalmentProjectionsServiceImpl.class);


    /**
     * The type loan product repository
     */
    final LoanProductRepository loanProductRepository;

    /**
     * The type loan projection validation
     */
    final LoanProjectionValidation loanProjectionValidation;

    @Autowired
    public LoanInstalmentProjectionsServiceImpl(LoanProductRepository loanProductRepository, LoanProjectionValidation loanProjectionValidation) {
        this.loanProductRepository = loanProductRepository;
        this.loanProjectionValidation = loanProjectionValidation;
    }

    /**
     * Generates loan fee projections based on the provided request.
     *
     * @param request the loan projection request
     * @return a list of loan fee projections
     */


    @Override
    public LoanProjectionResponse getLoanInstallmentProjections(LoanProjectionRequest request) {
        loanProjectionValidation.validateRequest(request);
        return null;
    }



}
