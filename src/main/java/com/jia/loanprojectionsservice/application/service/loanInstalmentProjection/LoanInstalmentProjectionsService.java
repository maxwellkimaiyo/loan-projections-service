package com.jia.loanprojectionsservice.application.service.loanInstalmentProjection;

import com.jia.loanprojectionsservice.infrastructure.controller.request.LoanProjectionRequest;
import com.jia.loanprojectionsservice.infrastructure.controller.response.LoanProjectionResponse;

public interface LoanInstalmentProjectionsService {
    LoanProjectionResponse getLoanInstallmentProjections(LoanProjectionRequest request);

}
