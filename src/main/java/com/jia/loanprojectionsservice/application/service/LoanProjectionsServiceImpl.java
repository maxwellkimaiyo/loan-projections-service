package com.jia.loanprojectionsservice.application.service;

import com.jia.loanprojectionsservice.domain.repository.LoanProductRepository;
import com.jia.loanprojectionsservice.infrastructure.controller.request.LoanProjectionRequest;
import com.jia.loanprojectionsservice.infrastructure.controller.response.LoanProjectionResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

/**
 * The type loan product service.
 */
@Service
@RequiredArgsConstructor
public class LoanProjectionsServiceImpl implements LoanProjectionsService {

    /**
     * The type loan product repository
     */
    LoanProductRepository loanProductRepository;


    @Override
    public LoanProjectionResponse getLoanFeeProjections(LoanProjectionRequest request) {
        return null;
    }

    @Override
    public LoanProjectionResponse getLoanInstallmentProjections(LoanProjectionRequest request) {
        return null;
    }
}
