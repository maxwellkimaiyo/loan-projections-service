package com.jia.loanprojectionsservice.application.service;

import com.jia.loanprojectionsservice.infrastructure.controller.request.LoanProjectionRequest;
import com.jia.loanprojectionsservice.infrastructure.controller.response.LoanProjectionResponse;

public interface LoanProjectionsService {

    LoanProjectionResponse getLoanFeeProjections(LoanProjectionRequest request);
    LoanProjectionResponse getLoanInstallmentProjections(LoanProjectionRequest request);

}
