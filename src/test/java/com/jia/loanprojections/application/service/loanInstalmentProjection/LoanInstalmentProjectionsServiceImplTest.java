package com.jia.loanprojections.application.service.loanInstalmentProjection;

import com.jia.loanprojections.application.common.LoanProjectionCalculator;
import com.jia.loanprojections.application.validator.LoanProjectionValidator;
import com.jia.loanprojections.domain.entity.LoanFeeEntity;
import com.jia.loanprojections.domain.entity.LoanProductEntity;
import com.jia.loanprojections.domain.repository.LoanProductRepository;
import com.jia.loanprojections.infrastructure.controller.request.LoanProjectionRequest;
import com.jia.loanprojections.infrastructure.controller.response.LoanProjectionResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@SpringBootTest
class LoanInstalmentProjectionsServiceImplTest {

    @InjectMocks
    private LoanInstalmentProjectionsServiceImpl instalmentProjectionsService;

    @Mock
    private LoanProductRepository loanProductRepository;

    @Mock
    private LoanProjectionCalculator loanProjectionCalculator;

    @Mock
    private LoanProjectionValidator loanProjectionValidator;


    private LoanProjectionRequest request;

    @BeforeEach
    void setUp() {
        request = new LoanProjectionRequest(
                3000,
                "weekly",
                3,
                "01/11/2023",
                "weeks"
        );
    }

    @Test
    @DisplayName("Test get Loan Installment Projections")
    void testGetLoanInstallmentProjections() {

        // Arrange
        LoanFeeEntity loanFeeEntity = new LoanFeeEntity();
        loanFeeEntity.setServiceFeeCap(50);
        loanFeeEntity.setServiceFeeRate(0.5);
        loanFeeEntity.setInterestRate(1.0);
        LoanProductEntity productEntity = new LoanProductEntity();
        productEntity.setLoanFee(loanFeeEntity);
        when(loanProductRepository.findByLoanInstalmentType(any())).thenReturn(Optional.of(productEntity));
        when(loanProjectionCalculator.calculateInstalmentAmount(3000,3)).thenReturn(100.0);
        when(loanProjectionCalculator.calculateInterestAmount(3000,1)).thenReturn(30.0);
        when(loanProjectionCalculator.calculateServiceFee(3000,0.5,50)).thenReturn(15.0);

        // Act
        LoanProjectionResponse actual = instalmentProjectionsService.getLoanInstallmentProjections(request);

        // Assert
        assertEquals(3, actual.getLoanFeeProjections().size());

    }
}