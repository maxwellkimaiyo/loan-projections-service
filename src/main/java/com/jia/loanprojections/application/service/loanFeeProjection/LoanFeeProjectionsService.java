package com.jia.loanprojections.application.service.loanFeeProjection;

import com.jia.loanprojections.infrastructure.controller.request.LoanProjectionRequest;
import com.jia.loanprojections.infrastructure.controller.response.LoanProjectionResponse;

public interface LoanFeeProjectionsService {
    LoanProjectionResponse getLoanFeeProjections(LoanProjectionRequest request);
}
