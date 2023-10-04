package com.jia.loanprojections.domain.repository;

import com.jia.loanprojections.domain.entity.LoanProductEntity;

import java.util.Optional;

public interface LoanProductRepository {
    Optional<LoanProductEntity> findByLoanInstalmentType(String type);

}
