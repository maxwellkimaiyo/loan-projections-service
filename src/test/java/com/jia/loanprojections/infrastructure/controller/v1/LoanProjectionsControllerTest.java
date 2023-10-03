package com.jia.loanprojections.infrastructure.controller.v1;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jia.loanprojections.application.service.loanFeeProjection.LoanFeeProjectionsService;
import com.jia.loanprojections.application.service.loanInstalmentProjection.LoanInstalmentProjectionsService;
import com.jia.loanprojections.infrastructure.controller.request.LoanProjectionRequest;
import com.jia.loanprojections.infrastructure.controller.response.LoanProjectionResponse;
import com.jia.loanprojections.infrastructure.controller.response.LoanProjections;
import org.junit.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@RunWith(SpringRunner.class)
@WebMvcTest(LoanProjectionsController.class)
public class LoanProjectionsControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private LoanFeeProjectionsService loanFeeProjectionsService;

    @MockBean
    private LoanInstalmentProjectionsService loanInstalmentProjectionsService;

    @Autowired
    ObjectMapper objectMapper;

    private LoanProjectionRequest loanProjectionRequest;
    private LoanProjectionResponse loanProjectionResponse;

    @BeforeEach
    void setUp() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

        loanProjectionRequest = LoanProjectionRequest.builder()
                .loanAmount(3000)
                .loanDuration(3)
                .loanDurationUnit("weeks")
                .startDate("2023-11-01")
                .installmentFrequency("weekly")
                .build();

        loanProjectionResponse = LoanProjectionResponse.builder()
                .loanFeeProjections(List.of(
                        new LoanProjections(LocalDate.parse("2023-11-08", formatter), 30),
                        new LoanProjections(LocalDate.parse("2023-11-15", formatter), 15),
                        new LoanProjections(LocalDate.parse("2023-11-15", formatter), 30),
                        new LoanProjections(LocalDate.parse("2023-11-22", formatter), 30)
                ))
                .build();
    }


    @Test
    @DisplayName("Test Get Loan Fee Projections API")
    public void testGetLoanFeeProjections() throws Exception {
        // Arrange
        Mockito.when(loanFeeProjectionsService.getLoanFeeProjections(loanProjectionRequest))
                .thenReturn(loanProjectionResponse);

        // Act and Assert
        mockMvc.perform(post("/api/v1/loans/projections/fees")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsBytes(new LoanProjectionRequest(3000, "weekly", 3, "01/11/2023", "weeks"))))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.message").value("Loan fees projections retrieved successfully"));
    }


    @Test
    @DisplayName("Get Loan Installment Projections - Success")
    public void getLoanInstallmentProjections_Success() throws Exception {
        // Arrange
        Mockito.when(loanInstalmentProjectionsService.getLoanInstallmentProjections(loanProjectionRequest))
                .thenReturn(loanProjectionResponse);

        // Act and Assert
        mockMvc.perform(post("/api/v1/loans/projections/installments")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsBytes(new LoanProjectionRequest(3000, "weekly", 3, "01/11/2023", "weeks"))))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.message").value("Loan installments projections retrieved successfully"));
    }



    @Test
    @DisplayName("Loan Projections Request Validation - Invalid Duration Unit")
    public void loanProjectionsRequestValidation_InvalidDurationUnitTest() throws Exception {
        // Act and Assert
        mockMvc.perform(post("/api/v1/loans/projections/installments")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsBytes(new LoanProjectionRequest(3000, "weekly", 3, "01/11/2023", ""))))
                .andExpect(status().isBadRequest())
                .andExpect(MockMvcResultMatchers.jsonPath("$.message").value("Invalid [loan_duration_unit] value. Please specify the duration unit as either 'weeks' or 'months'."));
    }



}