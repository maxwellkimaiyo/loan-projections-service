package com.jia.loanprojectionsservice.infrastructure.repositories;

import com.jia.loanprojectionsservice.domain.entities.LoanProductEntity;
import com.jia.loanprojectionsservice.domain.entities.enums.LoanTypes;
import com.jia.loanprojectionsservice.domain.repository.LoanProductRepository;
import lombok.NonNull;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface JpaLoanProductRepository extends JpaRepository<LoanProductEntity, Long>, LoanProductRepository {

    @Override
    @Query("SELECT lp FROM LoanProductEntity lp WHERE lp.type = ?1")
    Optional<LoanProductEntity> findByLoanType(@NonNull String loanTypes);
}