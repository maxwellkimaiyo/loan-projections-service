package com.jia.loanprojectionsservice.infrastructure.controller.v1;

import com.jia.loanprojectionsservice.application.service.loanFeeProjection.LoanFeeProjectionsService;
import com.jia.loanprojectionsservice.application.service.loanInstalmentProjection.LoanInstalmentProjectionsService;
import com.jia.loanprojectionsservice.infrastructure.controller.request.LoanProjectionRequest;
import com.jia.loanprojectionsservice.infrastructure.controller.response.GenericResponse;
import com.jia.loanprojectionsservice.infrastructure.controller.response.LoanProjectionResponse;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Author: Maxwell Kimaiyo
 * Controller responsible for retrieving loan projection fees and installment information.
 */
@RestController
@RequestMapping("api/v1/loans")
@Api("Loan Projections API")
public class LoanProjectionsController {

    /**
     * The Logging.
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(LoanProjectionsController.class);


    /**
     * The loan projection service interface
     */
    final LoanFeeProjectionsService loanFeeProjectionsService;

    final LoanInstalmentProjectionsService loanInstalmentProjectionsService;

    @Autowired
    public LoanProjectionsController(LoanFeeProjectionsService loanFeeProjectionsService, LoanInstalmentProjectionsService loanInstalmentProjectionsService) {
        this.loanFeeProjectionsService = loanFeeProjectionsService;
        this.loanInstalmentProjectionsService = loanInstalmentProjectionsService;
    }

    @ApiOperation(value = "Get all applicable fees for a given loan")
    @PostMapping(value = "/projections/fees")
    public ResponseEntity<GenericResponse<LoanProjectionResponse>> getLoanFeeProjections(@Valid @RequestBody LoanProjectionRequest request) {
        LOGGER.debug("Entering in getLoanFeeProjections rest endpoint with body: {} ", request);
        LoanProjectionResponse response = loanFeeProjectionsService.getLoanFeeProjections(request);
        return ResponseEntity.ok().body(new GenericResponse<>("Loan fees projections retrieved successfully", response));
    }

    @ApiOperation(value = "Get all applicable installments for a given loan")
    @PostMapping(value = "/projections/installments")
    public ResponseEntity<GenericResponse<LoanProjectionResponse>> getLoanInstallmentProjections(@Valid @RequestBody LoanProjectionRequest request) {
        LOGGER.debug("Entering in getLoanFeeProjections rest endpoint with body: {} ", request);
        LoanProjectionResponse response = loanInstalmentProjectionsService.getLoanInstallmentProjections(request);
        return ResponseEntity.ok().body(new GenericResponse<>("Loan installments projections retrieved successfully", response));
    }



}
