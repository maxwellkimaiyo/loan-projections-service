package com.jia.loanprojections.infrastructure.repositories;

import com.jia.loanprojections.domain.entities.LoanProductEntity;
import com.jia.loanprojections.domain.repository.LoanProductRepository;
import lombok.NonNull;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface JpaLoanProductRepository extends JpaRepository<LoanProductEntity, Long>, LoanProductRepository {

    @Override
    @Query("SELECT lp FROM LoanProductEntity lp WHERE lp.type = ?1")
    Optional<LoanProductEntity> findByLoanInstalmentType(@NonNull String loanTypes);
}