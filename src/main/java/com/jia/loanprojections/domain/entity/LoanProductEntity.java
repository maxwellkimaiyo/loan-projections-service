package com.jia.loanprojections.domain.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

/***
 * The  loan LoanProduct table contains configuration for calculating loan projections
 */
@Getter
@Setter
@Entity
@Table(name = "loan_product")
public class LoanProductEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", nullable = false)
    private Long id;

    private String type;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "loan_duration_id", referencedColumnName = "id")
    private LoanDurationEntity loanDuration;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "installment_frequency_id", referencedColumnName = "id")
    private InstallmentFrequencyEntity installmentFrequency;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "loan_fee_id", referencedColumnName = "id")
    private LoanFeeEntity loanFee;

}