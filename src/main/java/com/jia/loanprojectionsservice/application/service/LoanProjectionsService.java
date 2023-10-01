package com.jia.loanprojectionsservice.application.service;

import com.jia.loanprojectionsservice.domain.entities.LoanProductEntity;
import com.jia.loanprojectionsservice.domain.repository.LoanProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

/**
 * The type loan product service.
 */
@Service
@RequiredArgsConstructor
public class LoanProjectionsService {

    /**
     * The type loan product repository
     */
    LoanProductRepository loanProductRepository;


}
