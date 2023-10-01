package com.jia.loanprojectionsservice.domain.repository;

import com.jia.loanprojectionsservice.domain.entities.LoanProductEntity;

import java.util.Optional;

public interface LoanProductRepository {
    Optional<LoanProductEntity> findByLoanType(String type);

}
