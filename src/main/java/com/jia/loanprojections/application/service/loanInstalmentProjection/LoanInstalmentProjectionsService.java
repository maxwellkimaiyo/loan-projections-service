package com.jia.loanprojections.application.service.loanInstalmentProjection;

import com.jia.loanprojections.infrastructure.controller.request.LoanProjectionRequest;
import com.jia.loanprojections.infrastructure.controller.response.LoanProjectionResponse;

public interface LoanInstalmentProjectionsService {
    LoanProjectionResponse getLoanInstallmentProjections(LoanProjectionRequest request);

}
