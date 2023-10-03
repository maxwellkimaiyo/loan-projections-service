package com.jia.loanprojections.application.service.loanFeeProjection;


import com.jia.loanprojections.application.common.LoanProjectionCalculator;
import com.jia.loanprojections.application.validator.LoanProjectionValidator;
import com.jia.loanprojections.domain.entities.LoanFeeEntity;
import com.jia.loanprojections.domain.entities.LoanProductEntity;
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

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@SpringBootTest
class LoanFeeProjectionsServiceImplTest {

    @InjectMocks
    private LoanFeeProjectionsServiceImpl loanFeeProjectionsService;

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
    @DisplayName("Test get Loan fee Projections")
    void testGetLoanFeeProjections() {
        // Arrange
        LoanFeeEntity loanFeeEntity = new LoanFeeEntity();
        loanFeeEntity.setServiceFeeCap(50);
        loanFeeEntity.setServiceFeeRate(0.5);
        loanFeeEntity.setInterestRate(1.0);
        LoanProductEntity productEntity = new LoanProductEntity();
        productEntity.setLoanFee(loanFeeEntity);

        when(loanProductRepository.findByLoanInstalmentType(any())).thenReturn(Optional.of(productEntity));
        when(loanProjectionCalculator.calculateInterestAmount(3000, 1)).thenReturn(10.0);
        when(loanProjectionCalculator.calculateServiceFee(3000,0.5, 50)).thenReturn(5.0);

        // Act
        LoanProjectionResponse actual = loanFeeProjectionsService.getLoanFeeProjections(request);

        // Assert
        assertEquals(4, actual.getLoanFeeProjections().size());
    }
}

