package com.jia.loanprojectionsservice.infrastructure.repositories;

import com.jia.loanprojectionsservice.domain.entities.LoanProductEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LoanProductRepository extends JpaRepository<LoanProductEntity, Long> {

}