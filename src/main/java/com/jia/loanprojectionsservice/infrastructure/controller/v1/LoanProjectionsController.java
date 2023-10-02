package com.jia.loanprojectionsservice.infrastructure.controller.v1;

import com.jia.loanprojectionsservice.application.service.LoanProjectionsService;
import com.jia.loanprojectionsservice.infrastructure.controller.request.LoanProjectionRequest;
import com.jia.loanprojectionsservice.infrastructure.controller.response.GenericResponse;
import com.jia.loanprojectionsservice.infrastructure.controller.response.LoanProjectionResponse;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/v1/loans/projections")
@RequiredArgsConstructor
@Api("Loan Projections API")
public class LoanProjectionsController {

    final LoanProjectionsService loanProjectionsService;

    @ApiOperation(value = "Get all applicable fees for a given loan")
    @PostMapping(value = "/fees")
    public ResponseEntity<GenericResponse<LoanProjectionResponse>> getLoanFeeProjections(@Valid @RequestBody LoanProjectionRequest request) {
        LoanProjectionResponse response = loanProjectionsService.getLoanFeeProjections(request);
        return ResponseEntity.ok().body(new GenericResponse<>("Loan fees projections retrieved successfully", response));
    }

    @ApiOperation(value = "Get all applicable installments for a given loan")
    @PostMapping(value = "/fees")
    public ResponseEntity<GenericResponse<LoanProjectionResponse>> getLoanInstallmentProjections(@Valid @RequestBody LoanProjectionRequest request) {
        LoanProjectionResponse response = loanProjectionsService.getLoanInstallmentProjections(request);
        return ResponseEntity.ok().body(new GenericResponse<>("Loan installments projections retrieved successfully", response));
    }

}
