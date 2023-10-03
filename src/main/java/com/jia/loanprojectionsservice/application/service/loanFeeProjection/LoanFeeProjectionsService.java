package com.jia.loanprojectionsservice.application.service.loanFeeProjection;

import com.jia.loanprojectionsservice.infrastructure.controller.request.LoanProjectionRequest;
import com.jia.loanprojectionsservice.infrastructure.controller.response.LoanProjectionResponse;

public interface LoanFeeProjectionsService {
    LoanProjectionResponse getLoanFeeProjections(LoanProjectionRequest request);
}
