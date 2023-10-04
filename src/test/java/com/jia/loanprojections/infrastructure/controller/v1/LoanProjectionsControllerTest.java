package com.jia.loanprojections.infrastructure.controller.v1;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jia.loanprojections.application.service.loanFeeProjection.LoanFeeProjectionsService;
import com.jia.loanprojections.application.service.loanInstalmentProjection.LoanInstalmentProjectionsService;
import com.jia.loanprojections.infrastructure.controller.request.LoanProjectionRequest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@ExtendWith(SpringExtension.class)
@WebMvcTest(LoanProjectionsController.class)
class LoanProjectionsControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private LoanFeeProjectionsService loanFeeProjectionsService;

    @MockBean
    private LoanInstalmentProjectionsService loanInstalmentProjectionsService;

    @Autowired
    ObjectMapper objectMapper;



    @Test
    @DisplayName("Test Get Loan Fee Projections API")
    void testGetLoanFeeProjections() throws Exception {

        // Act and Assert
        mockMvc.perform(post("/api/v1/loans/projections/fees")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(new LoanProjectionRequest(3000, "weekly", 3, "01/11/2023", "weeks"))))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.message").value("Loan fees projections retrieved successfully"));
    }


    @Test
    @DisplayName("Get Loan Installment Projections API")
    void testGetLoanInstallmentProjections() throws Exception {
        // Act and Assert
        mockMvc.perform(post("/api/v1/loans/projections/installments")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(new LoanProjectionRequest(3000, "weekly", 3, "01/11/2023", "weeks"))))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.message").value("Loan installments projections retrieved successfully"));
    }



    @Test
    @DisplayName("Loan Projections Request Validation - Invalid Duration Unit")
    void loanProjectionsRequestValidation_InvalidDurationUnitTest() throws Exception {
        // Act and Assert
        mockMvc.perform(post("/api/v1/loans/projections/installments")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(new LoanProjectionRequest(3000, "weekly", 3, "01/11/2023", ""))))
                .andExpect(status().isBadRequest())
                .andExpect(MockMvcResultMatchers.jsonPath("$.message").value("Invalid [loan_duration_unit] value. Please specify the duration unit as either 'weeks' or 'months'."));
    }



}